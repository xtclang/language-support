import re

def expand_ranges(production, allow_ranges=False):
    def range_expansion(match):
        start, end = match.group(1), match.group(2)
        return ' | '.join([f'"{chr(c)}"' for c in range(ord(start), ord(end) + 1)])
    assert not allow_ranges
    return re.sub(r'"(.)"\.\."(.)"', range_expansion, production)

def convert_to_bnf_with_optional_and_transform_left_recursion(content):
    # strip whitespace
    content = content.strip()
    lines = [line.strip() for line in content.split('\n')]

    # strip comments
    lines = [line for line in lines if not line.startswith('#')]

    rules = []
    current_rule = []

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
