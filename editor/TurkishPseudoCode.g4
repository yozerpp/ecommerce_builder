grammar TurkishPseudoCode;

pluginDef: IDENTIFIER (hataExpr | SONRA) block;

hataExpr: IDENTIFIER HATA;

statement: loopStatement
         | ifStatement
         | foreachStatement
         | varDeclaration ';'
         | exprStatement ( (';' | SONRA statement) )
         | block;
functionCall: IDENTIFIER '(' expr (',' expr)* ')';
exprStatement: functionCall
             | assignment;

foreachStatement: IDENTIFIER ICINDEKI HER IDENTIFIER ICIN block;

loopStatement: expr OLDUĞU_SÜRECE block;

ifStatement: EĞER expr İSE block (DEĞİLSE block)?;

block: '{' statement* '}';

varDeclaration: DEĞİŞKEN assignment;

assignment: IDENTIFIER '=' expr;

yazdir: YAZDIR expr;

returnStatement: DÖNDÜR expr;


expr: logicalAndExpr ( VEYA logicalAndExpr )*;

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
       | exprStatement
       ;

// Lexer Rules
VE: 've';
VEYA: 'veya';
DEĞİL: 'değil';
HATA: 'hatasında';
EĞER: 'eğer';
İSE: 'ise';
DEĞİLSE: 'değilse';
OLDUĞU_SÜRECE: 'olduğu sürece';
FONKSİYON: 'fonksiyon';
DÖNDÜR: 'döndür';
YAZDIR: 'yazdır';
DEĞİŞKEN: 'değişken';

ICINDEKI: 'içindeki';
HER: 'her';
ICIN: 'için';
SONRA: 'sonrasında';

SAYI: [0-9]+ ( '.' [0-9]+ )?;
YAZI: '"' .*? '"';
IDENTIFIER: [a-zA-ZğüşıöçĞÜŞİÖÇ_][a-zA-ZğüşıöçĞÜŞİÖÇ0-9_]*;

WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;
MULTILINE_COMMENT: '/*' .*? '*/' -> skip;
