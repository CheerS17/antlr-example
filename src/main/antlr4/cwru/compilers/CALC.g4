grammar CALC;

@header {
package cwru.compilers;
}

expr : expr op=('+'|'-') term | term;

term : term op=('*'|'/') factor | factor;

factor : '(' expr ')' | NUMBER;

NUMBER : [0-9]+;

WS : [ \t\n\r]+ -> skip;

