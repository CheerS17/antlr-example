grammar CALC;

@header {
package cwru.compilers;
}

expr : factor op=('+'|'-') expr | factor;

factor : term op=('*'|'/') factor | term;

term : '(' expr ')' | NUMBER;

NUMBER : [0-9]+;

WS : [ \t\n\r]+ -> skip;

