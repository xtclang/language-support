import re

def expand_ranges(production):
    # Expand ranges like "0".."9" to "0" | "1" | ... | "9"
    def range_expansion(match):
        start, end = match.group(1), match.group(2)
        return ' | '.join([f'"{chr(c)}"' for c in range(ord(start), ord(end) + 1)])

    return re.sub(r'"(.)"\.\."(.)"', range_expansion, production)

def convert_to_bnf_with_optional_and_transform_left_recursion(content):
    # Strip leading and trailing whitespace from the entire content
    content = content.strip()
    lines = [line.strip() for line in content.split('\n')]

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

    # Add the definition for Name
    #name_rules = [
    #    'Name ::= <Letter> (<Letter> | <Digit> | "_")*',
    #    '<Letter> ::= "a" | "b" | "c" | "d" | "e" | "f" | "g" | "h" | "i" | "j" | "k" | "l" | "m" | "n" | "o" | "p" | "q" | "r" | "s" | "t" | "u" | "v" | "w" | "x" | "y" | "z" | "A" | "B" | "C" | "D" | "E" | "F" | "G" | "H" | "I" | "J" | "K" | "L" | "M" | "N" | "O" | "P" | "Q" | "R" | "S" | "T" | "U" | "V" | "W" | "X" | "Y" | "Z" | "_"',
    #    '<Digit> ::= "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"'
    #]
    #print(rules)

    # Add name rules at the beginning
    #rules = name_rules + rules

    # Store rules in a dictionary
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
            # Handle optional elements marked with -opt
            production = re.sub(r'(\w+)-opt', r'[\1]', production)
            # Handle optional literals marked with -opt
            production = re.sub(r'("[^"]*")-opt', r'[\1]', production)
            # Expand ranges
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

# Read the file content
file_path = 'xtc.ebnf'
with open(file_path, 'r') as file:
    content = file.read()

# Convert the EBNF content to BNF with optional handling and transform left recursion
bnf_content_with_optional_and_transformed = convert_to_bnf_with_optional_and_transform_left_recursion(content)

# Save the converted BNF content to a new file
bnf_file_path_with_optional_and_transformed = 'xtc_with_optional_transformed_valid.bnf'
with open(bnf_file_path_with_optional_and_transformed, 'w') as file:
    file.write(bnf_content_with_optional_and_transformed)

bnf_file_path_with_optional_and_transformed  # Return the path to the converted file with optional elements handled and left recursion transformed