grammar CALC;

expr : factor '+' expr | factor;

factor : term '*' factor | term;

term : '(' expr ')' | NUMBER;

NUMBER : [0-9]+;

WS : [ \t\n\r]+ -> skip;
