grammar Stupid;

prog: statements EOF;
statements: expr ';' statements
          | expr;

expr: BANG center=expr
    | MINUS center=expr
    | LPAREN center=expr RPAREN
    | var
    | left=expr DOT var
    | call
    | left=expr DOT call
    | left=expr DOT LPAREN argslist? RPAREN
    | left=expr STAR right=expr
    | left=expr SLASH right=expr
    | left=expr PLUS right=expr
    | left=expr MINUS right=expr
    | left=expr LANGLE right=expr
    | left=expr RANGLE right=expr
    | left=expr EQUALS EQUALS right=expr
    | left=expr AND right=expr
    | left=expr OR right=expr
    | left=expr ASK center=expr COLON right=expr
    | assign
    | left=expr DOT assign
    | value;

value: bool
     | str
     | num
     | nil
     | resource
     | block;

assign: IDENTIFIER EQUALS expr;

var: IDENTIFIER;

call: IDENTIFIER LPAREN argslist? RPAREN;

argslist: expr ',' argslist
        | expr;

bool: TRUE
    | FALSE;

str: STRING;

num: INT
   | DOUBLE;

nil: NULL;

resource: '@' (( pckg ':' )? type=IDENTIFIER '/' name=IDENTIFIER | NULL);

pckg: IDENTIFIER ('.' IDENTIFIER)*;

block: '{' ('|' varlist '|')? statements '}';

varlist: IDENTIFIER ',' varlist
       | IDENTIFIER;

STRING: '\'' ( ESC | ~('\\'|'\'') )* '\'';
NULL: 'null';
TRUE: 'true';
FALSE: 'false';
INT: DIGIT+;
DOUBLE: INT ('d'|'f')
      | INT '.' INT ('d'|'f')?;
BANG: '!';
AND: 'and';
EQUALS: '=';
OR: 'or';
PLUS: '+';
MINUS: '-';
SLASH: '/';
STAR: '*';
LANGLE: '<';
RANGLE: '>';
DOLLAR: '$';
ASK: '?';
COLON: ':';
LPAREN: '(';
RPAREN: ')';
DOT: '.';
IDENTIFIER: (ALPHA|DOLLAR) (ALPHANUMERIC)*;
WS: [ \r\t\u000c\n]+ -> skip;

fragment ESC: '\\' ('t'|'n'|'r'|'\''|'\\');
fragment DIGIT: '0'..'9';
fragment UPPERC: [A-Z];
fragment LOWERC: [a-z];
fragment UNDERS: '_';
fragment ALPHA: (UPPERC|LOWERC|UNDERS);
fragment ALPHANUMERIC: (UPPERC|LOWERC|UNDERS|DIGIT);
