import re

whitespace_chars = None
no_whitespace_chars = None

def is_whitespace(i) -> bool:
    global whitespace_chars, no_whitespace_chars

    if whitespace_chars is None:
        whitespace_chars = [chr(i) for i in range(0x110000) if chr(i).isspace()]
        no_whitespace_chars = [chr(i) for i in range(0x110000) if not chr(i).isspace()]
        print(whitespace_chars)
        print(f"Total whitespace characters: {len(whitespace_chars)}")
        #print(no_whitespace_chars)
        print(f"Total non-whitespace characters: {len(no_whitespace_chars)}")

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

def convert_to_bnf_with_optional_and_transform_left_recursion(content):
    # strip leading and trailing whitespace
    content = content.strip()

    # remove the NoWhitespace token, which hopefully can just be replace by 'nothing' in real BNF.
    content = replace_token(content, 'NoWhiteSpaces')

    lines = [line.strip() for line in content.split('\n')]

    # strip comments
    lines = [line for line in lines if not line.startswith('#')]

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

    # insert special roles.
    ## rule_nowhitespace = ['NoWhiteSpaces\n']

    rule_dict = {}
    for rule in rules:
        lines = rule.split('\n')
        header = lines[0].strip()
        productions = [line.strip() for line in lines[1:]]
        if header in rule_dict:
            rule_dict[header].extend(productions)
        else:
            rule_dict[header] = productions

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

    return '\n'.join(bnf_rules)

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
