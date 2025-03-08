grammar TurkishPseudoCode;
işlevTanımı: varlıkİsmi eylemİsmi (hataİfadesi| sonraİfadesi) YAP işlevİsmi gövde;
varlıkİsmi: İSİM;
eylemİsmi: İSİM+;
işlevİsmi: İSİM;
hataİfadesi: BULUNMA_EKİ hataİsmi HATASINDA;
sonraİfadesi: AYRILMA_EKİ SONRA;
hataDenklemi: hataİsmi HATASINDA;
hataİsmi: İSİM+;
ifade: döngüİfadesi
         | eğerİfadesi
         | herBiriİfadesi
         | değişkenTanımı
         | denklemİfadesi
         | gövde;
fonksiyonÇağrımı: değişken '(' (denklem (',' denklem)* )? ')';
denklemİfadesi: (fonksiyonÇağrımı | atama) ';';

herBiriİfadesi: koleksiyonİsmi İÇİNDEKİ HER elementİsmi İÇİN gövde;
koleksiyonİsmi: İSİM;
elementİsmi: İSİM;
döngüİfadesi: koşul IKEN gövde;
eğerİfadesi: EĞER koşul İSE gövde (DEĞİLSE gövde)?;
koşul: denklem;
gövde: '{' ifade* '}';

değişkenTanımı: DEĞ ilkelDeğişken ';';

atama: değişken '=' denklem;

ilkelDeğişken: İSİM;
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
tekliDenklem: eksiİşareti tekliDenklem | değilİfadesi;
eksiİşareti: EKSİ ;
değilİfadesi: değer ( DEĞİL )?;
değişken: İSİM (SAHİPLİK_EKİ İSİM)*;
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
BULUNMA_EKİ: '\'' 't' 'a'
               | '\'' 't' 'e'
               | '\'' 'd' 'a'
               | '\'' 'd' 'e';
AYRILMA_EKİ: '\'' 't' 'a' 'n'
               | '\'' 't' 'e' 'n'
               | '\'' 'd' 'a' 'n'
               | '\'' 'd' 'e' 'n';

SAHİPLİK_EKİ: '\'' 'n' 'ı' 'n'
               | '\'' 'n' 'i' 'n'
               | '\'' 'n' 'ü' 'n'
               | '\'' 'n' 'u' 'n'
               | '\'' 'ı' 'n'
               | '\'' 'i' 'n'
               | '\'' 'u' 'n'
               | '\'' 'ü' 'n';

SAYI: [0-9]+ ( '.' [0-9]+ )?;
YAZI: '"' .*? '"' | '\'' .*? '\'';
İSİM: [a-zA-ZğüşıöçĞÜŞİÖÇ_]([a-zA-ZğüşıöçĞÜŞİÖÇ0-9_$])*;
BOŞLUK: [ \t\r\n]+ -> skip;
YORUM: '//' ~[\r\n]* -> skip;
ÇOK_SATIR_YORUM: '/*' .*? '*/' -> skip;
