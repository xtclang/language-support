import json
import re

def parse_ebnf(ebnf):
    rules = {}
    current_rule = None
    for line in ebnf.splitlines():
        line = line.strip()
        if not line or line.startswith('#'):
            continue
        if '=' in line:
            lhs, rhs = line.split('=', 1)
            lhs = lhs.strip()
            rhs = rhs.strip().strip(';')
            rules[lhs] = rhs
            current_rule = lhs
        elif current_rule and line.endswith(';'):
            rules[current_rule] += ' ' + line.strip().strip(';')
        elif current_rule:
            rules[current_rule] += ' ' + line.strip()
    return rules

def expand_rule(rule, rules, visited=None):
    if visited is None:
        visited = set()
    tokens = re.split(r'(\s+|\W+)', rule)
    expanded = []
    for token in tokens:
        token = token.strip()
        if token in rules and token not in visited:
            visited.add(token)
            expanded.append('(' + expand_rule(rules[token], rules, visited) + ')')
            visited.remove(token)
        else:
            expanded.append(re.escape(token) if token else token)
    return ''.join(expanded)

def ebnf_to_textmate_patterns(rules):
    patterns = []
    repository = {}

    for lhs, rhs in rules.items():
        pattern_name = lhs.replace("_", "").lower()
        regex_pattern = expand_rule(rhs, rules)
        repository[pattern_name] = {
            "patterns": [
                {
                    "match": regex_pattern,
                    "name": f"meta.{pattern_name}.xtc"
                }
            ]
        }
        patterns.append({"include": f"#{pattern_name}"})

    return patterns, repository

def ebnf_to_textmate(ebnf_grammar):
    rules = parse_ebnf(ebnf_grammar)
    patterns, repository = ebnf_to_textmate_patterns(rules)

    textmate_grammar = {
        "scopeName": "source.xtc",
        "fileTypes": [".xtc"],
        "uuid": "12345678-1234-1234-1234-123456789012",
        "patterns": patterns,
        "repository": repository
    }

    return textmate_grammar

# Read the provided EBNF grammar from file
with open("/mnt/data/xtc.ebnf", "r") as file:
    ebnf_grammar_cleaned = file.read()

# Convert EBNF to TextMate
textmate_grammar = ebnf_to_textmate(ebnf_grammar_cleaned)

# Save to file
output_path = "/mnt/data/xtc.tmLanguage.json"
with open(output_path, "w") as f:
    json.dump(textmate_grammar, f, indent=4)

output_path
