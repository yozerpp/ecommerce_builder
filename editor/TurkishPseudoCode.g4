grammar TurkishPseudoCode;
işlevTanımı: varlıkİsmi eylemİsmi (hataİfadesi| sonraİfadesi) YAP işlevİsmi gövde;
varlıkİsmi: İSİM;
eylemİsmi: değişken;
işlevİsmi: İSİM;
hataİfadesi: BULUNMA_EKI hataİsmi HATASINDA;
sonraİfadesi: AYRILMA_EKI SONRA;
hataDenklemi: hataİsmi HATASINDA;
hataİsmi: değişken;
ifade: döngüİfadesi
         | eğerİfadesi
         | herBiriİfadesi
         | değişkenTanımı
         | denklemİfadesi
         | dönmeİfadesi
         | gövde;
fonksiyonÇağrımı: değişken '(' (denklem (',' denklem)* )? ')';
denklemİfadesi: (fonksiyonÇağrımı | atama) ';';
ilkelDeğişken: İSİM;
herBiriİfadesi: denklem İÇİNDEKİ HER elementİsmi İÇİN gövde;
koleksiyonİsmi: İSİM;
elementİsmi: İSİM;
döngüİfadesi: koşul IKEN gövde;
eğerİfadesi: EĞER koşul İSE gövde (DEĞİLSE gövde)?;
koşul: denklem;
gövde: '{' ifade* '}';

değişkenTanımı: DEĞ ilkelDeğişken ('=' denklem)? ';';

atama: değişken '=' denklem;


dönmeİfadesi: DÖNDÜR denklem;
denklem: mantıksalVeDenklemi ( VEYA mantıksalVeDenklemi )*;

mantıksalVeDenklemi: eşitlikDenklemi ( VE eşitlikDenklemi )*;

eşitlikDenklemi: karşılaştırmaDenklemi ( eşitlikİşareti karşılaştırmaDenklemi )*;
eşitlikİşareti: EŞİTTİR | EŞİT_DEĞİLDİR;
karşılaştırmaDenklemi: toplamaDenklemi ( karşılaştrımaİşareti toplamaDenklemi )*;
karşılaştrımaİşareti: BÜYÜKTÜR | KÜÇÜKTÜR | BÜYÜK_EŞİTTİR | KÜÇÜK_EŞİTTİR;
toplamaDenklemi: çarpmaDenklemi ( toplamaÇıkarmaİşareti çarpmaDenklemi )*;
toplamaÇıkarmaİşareti: ARTI | EKSİ;
çarpmaDenklemi: tekliDenklem ( çarpmaBölmeİşareti tekliDenklem )*;
çarpmaBölmeİşareti: ÇARPIM | BÖLÜ;
tekliDenklem: eksiİşareti? değilİfadesi; //unaryExpr
eksiİşareti: EKSİ ;
değilİfadesi: değer ( DEĞİL )?; //postfixExpr
üyeErişimiDeğişken: İSİM ('.' İSİM)+;
değişken: ilkelDeğişken |
            üyeErişimiDeğişken;
sabitDeğer : SAYI | YAZI;
değer:  sabitDeğer
       | değişken
       | '(' denklem ')'
       | denklemİfadesi
       ;

// Lexer Rules
YAP: 'yap';
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
HATASINDA: 'hatasında';
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

// Updated EK rules with proper escaping and grouping
BULUNMA_EKI: '\''('ta' | 'te' | 'da' | 'de');
AYRILMA_EKI: '\''('tan' | 'ten' | 'dan' | 'den');
SAHİPLİK_EKİ: '\''('nın' | 'nin' | 'nün' | 'nun' | 'ın' | 'in' | 'un' | 'ün');

SAYI: [0-9]+ ( '.' [0-9]+ )?;
YAZI: '"' .*? '"' | '\'' .*? '\'';
İSİM: [a-zA-ZğüşıöçĞÜŞİÖÇ_]([a-zA-ZğüşıöçĞÜŞİÖÇ0-9_$])*;
BOŞLUK: [ \t\r\n]+ -> skip;
YORUM: '//' ~[\r\n]* -> skip;
ÇOK_SATIR_YORUM: '/*' .*? '*/' -> skip;
