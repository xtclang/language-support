import re

# Unicode these days is 0 to 0x10FFFF
#MAX_UNICODE = 0x10000 # TODO: should really be 0x110000
MAX_UNICODE = 0x100 # TODO: should really be 0x110000

def get_charsets() -> tuple[str, str]:
    whitespace_chars = [chr(i) for i in range(MAX_UNICODE) if chr(i).isspace()]
    no_whitespace_chars = [chr(i) for i in range(MAX_UNICODE) if not chr(i).isspace()]
    return whitespace_chars, no_whitespace_chars

def is_whitespace(i, whitespace_chars=get_charsets()[0]) -> bool:
    return chr(i) in whitespace_chars

def expand_ranges(production, allow_ranges=False):
    def range_expansion(match):
        start, end = match.group(1), match.group(2)
        return ' | '.join([f'"{chr(c)}"' for c in range(ord(start), ord(end) + 1)])
    assert not allow_ranges
    return re.sub(r'"(.)"\.\."(.)"', range_expansion, production)

def replace_token(text: str, token: str, new_token: str = '', prune_multiple_space: bool = True):
    ''' Replace a token/substring in a string, and ensure that there are no multiple whitespaces left around it (and everywhere else '''
    new_text = text.replace(token, new_token)
    return re.sub(r'\s+', ' ', new_text) if prune_multiple_space else new_text

def surround_with_brackets(text, strings):
    def replace_match(match):
        matched_text = match.group(0)
        # Check if the matched text is already surrounded by <>
        if matched_text.startswith('<') and matched_text.endswith('>'):
            return matched_text
        else:
            return f'<{matched_text}>'

    for string in strings:
        # Create a regex pattern to find full-word occurrences of the string not already surrounded by <>
        pattern = re.compile(rf'\b(?<!<){re.escape(string)}(?!>)\b')
        # Replace using the replace_match function
        text = pattern.sub(replace_match, text)

    return text

def pad_to_unicode(number):
    if not (0 <= number <= 0xFFFF):
        raise ValueError("Number must be in the range 0 to 65535 (0x0000 to 0xFFFF).")
    return f"\\\\u{number:04x}"

def get_nowhitespace_values() -> list[str]:
    ws, nws = get_charsets()
    total = len(ws) + len(nws)
    print(f'Created whitespace list of {len(ws)} unicode characters')
    print(f'Created non whitespace list of {len(nws)} unicode characters')
    print(f'Total: {total} unicode characters')
    assert total == MAX_UNICODE
    unicode = []
    for c in nws:
        unicode.append(f'"{pad_to_unicode(ord(c))}"')
    return unicode

def convert_to_bnf_with_optional_and_transform_left_recursion(content: str) -> str:
    # strip leading and trailing whitespace
    content = content.strip()
    lines = [line.strip() for line in content.split('\n')]
    line_count = len(lines)

    # strip comments
    lines = [line for line in lines if not line.startswith('#')]
    uncommented_line_count = len(lines)

    print(f'Non-comment lines in grammar {uncommented_line_count} (total: {line_count})')
    rules = []
    current_rule = []

    # TODO: This is pretty horrible. It would likely be better if we had a colon at the end of the token defintions
    for line in lines:
        if line:
            current_rule.append(line)
        else:
            if current_rule:
                rules.append('\n'.join(current_rule))
                current_rule = []

    if current_rule:
        rules.append('\n'.join(current_rule))

    rule_dict = {}
    for rule in rules:
        lines = rule.split('\n')
        header = lines[0].strip()
        productions = [line.strip() for line in lines[1:]]
        if header in rule_dict:
            rule_dict[header].extend(productions)
        else:
            rule_dict[header] = productions

    rule_dict['NoWhitespace'] = get_nowhitespace_values()
    rule_dict = dict(sorted(rule_dict.items()))

    bnf_rules = []
    for header, productions in rule_dict.items():
        left_recursive = []
        non_recursive = []
        for production in productions:
            production = production.strip()
            production = re.sub(r'(\w+)-opt', r'<\1>', production)
            production = re.sub(r'("[^"]*")-opt', r'[\1]', production)
            production = expand_ranges(production)

            if production.startswith(header):
                left_recursive.append(production[len(header):].strip())
            else:
                non_recursive.append(production)

        if left_recursive:
            new_header = header + "Rest"
            non_recursive_productions = " | ".join(non_recursive) if non_recursive else "ε"
            bnf_rules.append(f'<{header}> ::= {non_recursive_productions} <{new_header}>')
            left_recursive_productions = " | ".join(left_recursive)
            bnf_rules.append(f'<{new_header}> ::= {left_recursive_productions} <{new_header}> | ε')
        else:
            bnf_productions = " | ".join(non_recursive)
            bnf_rules.append(f'<{header}> ::= {bnf_productions}')

    grammar = '\n'.join(bnf_rules) + '\n'

    # Replace all headers (token names) on the rhs with <header>, if not already the case.
    headers = list(rule_dict.keys())
    grammar = surround_with_brackets(grammar, headers)

    # Add primitive NoWhiteSpace rule in EBNF form, which seems to be unable to supply unicode ranges.

    return grammar

def convert_ebnf_to_bnf(input_file_path, output_file_path):
    with open(input_file_path, 'r') as file:
        content = file.read()
    bnf_content = convert_to_bnf_with_optional_and_transform_left_recursion(content)
    with open(output_file_path, 'w') as file:
        file.write(bnf_content)

# Corrected paths for input and output files
input_file_path = 'xtc.ebnf'
output_file_path = 'converted_bnf.bnf'

# Perform the conversion
convert_ebnf_to_bnf(input_file_path, output_file_path)

output_file_path  # Return the path to the converted file
