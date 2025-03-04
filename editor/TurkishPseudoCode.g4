grammar TurkishPseudoCode;
pluginDef: id_with_dots (hataExpr | SONRA) 'yap' IDENTIFIER block;
hataExpr: id_with_dots HATA;

statement: loopStatement
         | ifStatement
         | foreachStatement
         | varDeclaration
         | exprStatement
         | block;
functionCall: id_with_dots '(' (expr (',' expr)* )?')';
exprStatement: (functionCall | assignment) ';';

foreachStatement: IDENTIFIER ICINDEKI HER IDENTIFIER ICIN block;

loopStatement: expr IKEN block;

ifStatement: EĞER expr İSE block (DEĞİLSE block)?;

block: '{' statement* '}';

varDeclaration: DEĞİŞKEN assignment ';';

assignment: IDENTIFIER '=' expr;


returnStatement: DÖNDÜR expr;


expr: logicalAndExpr ( VEYA logicalAndExpr )*;

logicalAndExpr: equalityExpr ( VE equalityExpr )*;

equalityExpr: comparisonExpr ( equalityOp comparisonExpr )*;
equalityOp: EŞİTTİR | EŞİT_DEĞİLDİR;
comparisonExpr: additiveExpr ( comparisonOp additiveExpr )*;
comparisonOp: BÜYÜKTÜR | KÜÇÜKTÜR | BÜYÜK_EŞİTTİR | KÜÇÜK_EŞİTTİR;
additiveExpr: multiplicativeExpr ( additiveOp multiplicativeExpr )*;
additiveOp: ARTI|EKSİ;
multiplicativeExpr: unaryExpr ( multiplicativeOp unaryExpr )*;
multiplicativeOp: ÇARPIM | BÖLÜ;
unaryExpr: unaryOp unaryExpr | postfixExpr;
unaryOp: EKSİ | DEĞİL;
postfixExpr: primary ( DEĞİL )?;
id_with_dots: IDENTIFIER ('.' IDENTIFIER)*;
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
EŞİTTİR: '==';
EŞİT_DEĞİLDİR: '!=';
BÜYÜKTÜR:'>';
KÜÇÜKTÜR:'<';
BÜYÜK_EŞİTTİR:'>=';
KÜÇÜK_EŞİTTİR:'<=';
ARTI: '+';
EKSİ: '-';
ÇARPIM: '*';
BÖLÜ: '/';
HATA: 'hatasında';
EĞER: 'eğer';
İSE: 'ise';
DEĞİLSE: 'değilse';
IKEN: 'iken';
FONKSİYON: 'fonksiyon';
DÖNDÜR: 'döndür';
DEĞİŞKEN: 'değişken';

ICINDEKI: 'içindeki';
HER: 'her';
ICIN: 'için';
SONRA: 'sonrasında';

SAYI: [0-9]+ ( '.' [0-9]+ )?;
YAZI: '"' .*? '"';
IDENTIFIER: [a-zA-ZğüşıöçĞÜŞİÖÇ_]([a-zA-ZğüşıöçĞÜŞİÖÇ0-9_$])*;
WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;
MULTILINE_COMMENT: '/*' .*? '*/' -> skip;
