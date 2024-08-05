This folder contains grammar experiments. It's extremely unclear what the bnf.x grammar
file contains, but it's not any valid format understood by any of the grammar kits 
like the one in IntelliJ or bncf.

We attempt to generate any valid grammar format and work our way from there to 
tmLanguage format. Right now it's pretty horrible, since there are left recursive
rules that are ALSO not permitted in BNF.

Ranges can be expressed ..., which should be OK but a little bit slower to parse.
