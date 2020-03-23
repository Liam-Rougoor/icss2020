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

stylesheet: stylesheet_element+;
stylesheet_element: stylesheet_rule | variable_assignment;
stylesheet_rule: selector OPEN_BRACE declaration+ CLOSE_BRACE;
selector: ID_IDENT | CLASS_IDENT | LOWER_IDENT;
declaration: property COLON value SEMICOLON;
property: PROP_COLOR | PROP_BACKGROUND_COLOR | PROP_WIDTH | PROP_HEIGHT;
value: PIXELSIZE | PERCENTAGE | COLOR | TRUE | FALSE | CAPITAL_IDENT;
variable_assignment: CAPITAL_IDENT ASSIGNMENT_OPERATOR value SEMICOLON;
