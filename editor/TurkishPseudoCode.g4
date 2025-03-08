grammar TurkishPseudoCode;
pluginDef: değişkenErişimi (hataExpr | SONRA) 'yap' IDENTIFIER block;
hataExpr: değişkenErişimi HATA;

statement: döngüİfadesi
         | eğerİfadesi
         | foreachStatement
         | değişkenTanımı
         | exprStatement
         | block;
functionCall: değişkenErişimi '(' (denklem (',' denklem)* )? ')';
exprStatement: (functionCall | atama) ';';

foreachStatement: koleksiyonİsmi İÇİNDEKİ HER elementİsmi İÇİN block;
koleksiyonİsmi: IDENTIFIER;
elementİsmi: IDENTIFIER;
döngüİfadesi: koşul IKEN block;
eğerİfadesi: EĞER koşul İSE block (DEĞİLSE block)?;
koşul: denklem;
block: '{' statement* '}';

değişkenTanımı: DEĞ atama ';';

atama: değişken '=' denklem;
değişken: IDENTIFIER;
dönmeİfadesi: DÖNDÜR denklem;
denklem: logicalAndExpr ( VEYA logicalAndExpr )*;

logicalAndExpr: equalityExpr ( VE equalityExpr )*;

equalityExpr: comparisonExpr ( equalityOp comparisonExpr )*;
equalityOp: EŞİTTİR | EŞİT_DEĞİLDİR;
comparisonExpr: additiveExpr ( comparisonOp additiveExpr )*;
comparisonOp: BÜYÜKTÜR | KÜÇÜKTÜR | BÜYÜK_EŞİTTİR | KÜÇÜK_EŞİTTİR;
additiveExpr: multiplicativeExpr ( additiveOp multiplicativeExpr )*;
additiveOp: ARTI | EKSİ;
multiplicativeExpr: unaryExpr ( multiplicativeOp unaryExpr )*;
multiplicativeOp: ÇARPIM | BÖLÜ;
unaryExpr: unaryOp unaryExpr | postfixExpr;
unaryOp: EKSİ | DEĞİL;
postfixExpr: primary ( DEĞİL )?;
değişkenErişimi: IDENTIFIER (APOSTROPHE_NIN IDENTIFIER)*;
primary: SAYI
       | YAZI
       | değişkenErişimi
       | '(' denklem ')'
       | exprStatement
       ;

// Lexer Rules
VE: 've';
VEYA: 'veya';
DEĞİL: 'değil';
EŞİTTİR: '==';
EŞİT_DEĞİLDİR: '!=';
BÜYÜKTÜR: '>';
KÜÇÜKTÜR: '<';
BÜYÜK_EŞİTTİR: '>=';
KÜÇÜK_EŞİTTİR: '<=';
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
DEĞ: 'değ';

İÇİNDEKİ: 'içindeki';
HER: 'her';
İÇİN: 'için';
SONRA: 'sonrasında';

APOSTROPHE_NIN: '\'' 'n' 'ı' 'n'
               | '\'' 'n' 'i' 'n'
               | '\'' 'n' 'ü' 'n'
               | '\'' 'n' 'u' 'n';

SAYI: [0-9]+ ( '.' [0-9]+ )?;
YAZI: '"' .*? '"';
IDENTIFIER: [a-zA-ZğüşıöçĞÜŞİÖÇ_]([a-zA-ZğüşıöçĞÜŞİÖÇ0-9_$])*;
WS: [ \t\r\n]+ -> skip;
COMMENT: '//' ~[\r\n]* -> skip;
MULTILINE_COMMENT: '/*' .*? '*/' -> skip;
