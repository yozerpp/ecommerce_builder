// Generated from ./TurkishPseudoCode.g4 by ANTLR 4.9.2
package me.yusuf.ecommerce_builder.transpiler.generated;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TurkishPseudoCodeLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		VE=10, VEYA=11, DEĞİL=12, EŞİTTİR=13, EŞİT_DEĞİLDİR=14, BÜYÜKTÜR=15, KÜÇÜKTÜR=16, 
		BÜYÜK_EŞİTTİR=17, KÜÇÜK_EŞİTTİR=18, ARTI=19, EKSİ=20, ÇARPIM=21, BÖLÜ=22, 
		HATA=23, EĞER=24, İSE=25, DEĞİLSE=26, OLDUĞU_SÜRECE=27, FONKSİYON=28, 
		DÖNDÜR=29, YAZDIR=30, DEĞİŞKEN=31, ICINDEKI=32, HER=33, ICIN=34, SONRA=35, 
		SAYI=36, YAZI=37, IDENTIFIER=38, WS=39, COMMENT=40, MULTILINE_COMMENT=41;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"VE", "VEYA", "DEĞİL", "EŞİTTİR", "EŞİT_DEĞİLDİR", "BÜYÜKTÜR", "KÜÇÜKTÜR", 
			"BÜYÜK_EŞİTTİR", "KÜÇÜK_EŞİTTİR", "ARTI", "EKSİ", "ÇARPIM", "BÖLÜ", "HATA", 
			"EĞER", "İSE", "DEĞİLSE", "OLDUĞU_SÜRECE", "FONKSİYON", "DÖNDÜR", "YAZDIR", 
			"DEĞİŞKEN", "ICINDEKI", "HER", "ICIN", "SONRA", "SAYI", "YAZI", "IDENTIFIER", 
			"WS", "COMMENT", "MULTILINE_COMMENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'yap'", "'('", "','", "')'", "';'", "'{'", "'}'", "'='", "'.'", 
			"'ve'", "'veya'", "'de\u011Fil'", "'=='", "'!='", "'>'", "'<'", "'>='", 
			"'<='", "'+'", "'-'", "'*'", "'/'", "'hatas\u0131nda'", "'e\u011Fer'", 
			"'ise'", "'de\u011Filse'", "'oldu\u011Fu s\u00FCrece'", "'fonksiyon'", 
			"'d\u00F6nd\u00FCr'", "'yazd\u0131r'", "'de\u011Fi\u015Fken'", "'i\u00E7indeki'", 
			"'her'", "'i\u00E7in'", "'sonras\u0131nda'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "VE", "VEYA", 
			"DE\u0002\u0003L", "E\u0001\u0002TT\u0005R", "E\u0001\u0002T_DE\u0007\u0008LD\u000BR", 
			"B\u0001Y\u0003KT\u0006R", "K\u0001\u0002\u0003KT\u0006R", "B\u0001Y\u0003K_E\u0007\u0008TT\u000BR", 
			"K\u0001\u0002\u0003K_E\u0007\u0008TT\u000BR", "ARTI", "EKS\u0003", "\u0000ARPIM", 
			"B\u0001L\u0003", "HATA", "E\u0001ER", "\u0000SE", "DE\u0002\u0003LSE", 
			"OLDU\u0004U_S\u0008RECE", "FONKS\u0005YON", "D\u0001ND\u0004R", "YAZDIR", 
			"DE\u0002\u0003\u0004KEN", "ICINDEKI", "HER", "ICIN", "SONRA", "SAYI", 
			"YAZI", "IDENTIFIER", "WS", "COMMENT", "MULTILINE_COMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public TurkishPseudoCodeLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "TurkishPseudoCode.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2+\u0133\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\3\2\3\2"+
		"\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3"+
		"\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23"+
		"\3\24\3\24\3\25\3\25\3\26\3\26\3\27\3\27\3\30\3\30\3\30\3\30\3\30\3\30"+
		"\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\33"+
		"\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34"+
		"\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\35"+
		"\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37"+
		"\3\37\3\37\3\37\3 \3 \3 \3 \3 \3 \3 \3 \3 \3!\3!\3!\3!\3!\3!\3!\3!\3!"+
		"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3%\6"+
		"%\u00f8\n%\r%\16%\u00f9\3%\3%\6%\u00fe\n%\r%\16%\u00ff\5%\u0102\n%\3&"+
		"\3&\7&\u0106\n&\f&\16&\u0109\13&\3&\3&\3\'\3\'\7\'\u010f\n\'\f\'\16\'"+
		"\u0112\13\'\3(\6(\u0115\n(\r(\16(\u0116\3(\3(\3)\3)\3)\3)\7)\u011f\n)"+
		"\f)\16)\u0122\13)\3)\3)\3*\3*\3*\3*\7*\u012a\n*\f*\16*\u012d\13*\3*\3"+
		"*\3*\3*\3*\4\u0107\u012b\2+\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M(O)Q*S+\3\2\7\3\2\62;\16"+
		"\2C\\aac|\u00c9\u00c9\u00d8\u00d8\u00de\u00de\u00e9\u00e9\u00f8\u00f8"+
		"\u00fe\u00fe\u0120\u0121\u0132\u0133\u0160\u0161\20\2&&\62;C\\aac|\u00c9"+
		"\u00c9\u00d8\u00d8\u00de\u00de\u00e9\u00e9\u00f8\u00f8\u00fe\u00fe\u0120"+
		"\u0121\u0132\u0133\u0160\u0161\5\2\13\f\17\17\"\"\4\2\f\f\17\17\2\u013a"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2"+
		"\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3"+
		"\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2"+
		"\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\3"+
		"U\3\2\2\2\5Y\3\2\2\2\7[\3\2\2\2\t]\3\2\2\2\13_\3\2\2\2\ra\3\2\2\2\17c"+
		"\3\2\2\2\21e\3\2\2\2\23g\3\2\2\2\25i\3\2\2\2\27l\3\2\2\2\31q\3\2\2\2\33"+
		"w\3\2\2\2\35z\3\2\2\2\37}\3\2\2\2!\177\3\2\2\2#\u0081\3\2\2\2%\u0084\3"+
		"\2\2\2\'\u0087\3\2\2\2)\u0089\3\2\2\2+\u008b\3\2\2\2-\u008d\3\2\2\2/\u008f"+
		"\3\2\2\2\61\u0099\3\2\2\2\63\u009e\3\2\2\2\65\u00a2\3\2\2\2\67\u00aa\3"+
		"\2\2\29\u00b8\3\2\2\2;\u00c2\3\2\2\2=\u00c9\3\2\2\2?\u00d0\3\2\2\2A\u00d9"+
		"\3\2\2\2C\u00e2\3\2\2\2E\u00e6\3\2\2\2G\u00eb\3\2\2\2I\u00f7\3\2\2\2K"+
		"\u0103\3\2\2\2M\u010c\3\2\2\2O\u0114\3\2\2\2Q\u011a\3\2\2\2S\u0125\3\2"+
		"\2\2UV\7{\2\2VW\7c\2\2WX\7r\2\2X\4\3\2\2\2YZ\7*\2\2Z\6\3\2\2\2[\\\7.\2"+
		"\2\\\b\3\2\2\2]^\7+\2\2^\n\3\2\2\2_`\7=\2\2`\f\3\2\2\2ab\7}\2\2b\16\3"+
		"\2\2\2cd\7\177\2\2d\20\3\2\2\2ef\7?\2\2f\22\3\2\2\2gh\7\60\2\2h\24\3\2"+
		"\2\2ij\7x\2\2jk\7g\2\2k\26\3\2\2\2lm\7x\2\2mn\7g\2\2no\7{\2\2op\7c\2\2"+
		"p\30\3\2\2\2qr\7f\2\2rs\7g\2\2st\7\u0121\2\2tu\7k\2\2uv\7n\2\2v\32\3\2"+
		"\2\2wx\7?\2\2xy\7?\2\2y\34\3\2\2\2z{\7#\2\2{|\7?\2\2|\36\3\2\2\2}~\7@"+
		"\2\2~ \3\2\2\2\177\u0080\7>\2\2\u0080\"\3\2\2\2\u0081\u0082\7@\2\2\u0082"+
		"\u0083\7?\2\2\u0083$\3\2\2\2\u0084\u0085\7>\2\2\u0085\u0086\7?\2\2\u0086"+
		"&\3\2\2\2\u0087\u0088\7-\2\2\u0088(\3\2\2\2\u0089\u008a\7/\2\2\u008a*"+
		"\3\2\2\2\u008b\u008c\7,\2\2\u008c,\3\2\2\2\u008d\u008e\7\61\2\2\u008e"+
		".\3\2\2\2\u008f\u0090\7j\2\2\u0090\u0091\7c\2\2\u0091\u0092\7v\2\2\u0092"+
		"\u0093\7c\2\2\u0093\u0094\7u\2\2\u0094\u0095\7\u0133\2\2\u0095\u0096\7"+
		"p\2\2\u0096\u0097\7f\2\2\u0097\u0098\7c\2\2\u0098\60\3\2\2\2\u0099\u009a"+
		"\7g\2\2\u009a\u009b\7\u0121\2\2\u009b\u009c\7g\2\2\u009c\u009d\7t\2\2"+
		"\u009d\62\3\2\2\2\u009e\u009f\7k\2\2\u009f\u00a0\7u\2\2\u00a0\u00a1\7"+
		"g\2\2\u00a1\64\3\2\2\2\u00a2\u00a3\7f\2\2\u00a3\u00a4\7g\2\2\u00a4\u00a5"+
		"\7\u0121\2\2\u00a5\u00a6\7k\2\2\u00a6\u00a7\7n\2\2\u00a7\u00a8\7u\2\2"+
		"\u00a8\u00a9\7g\2\2\u00a9\66\3\2\2\2\u00aa\u00ab\7q\2\2\u00ab\u00ac\7"+
		"n\2\2\u00ac\u00ad\7f\2\2\u00ad\u00ae\7w\2\2\u00ae\u00af\7\u0121\2\2\u00af"+
		"\u00b0\7w\2\2\u00b0\u00b1\7\"\2\2\u00b1\u00b2\7u\2\2\u00b2\u00b3\7\u00fe"+
		"\2\2\u00b3\u00b4\7t\2\2\u00b4\u00b5\7g\2\2\u00b5\u00b6\7e\2\2\u00b6\u00b7"+
		"\7g\2\2\u00b78\3\2\2\2\u00b8\u00b9\7h\2\2\u00b9\u00ba\7q\2\2\u00ba\u00bb"+
		"\7p\2\2\u00bb\u00bc\7m\2\2\u00bc\u00bd\7u\2\2\u00bd\u00be\7k\2\2\u00be"+
		"\u00bf\7{\2\2\u00bf\u00c0\7q\2\2\u00c0\u00c1\7p\2\2\u00c1:\3\2\2\2\u00c2"+
		"\u00c3\7f\2\2\u00c3\u00c4\7\u00f8\2\2\u00c4\u00c5\7p\2\2\u00c5\u00c6\7"+
		"f\2\2\u00c6\u00c7\7\u00fe\2\2\u00c7\u00c8\7t\2\2\u00c8<\3\2\2\2\u00c9"+
		"\u00ca\7{\2\2\u00ca\u00cb\7c\2\2\u00cb\u00cc\7|\2\2\u00cc\u00cd\7f\2\2"+
		"\u00cd\u00ce\7\u0133\2\2\u00ce\u00cf\7t\2\2\u00cf>\3\2\2\2\u00d0\u00d1"+
		"\7f\2\2\u00d1\u00d2\7g\2\2\u00d2\u00d3\7\u0121\2\2\u00d3\u00d4\7k\2\2"+
		"\u00d4\u00d5\7\u0161\2\2\u00d5\u00d6\7m\2\2\u00d6\u00d7\7g\2\2\u00d7\u00d8"+
		"\7p\2\2\u00d8@\3\2\2\2\u00d9\u00da\7k\2\2\u00da\u00db\7\u00e9\2\2\u00db"+
		"\u00dc\7k\2\2\u00dc\u00dd\7p\2\2\u00dd\u00de\7f\2\2\u00de\u00df\7g\2\2"+
		"\u00df\u00e0\7m\2\2\u00e0\u00e1\7k\2\2\u00e1B\3\2\2\2\u00e2\u00e3\7j\2"+
		"\2\u00e3\u00e4\7g\2\2\u00e4\u00e5\7t\2\2\u00e5D\3\2\2\2\u00e6\u00e7\7"+
		"k\2\2\u00e7\u00e8\7\u00e9\2\2\u00e8\u00e9\7k\2\2\u00e9\u00ea\7p\2\2\u00ea"+
		"F\3\2\2\2\u00eb\u00ec\7u\2\2\u00ec\u00ed\7q\2\2\u00ed\u00ee\7p\2\2\u00ee"+
		"\u00ef\7t\2\2\u00ef\u00f0\7c\2\2\u00f0\u00f1\7u\2\2\u00f1\u00f2\7\u0133"+
		"\2\2\u00f2\u00f3\7p\2\2\u00f3\u00f4\7f\2\2\u00f4\u00f5\7c\2\2\u00f5H\3"+
		"\2\2\2\u00f6\u00f8\t\2\2\2\u00f7\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9"+
		"\u00f7\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa\u0101\3\2\2\2\u00fb\u00fd\7\60"+
		"\2\2\u00fc\u00fe\t\2\2\2\u00fd\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff"+
		"\u00fd\3\2\2\2\u00ff\u0100\3\2\2\2\u0100\u0102\3\2\2\2\u0101\u00fb\3\2"+
		"\2\2\u0101\u0102\3\2\2\2\u0102J\3\2\2\2\u0103\u0107\7$\2\2\u0104\u0106"+
		"\13\2\2\2\u0105\u0104\3\2\2\2\u0106\u0109\3\2\2\2\u0107\u0108\3\2\2\2"+
		"\u0107\u0105\3\2\2\2\u0108\u010a\3\2\2\2\u0109\u0107\3\2\2\2\u010a\u010b"+
		"\7$\2\2\u010bL\3\2\2\2\u010c\u0110\t\3\2\2\u010d\u010f\t\4\2\2\u010e\u010d"+
		"\3\2\2\2\u010f\u0112\3\2\2\2\u0110\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111"+
		"N\3\2\2\2\u0112\u0110\3\2\2\2\u0113\u0115\t\5\2\2\u0114\u0113\3\2\2\2"+
		"\u0115\u0116\3\2\2\2\u0116\u0114\3\2\2\2\u0116\u0117\3\2\2\2\u0117\u0118"+
		"\3\2\2\2\u0118\u0119\b(\2\2\u0119P\3\2\2\2\u011a\u011b\7\61\2\2\u011b"+
		"\u011c\7\61\2\2\u011c\u0120\3\2\2\2\u011d\u011f\n\6\2\2\u011e\u011d\3"+
		"\2\2\2\u011f\u0122\3\2\2\2\u0120\u011e\3\2\2\2\u0120\u0121\3\2\2\2\u0121"+
		"\u0123\3\2\2\2\u0122\u0120\3\2\2\2\u0123\u0124\b)\2\2\u0124R\3\2\2\2\u0125"+
		"\u0126\7\61\2\2\u0126\u0127\7,\2\2\u0127\u012b\3\2\2\2\u0128\u012a\13"+
		"\2\2\2\u0129\u0128\3\2\2\2\u012a\u012d\3\2\2\2\u012b\u012c\3\2\2\2\u012b"+
		"\u0129\3\2\2\2\u012c\u012e\3\2\2\2\u012d\u012b\3\2\2\2\u012e\u012f\7,"+
		"\2\2\u012f\u0130\7\61\2\2\u0130\u0131\3\2\2\2\u0131\u0132\b*\2\2\u0132"+
		"T\3\2\2\2\13\2\u00f9\u00ff\u0101\u0107\u0110\u0116\u0120\u012b\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}