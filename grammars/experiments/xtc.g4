" : ' ;
Name " : ' TypeParameterList '<' TypeParameters '>' TypeParameters TypeParameter TypeParameters ',' TypeParameter TypeParameter Name TypeParameterConstraint-opt TypeParameterConstraint 'extends' ExtendedTypeExpression TypeParameterTypeList '<' TypeExpressionList '>' TypeExpressionList TypeExpressionListElement TypeExpressionList ',' TypeExpressionListElement TypeExpressionListElement TypeParameterTypeList ExtendedTypeExpression CompilationUnit ImportStatements-opt TypeCompositionStatement ImportStatements ImportStatement ImportStatements ImportStatement TypeCompositionStatement Modifiers-opt Category QualifiedName TypeParameterList-opt ParameterList-opt Compositions-opt TypeCompositionBody Category 'module' 'package' 'class' 'interface' 'service' 'const' 'enum' 'mixin' Compositions ConditionalComposition Compositions ConditionalComposition ConditionalComposition IfComposition Composition IfComposition 'if' '(' Expression ')' '(' Compositions ')*' ElseComposition-opt ElseComposition 'else' IfComposition 'else' '(' Compositions ')*' Composition 'extends' ExtendsList 'implements' ImplementsList 'delegates' DelegatesList 'incorporates' IncorporatesList 'into' AnyTypeExpression 'import' ImportModifier-opt QualifiedName VersionRequirement-opt ResourceProvider-opt 'default' '(' Expression ')' ExtendsList ExtendsSingle ExtendsList ',' ExtendsSingle ExtendsSingle TypeExpression ArgumentList-opt ImplementsList ImplementsSingle ImplementsList ',' ImplementsSingle ImplementsSingle ExtendedTypeExpression DelegatesList DelegatesSingle DelegatesList ',' DelegatesSingle DelegatesSingle AnyTypeExpression '(' Expression ')' IncorporatesList IncorporatesSingle IncorporatesList ',' IncorporatesSingle IncorporatesSingle 'conditional' QualifiedName TypeParameterList ArgumentList-opt TypeExpression ArgumentList-opt ImportModifier 'embedded' 'required' 'desired' 'optional' VersionRequirement Version VersionOverrides-opt VersionOverrides VersionOverride VersionOverrides ',' VersionOverride VersionOverride VersionOverrideVerb Versions VersionOverrideVerb 'allow' 'avoid' 'prefer' Versions Version Versions ',' Version Version VersionLiteral ResourceProvider ResourceList-opt 'using' NamedTypeExpression ResourceList 'inject' '(' ResourceListContents-opt ')' ResourceListContents Resources ','-opt Resources Resource Resources ',' Resource Resource TypeExpression ResourceFinish ResourceFinish Name '_' TypeCompositionBody '(' EnumBody ')*' '(' TypeCompositionComponents ')*' ';' EnumBody Enums EnumBodyFinish-opt Enums Enum Enums ',' Enum EnumBodyFinish ';' TypeCompositionComponents Enum Annotations-opt Name TypeParameterTypeList-opt ArgumentList-opt TypeCompositionBody-opt TypeCompositionComponents ConditionalTypeCompositionComponent TypeCompositionComponents ConditionalTypeCompositionComponent ConditionalTypeCompositionComponent IfTypeCompositionComponent TypeCompositionComponent IfTypeCompositionComponent 'if' '(' Expression ')' '(' TypeCompositionComponents ')*' ElseTypeCompositionComponent-opt ElseTypeCompositionComponent 'else' IfTypeCompositionComponent 'else' '(' TypeCompositionComponents ')*' TypeCompositionComponent AccessModifier-opt TypeDefStatement ImportStatement TypeCompositionStatement PropertyDeclarationStatement MethodDeclarationStatement PropertyDeclarationStatement PropertyModifiers-opt TypeExpression Name PropertyDeclarationFinish PropertyModifiers PropertyModifier PropertyModifiers PropertyModifiers PropertyModifier 'static' PropertyAccessModifier Annotation PropertyAccessModifier AccessModifier AccessModifier '/' AccessModifier PropertyDeclarationFinish ';' PropertyDeclarationInitialValue '.' Name Parameters MethodBody PropertyDeclarationInitialValue-opt TypeCompositionBody PropertyDeclarationInitialValue-opt PropertyDeclarationInitialValue ;
"(" OptionalDeclarationList "," OptionalDeclaration ")" " : ' Expression Expression UsingStatement 'using' '(' UsingResources ')' StatementBlock TypeDefStatement 'typedef' AnyTypeExpression 'as' Name ';' Expression ElseExpression ElseExpression TernaryExpression TernaryExpression ':' ElseExpression TernaryExpression OrExpression OrExpression Whitespace '?' TernaryExpression ':' TernaryExpression OrExpression AndExpression OrExpression ' |  | ' AndExpression OrExpression '^^' AndExpression AndExpression EqualityExpression AndExpression '&&' EqualityExpression EqualityExpression RelationalExpression ;
"* : ' ;
"/ : ' ;
"% : ' ´ ;
"+ : ' ;
"_ : ' ;
"<< : ' ;
">> : ' ;
">>> : ' ;
"& : ' ;
"&& : ' ;
"| : ' ;
"|| : ' ;
"^ : ' ;
"?: : ' ;
": : ' ;
"? : ' ImportStatement 'import' QualifiedName ImportFinish ImportFinish ';' 'as' Name ';' NoWhitespace '.*' ';' ReturnStatement 'return' ReturnValue-opt ';' ReturnValue TupleLiteral ExpressionList SwitchStatement 'switch' '(' SwitchCondition ')' '(' SwitchBlocks ')*' SwitchCondition SwitchConditionExpression SwitchCondition ',' SwitchConditionExpression SwitchConditionExpression VariableInitializer Expression SwitchBlocks SwitchBlock SwitchBlocks SwitchBlock SwitchBlock SwitchLabels Statements SwitchBlockFinish-opt SwitchLabels SwitchLabel SwitchLabels SwitchLabel SwitchLabel 'case' CaseOptionList ':' 'default' ':' SwitchBlockFinish: BreakStatement ContinueStatement BreakStatement: 'break' Name-opt ';' ContinueStatement: 'continue' Name-opt ';' CaseOptionList: CaseOption CaseOptionList ',' CaseOption CaseOption: '(' CaseExpressionList ',' CaseExpression ')' SafeCaseExpression CaseExpressionList: CaseExpression CaseExpressionList ',' CaseExpression CaseExpression: '_' Expression SafeCaseExpression: '_' TernaryExpression TryStatement 'try' TryResources-opt StatementBlock TryFinish TryResources '(' VariableInitializationList ')' TryFinish Catches Catches-opt 'finally' StatementBlock Catches Catch Catches Catch Catch 'catch' '(' TypeExpression Name ')' StatementBlock UsingResources UsingResource UsingResources ',' UsingResource UsingResource ;
OptionalDeclaration " : ' Expression ;
EqualityExpression " : =' RelationalExpression ;
EqualityExpression "! : ' RelationalExpression RelationalExpression AssignmentExpression ;
AssignmentExpression "< : >' AssignmentExpression RelationalExpression '<' AssignmentExpression ;
RelationalExpression "< : ' AssignmentExpression RelationalExpression '>' AssignmentExpression ;
RelationalExpression "> : ' AssignmentExpression AssignmentExpression RangeExpression RangeExpression '<-' AssignmentExpression RangeExpression BitwiseExpression RangeExpression '..' BitwiseExpression BitwiseExpression AdditiveExpression BitwiseExpression '<<' AdditiveExpression BitwiseExpression '>>' AdditiveExpression BitwiseExpression '>>>' AdditiveExpression BitwiseExpression '&' AdditiveExpression BitwiseExpression '^' AdditiveExpression BitwiseExpression ' | ' AdditiveExpression AdditiveExpression MultiplicativeExpression AdditiveExpression '+' MultiplicativeExpression AdditiveExpression '-' MultiplicativeExpression MultiplicativeExpression ElvisExpression MultiplicativeExpression '*' ElvisExpression MultiplicativeExpression '/' ElvisExpression MultiplicativeExpression '%' ElvisExpression MultiplicativeExpression '/%' ElvisExpression ElvisExpression PrefixExpression PrefixExpression '?:' ElvisExpression PrefixExpression PostfixExpression '++' PrefixExpression '--' PrefixExpression '+' PrefixExpression '-' PrefixExpression '!' PrefixExpression '~' PrefixExpression PostfixExpression PrimaryExpression PostfixExpression '++' PostfixExpression '--' PostfixExpression '(' Arguments-opt ')' PostfixExpression ArrayDims PostfixExpression ArrayIndexes PostfixExpression NoWhitespace '?' PostfixExpression '.' '&'-opt DotNameFinish PostfixExpression '.new' NewFinish PostfixExpression '.as' '(' AnyTypeExpression ')' PostfixExpression '.is' '(' AnyTypeExpression ')' ArrayDims '(' DimIndicators-opt ')?' DimIndicators DimIndicator DimIndicators ',' DimIndicator DimIndicator '?' ArrayIndexes '(' ExpressionList ')?' ExpressionList Expression ExpressionList ',' Expression DotNameFinish Name TypeParameterTypeList-opt 'default' NewFinish TypeExpression NewArguments AnonClassBody-opt ArgumentList NewArguments ArrayIndexes ArgumentList-opt ArgumentList PrimaryExpression '(' Expression ')' 'new' NewFinish '&'-opt 'construct'-opt QualifiedName TypeParameterTypeList-opt StatementExpression SwitchExpression LambdaExpression '_' Literal AnonClassBody '(' TypeCompositionComponents ')*' StatementExpression StatementBlock 'throw' TernaryExpression 'TODO' TodoFinish-opt 'assert' SwitchExpression 'switch' '(' SwitchCondition-opt ')' '(' SwitchExpressionBlocks ')*' SwitchExpressionBlocks SwitchExpressionBlock SwitchExpressionBlocks SwitchExpressionBlock SwitchExpressionBlock SwitchLabels ExpressionList ';' LambdaExpression LambdaInputs '->' LambdaBody LambdaInputs LambdaParameterName LambdaInferredList LambdaParameterList LambdaInferredList '(' LambdaParameterNames ')' LambdaParameterNames LambdaParameterName LambdaParameterNames ',' LambdaParameterName LambdaParameterList '(' LambdaParameters ')' LambdaParameters LambdaParameter LambdaParameters ',' LambdaParameter LambdaParameter TypeExpression LambdaParameterName LambdaParameterName '_' Name LambdaBody Expression StatementBlock TodoFinish InputCharacter-not-'(' InputCharacters LineTerminator NoWhitespace '(' Expression-opt ')' Literal TypedNumericLiteral IntLiteral FPDecimalLiteral FPBinaryLiteral CharLiteral StringLiteral BinaryLiteral TupleLiteral ListLiteral MapLiteral VersionLiteral DateLiteral TimeOfDayLiteral TimeLiteral TimeZoneLiteral DurationLiteral PathLiteral FileLiteral DirectoryLiteral FileStoreLiteral TypedNumericLiteral: IntTypeName ':' IntLiteral FPTypeName ':' FPLiteral FPLiteral: IntLiteral FPDecimalLiteral FPBinaryLiteral IntTypeName: 'Int' 'Int8' 'Int16' 'Int32' 'Int64' 'Int128' 'IntN' 'Byte' 'UInt' 'UInt8' 'UInt16' 'UInt32' 'UInt64' 'UInt128' 'UIntN' FPTypeName: 'Dec' 'Dec32' 'Dec64' 'Dec128' 'DecN' 'Float8e4' 'Float8e5' 'BFloat16' 'Float16' 'Float32' 'Float64' 'Float128' 'FloatN' StringLiteral ''' CharacterString-opt ''' '$'' CharacterString-opt ''' '\ | ' FreeformLiteral '$ | ' FreeformLiteral '$' NoWhitespace File FreeformLiteral FreeformChars LineTerminator FreeformLines-opt FreeformLines FreeformLine FreeformLines FreeformLine FreeformLine Whitespace-opt ' | ' FreeformChars LineTerminator FreeformChars FreeformChar FreeformChars FreeformChar FreeformChar InputCharacter except LineTerminator BinaryLiteral '#' NoWhitespace Hexits '# | ' FreeformLiteral '#' NoWhitespace File TupleLiteral '(' ExpressionList ',' Expression ')' TypeExpression NoWhitespace ':' '(' ExpressionList-opt ')' CollectionLiteral '(' ExpressionList-opt ')?' TypeExpression NoWhitespace ':' '(' ExpressionList-opt ')?' MapLiteral '(' Entries-opt ')?' TypeExpression NoWhitespace ':' '(' Entries-opt ')?' Entries Entry Entries ',' Entry Entry ;
Expression " : ' Expression VersionLiteral 'Version:' NoWhitespace VersionString 'v:' NoWhitespace VersionString VersionString NonGASuffix VersionNumbers NoWhitespace VersionFinish-opt NoWhitespace Build-opt VersionNumbers DigitsNoUnderscores VersionNumbers NoWhitespace '.' NoWhitespace DigitsNoUnderscores VersionFinish: '-' NoWhitespace NonGASuffix '.' NoWhitespace NonGASuffix NonGASuffix NonGASuffix NonGAPrefix NoWhitespace NonGAVersion-opt NonGAVersion '-' NoWhitespace DigitsNoUnderscores '.' NoWhitespace DigitsNoUnderscores DigitsNoUnderscores Build '+' NoWhitespace BuildChars BuildChars BuildChar BuildChars BuildChar BuildChar '0'..'9' 'A'..'Z' 'a'..'z' '-' '.' NonGAPrefix: 'dev' 'ci' 'qc' 'alpha' 'beta' 'rc' DateLiteral 'Date:' Digit Digit Digit Digit '-' Digit Digit '-' Digit Digit TimeOfDayLiteral 'TimeOfDay:' Digit Digit ':' Digit Digit Seconds-opt Seconds ':' Digit Digit SecondsFraction-opt SecondsFraction '.' NoWhitespace Digits TimeLiteral 'Time:' Digit Digit Digit Digit '-' Digit Digit '-' Digit Digit 'T' Digit Digit ':' Digit Digit Seconds-opt TimeZone-opt TimeZoneLiteral 'TimeZone:' NoWhitespace TimeZone TimeZone 'Z' '+' NoWhitespace Digit NoWhitespace Digit NoWhitespace MinutesOffset-opt '-' NoWhitespace Digit NoWhitespace Digit NoWhitespace MinutesOffset-opt MinutesOffset ':' NoWhitespace Digit NoWhitespace Digit DurationLiteral 'Duration:P' YearsDuration-opt MonthsDuration-opt DaysDuration-opt TimeDuration-opt TimeDuration 'T' NoWhitespace HoursDuration-opt NoWhitespace MinutesDuration-opt NoWhitespace SecondsDuration-opt YearsDuration DigitsNoUnderscores NoWhitespace 'Y' MonthsDuration DigitsNoUnderscores NoWhitespace 'M' DaysDuration DigitsNoUnderscores NoWhitespace 'D' HoursDuration DigitsNoUnderscores NoWhitespace 'H' MinutesDuration DigitsNoUnderscores NoWhitespace 'M' SecondsDuration DigitsNoUnderscores NoWhitespace 'S' PathLiteral 'Path:' NoWhitespace Dir NoWhitespace PathName-opt FileLiteral 'File:'-opt NoWhitespace File DirectoryLiteral 'Directory:'-opt NoWhitespace Dir FileStoreLiteral 'FileStore:' NoWhitespace Dir NoWhitespace PathName-opt File Dir NoWhitespace PathName Dir '/' NoWhitespace DirElements-opt './' NoWhitespace DirElements-opt '../' NoWhitespace DirElements-opt DirElements DirElement DirElements NoWhitespace DirElement DirElement '../' PathName NoWhitespace '/' PathName '.'-opt NoWhitespace PathNameParts PathNameParts PathNamePart PathNameParts NoWhitespace PathNameSpecial NoWhitespace PathNamePart PathNamePart IdentifierTrails PathNameSpecial '.' '-' IdentifierTrails IdentifierTrail IdentifierTrails IdentifierTrail IdentifierTrail TypeExpression IntersectingTypeExpression ExtendedTypeExpression ExtendedIntersectingTypeExpression AnyTypeExpression ExtendedTypeExpression IntersectingTypeExpression UnionedTypeExpression IntersectingTypeExpression '+' UnionedTypeExpression IntersectingTypeExpression '-' UnionedTypeExpression ExtendedIntersectingTypeExpression ExtendedUnionedTypeExpression ExtendedIntersectingTypeExpression '+' ExtendedUnionedTypeExpression ExtendedIntersectingTypeExpression '-' ExtendedUnionedTypeExpression UnionedTypeExpression PrefixTypeExpression UnionedTypeExpression ' | ' PrefixTypeExpression ExtendedUnionedTypeExpression ExtendedPrefixTypeExpression ExtendedUnionedTypeExpression ' | ' ExtendedPrefixTypeExpression PrefixTypeExpression 'immutable'-opt Annotations-opt PostfixTypeExpression ExtendedPrefixTypeExpression 'immutable'-opt TypeAccessModifier-opt Annotations-opt ExtendedPostfixTypeExpression TypeAccessModifier 'struct' AccessModifier PostfixTypeExpression PrimaryTypeExpression PostfixTypeExpression NoWhitespace '?' PostfixTypeExpression ArrayDims PostfixTypeExpression ArrayIndexes ExtendedPostfixTypeExpression ExtendedPrimaryTypeExpression ExtendedPostfixTypeExpression NoWhitespace '?' ExtendedPostfixTypeExpression ArrayDims ExtendedPostfixTypeExpression ArrayIndexes PrimaryTypeExpression '(' ExtendedTypeExpression ')' NamedTypeExpression FunctionTypeExpression ExtendedPrimaryTypeExpression '(' ExtendedTypeExpression ')' NamedTypeExpression FunctionTypeExpression AnonTypeExpression 'const' 'enum' 'module' 'package' 'service' 'class' NamedTypeExpression NamedTypeExpressionPart NamedTypeExpression '.' Annotations-opt NamedTypeExpressionPart NamedTypeExpressionPart QualifiedName NoAutoNarrowModifier-opt TypeParameterTypeList-opt TypeValueSet-opt NoAutoNarrowModifier NoWhitespace '!' TypeValueSet '(' TypeValueList ')*' TypeValueList TypeValue TypeValueList ',' TypeValue TypeValue Expression AnonTypeExpression '(' NameOrSignatureList ';' ')*' NameOrSignatureList NameOrSignature NameOrSignatureList ';' NameOrSignature NameOrSignature Name PropertyModifiers-opt TypeExpression Name MethodModifiers-opt TypeParameterList-opt MethodIdentity ParameterList FunctionTypeExpression 'function' 'conditional'-opt ReturnList Name-opt '(' TypeExpressionList-opt ')' ;