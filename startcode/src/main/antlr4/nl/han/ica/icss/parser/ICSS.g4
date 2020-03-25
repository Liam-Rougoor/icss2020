grammar ICSS;

//--- LEXER: ---
// IF support:
IF: 'if';
BOX_BRACKET_OPEN: '[';
BOX_BRACKET_CLOSE: ']';


//Literals
TRUE: 'TRUE';
FALSE: 'FALSE';
PIXELSIZE: [0-9]+ 'px';
PERCENTAGE: [0-9]+ '%';
SCALAR: [0-9]+;

//Color value takes precedence over id idents
COLOR: '#' [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f] [0-9a-f];

//Specific identifiers for id's and css classes
ID_IDENT: '#' [a-z0-9\-]+;
CLASS_IDENT: '.' [a-z0-9\-]+;

//Possible properties
PROP_COLOR: 'color';
PROP_BACKGROUND_COLOR: 'background-color';
PROP_WIDTH: 'width';
PROP_HEIGHT: 'height';

//General identifiers
LOWER_IDENT: [a-z] [a-z0-9\-]*;
CAPITAL_IDENT: [A-Z] [A-Za-z0-9_]*;

//All whitespace is skipped
WS: [ \t\r\n]+ -> skip;

//
OPEN_BRACE: '{';
CLOSE_BRACE: '}';
SEMICOLON: ';';
COLON: ':';
PLUS: '+';
MIN: '-';
MUL: '*';
ASSIGNMENT_OPERATOR: ':=';

//--- PARSER: ---

stylesheet: stylesheet_element*;
stylesheet_element: stylesheet_rule | variable_assignment;
stylesheet_rule: selector OPEN_BRACE rule_element* CLOSE_BRACE;
rule_element: declaration | if_expression | variable_assignment;
selector: ID_IDENT | CLASS_IDENT | LOWER_IDENT;
declaration:  property COLON expression SEMICOLON;
if_expression: IF BOX_BRACKET_OPEN value BOX_BRACKET_CLOSE OPEN_BRACE rule_element* CLOSE_BRACE;
property: PROP_COLOR | PROP_BACKGROUND_COLOR | PROP_WIDTH | PROP_HEIGHT;
expression: expression MUL expression | expression (PLUS | MIN) expression | value;
value: PIXELSIZE | PERCENTAGE | COLOR | bool | variable_reference | SCALAR;
variable_assignment: variable_reference ASSIGNMENT_OPERATOR expression SEMICOLON;
variable_reference: CAPITAL_IDENT;
bool: TRUE | FALSE;
