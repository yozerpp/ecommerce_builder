grammar TürkçePsödoKod;

prog: (statement | functionDef)*;

functionDef: FONKSİYON '(' paramList? ')' IDENTIFIER block;

paramList: IDENTIFIER (',' IDENTIFIER)*;

statement: loopStatement
         | ifStatement
         | foreachStatement
         | varDeclaration ';'
         | exprStatement ( (';' | SONRA statement) )
         | block;

exprStatement: functionCall
             | assignment
             | yazdir
             | returnStatement;

foreachStatement: IDENTIFIER ICINDEKI HER IDENTIFIER ICIN block;

loopStatement: condition OLDUĞU_SÜRECE block;

ifStatement: EĞER condition İSE block (DEĞİLSE block)?;

block: '{' statement* '}';

varDeclaration: DEĞİŞKEN IDENTIFIER '=' expr;

assignment: IDENTIFIER '=' expr;

yazdir: YAZDIR expr;

returnStatement: DÖNDÜR expr;

functionCall: expr (',' expr)* IDENTIFIER;

condition: expr;

expr: logicalOrExpr;

logicalOrExpr: logicalAndExpr ( VEYA logicalAndExpr )*;

logicalAndExpr: equalityExpr ( VE equalityExpr )*;

equalityExpr: comparisonExpr ( ('==' | '!=') comparisonExpr )*;

comparisonExpr: additiveExpr ( ('>' | '<' | '>=' | '<=' ) additiveExpr )*;

additiveExpr: multiplicativeExpr ( ( '+' | '-' ) multiplicativeExpr )*;

multiplicativeExpr: unaryExpr ( ( '*' | '/' ) unaryExpr )*;

unaryExpr: ( '-' | DEĞİL ) unaryExpr | postfixExpr;

postfixExpr: primary ( DEĞİL )?;

primary: SAYI
       | YAZI
       | IDENTIFIER
       | '(' expr ')'
       ;

// Lexer Rules
EĞER: 'eğer';
İSE: 'ise';
DEĞİLSE: 'değilse';
OLDUĞU_SÜRECE: 'olduğu sürece';
FONKSİYON: 'fonksiyon';
DÖNDÜR: 'döndür';
YAZDIR: 'yazdır';
DEĞİŞKEN: 'değişken';
VE: 've';
VEYA: 'veya';
DEĞİL: 'değil';
ICINDEKI: 'içindeki';
HER: 'her';
ICIN: 'için';
SONRA: 'sonra';

SAYI: [0-9]+ ( '.' [0-9]+ )?;
YAZI: '"' .*? '"';
IDENTIFIER: [a-zA-ZğüşıöçĞÜŞİÖÇ_][a-zA-ZğüşıöçĞÜŞİÖÇ0-9_]*;

WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;
MULTILINE_COMMENT: '/*' .*? '*/' -> skip;
