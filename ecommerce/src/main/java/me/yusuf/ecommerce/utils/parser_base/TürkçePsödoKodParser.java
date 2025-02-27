// Generated from TürkçePsödoKod.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TürkçePsödoKodParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		EĞER=18, İSE=19, DEĞİLSE=20, OLDUĞU_SÜRECE=21, FONKSİYON=22, DÖNDÜR=23, 
		YAZDIR=24, DEĞİŞKEN=25, VE=26, VEYA=27, DEĞİL=28, ICINDEKI=29, HER=30, 
		ICIN=31, SONRA=32, SAYI=33, YAZI=34, IDENTIFIER=35, WS=36, COMMENT=37, 
		MULTILINE_COMMENT=38;
	public static final int
		RULE_prog = 0, RULE_functionDef = 1, RULE_paramList = 2, RULE_statement = 3, 
		RULE_exprStatement = 4, RULE_foreachStatement = 5, RULE_loopStatement = 6, 
		RULE_ifStatement = 7, RULE_block = 8, RULE_varDeclaration = 9, RULE_assignment = 10, 
		RULE_yazdir = 11, RULE_returnStatement = 12, RULE_functionCall = 13, RULE_condition = 14, 
		RULE_expr = 15, RULE_logicalOrExpr = 16, RULE_logicalAndExpr = 17, RULE_equalityExpr = 18, 
		RULE_comparisonExpr = 19, RULE_additiveExpr = 20, RULE_multiplicativeExpr = 21, 
		RULE_unaryExpr = 22, RULE_postfixExpr = 23, RULE_primary = 24;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "functionDef", "paramList", "statement", "exprStatement", "foreachStatement", 
			"loopStatement", "ifStatement", "block", "varDeclaration", "assignment", 
			"yazdir", "returnStatement", "functionCall", "condition", "expr", "logicalOrExpr", 
			"logicalAndExpr", "equalityExpr", "comparisonExpr", "additiveExpr", "multiplicativeExpr", 
			"unaryExpr", "postfixExpr", "primary"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "','", "';'", "'{'", "'}'", "'='", "'=='", "'!='", 
			"'>'", "'<'", "'>='", "'<='", "'+'", "'-'", "'*'", "'/'", "'e\u011Fer'", 
			"'ise'", "'de\u011Filse'", "'oldu\u011Fu s\u00FCrece'", "'fonksiyon'", 
			"'d\u00F6nd\u00FCr'", "'yazd\u0131r'", "'de\u011Fi\u015Fken'", "'ve'", 
			"'veya'", "'de\u011Fil'", "'i\u00E7indeki'", "'her'", "'i\u00E7in'", 
			"'sonra'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "E\u0001ER", "\u0000SE", "DE\u0002\u0003LSE", 
			"OLDU\u0004U_S\u0008RECE", "FONKS\u0005YON", "D\u0001ND\u0004R", "YAZDIR", 
			"DE\u0002\u0003\u0004KEN", "VE", "VEYA", "DE\u0002\u0003L", "ICINDEKI", 
			"HER", "ICIN", "SONRA", "SAYI", "YAZI", "IDENTIFIER", "WS", "COMMENT", 
			"MULTILINE_COMMENT"
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

	@Override
	public String getGrammarFileName() { return "TürkçePsödoKod.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TürkçePsödoKodParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class ProgContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<FunctionDefContext> functionDef() {
			return getRuleContexts(FunctionDefContext.class);
		}
		public FunctionDefContext functionDef(int i) {
			return getRuleContext(FunctionDefContext.class,i);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitProg(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__4) | (1L << T__14) | (1L << EĞER) | (1L << FONKSİYON) | (1L << DÖNDÜR) | (1L << YAZDIR) | (1L << DEĞİŞKEN) | (1L << DEĞİL) | (1L << SAYI) | (1L << YAZI) | (1L << IDENTIFIER))) != 0)) {
				{
				setState(52);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
				case T__4:
				case T__14:
				case EĞER:
				case DÖNDÜR:
				case YAZDIR:
				case DEĞİŞKEN:
				case DEĞİL:
				case SAYI:
				case YAZI:
				case IDENTIFIER:
					{
					setState(50);
					statement();
					}
					break;
				case FONKSİYON:
					{
					setState(51);
					functionDef();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDefContext extends ParserRuleContext {
		public TerminalNode FONKSİYON() { return getToken(TürkçePsödoKodParser.FONKSİYON, 0); }
		public TerminalNode IDENTIFIER() { return getToken(TürkçePsödoKodParser.IDENTIFIER, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ParamListContext paramList() {
			return getRuleContext(ParamListContext.class,0);
		}
		public FunctionDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterFunctionDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitFunctionDef(this);
		}
	}

	public final FunctionDefContext functionDef() throws RecognitionException {
		FunctionDefContext _localctx = new FunctionDefContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_functionDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			match(FONKSİYON);
			setState(58);
			match(T__0);
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IDENTIFIER) {
				{
				setState(59);
				paramList();
				}
			}

			setState(62);
			match(T__1);
			setState(63);
			match(IDENTIFIER);
			setState(64);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParamListContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(TürkçePsödoKodParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(TürkçePsödoKodParser.IDENTIFIER, i);
		}
		public ParamListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_paramList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterParamList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitParamList(this);
		}
	}

	public final ParamListContext paramList() throws RecognitionException {
		ParamListContext _localctx = new ParamListContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_paramList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(IDENTIFIER);
			setState(71);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(67);
				match(T__2);
				setState(68);
				match(IDENTIFIER);
				}
				}
				setState(73);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StatementContext extends ParserRuleContext {
		public LoopStatementContext loopStatement() {
			return getRuleContext(LoopStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public ForeachStatementContext foreachStatement() {
			return getRuleContext(ForeachStatementContext.class,0);
		}
		public VarDeclarationContext varDeclaration() {
			return getRuleContext(VarDeclarationContext.class,0);
		}
		public ExprStatementContext exprStatement() {
			return getRuleContext(ExprStatementContext.class,0);
		}
		public TerminalNode SONRA() { return getToken(TürkçePsödoKodParser.SONRA, 0); }
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitStatement(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_statement);
		try {
			setState(87);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(74);
				loopStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				ifStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(76);
				foreachStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(77);
				varDeclaration();
				setState(78);
				match(T__3);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(80);
				exprStatement();
				{
				setState(84);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__3:
					{
					setState(81);
					match(T__3);
					}
					break;
				case SONRA:
					{
					setState(82);
					match(SONRA);
					setState(83);
					statement();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(86);
				block();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprStatementContext extends ParserRuleContext {
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
		}
		public YazdirContext yazdir() {
			return getRuleContext(YazdirContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public ExprStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exprStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterExprStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitExprStatement(this);
		}
	}

	public final ExprStatementContext exprStatement() throws RecognitionException {
		ExprStatementContext _localctx = new ExprStatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_exprStatement);
		try {
			setState(93);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(89);
				functionCall();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(90);
				assignment();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(91);
				yazdir();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(92);
				returnStatement();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ForeachStatementContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(TürkçePsödoKodParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(TürkçePsödoKodParser.IDENTIFIER, i);
		}
		public TerminalNode ICINDEKI() { return getToken(TürkçePsödoKodParser.ICINDEKI, 0); }
		public TerminalNode HER() { return getToken(TürkçePsödoKodParser.HER, 0); }
		public TerminalNode ICIN() { return getToken(TürkçePsödoKodParser.ICIN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ForeachStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_foreachStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterForeachStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitForeachStatement(this);
		}
	}

	public final ForeachStatementContext foreachStatement() throws RecognitionException {
		ForeachStatementContext _localctx = new ForeachStatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_foreachStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(IDENTIFIER);
			setState(96);
			match(ICINDEKI);
			setState(97);
			match(HER);
			setState(98);
			match(IDENTIFIER);
			setState(99);
			match(ICIN);
			setState(100);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoopStatementContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode OLDUĞU_SÜRECE() { return getToken(TürkçePsödoKodParser.OLDUĞU_SÜRECE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public LoopStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterLoopStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitLoopStatement(this);
		}
	}

	public final LoopStatementContext loopStatement() throws RecognitionException {
		LoopStatementContext _localctx = new LoopStatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_loopStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(102);
			condition();
			setState(103);
			match(OLDUĞU_SÜRECE);
			setState(104);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IfStatementContext extends ParserRuleContext {
		public TerminalNode EĞER() { return getToken(TürkçePsödoKodParser.EĞER, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode İSE() { return getToken(TürkçePsödoKodParser.İSE, 0); }
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode DEĞİLSE() { return getToken(TürkçePsödoKodParser.DEĞİLSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterIfStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitIfStatement(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			match(EĞER);
			setState(107);
			condition();
			setState(108);
			match(İSE);
			setState(109);
			block();
			setState(112);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEĞİLSE) {
				{
				setState(110);
				match(DEĞİLSE);
				setState(111);
				block();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitBlock(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(114);
			match(T__4);
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__4) | (1L << T__14) | (1L << EĞER) | (1L << DÖNDÜR) | (1L << YAZDIR) | (1L << DEĞİŞKEN) | (1L << DEĞİL) | (1L << SAYI) | (1L << YAZI) | (1L << IDENTIFIER))) != 0)) {
				{
				{
				setState(115);
				statement();
				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(121);
			match(T__5);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VarDeclarationContext extends ParserRuleContext {
		public TerminalNode DEĞİŞKEN() { return getToken(TürkçePsödoKodParser.DEĞİŞKEN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(TürkçePsödoKodParser.IDENTIFIER, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VarDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDeclaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterVarDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitVarDeclaration(this);
		}
	}

	public final VarDeclarationContext varDeclaration() throws RecognitionException {
		VarDeclarationContext _localctx = new VarDeclarationContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_varDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			match(DEĞİŞKEN);
			setState(124);
			match(IDENTIFIER);
			setState(125);
			match(T__6);
			setState(126);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AssignmentContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(TürkçePsödoKodParser.IDENTIFIER, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterAssignment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitAssignment(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			match(IDENTIFIER);
			setState(129);
			match(T__6);
			setState(130);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YazdirContext extends ParserRuleContext {
		public TerminalNode YAZDIR() { return getToken(TürkçePsödoKodParser.YAZDIR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public YazdirContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yazdir; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterYazdir(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitYazdir(this);
		}
	}

	public final YazdirContext yazdir() throws RecognitionException {
		YazdirContext _localctx = new YazdirContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_yazdir);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			match(YAZDIR);
			setState(133);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode DÖNDÜR() { return getToken(TürkçePsödoKodParser.DÖNDÜR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterReturnStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitReturnStatement(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(135);
			match(DÖNDÜR);
			setState(136);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionCallContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode IDENTIFIER() { return getToken(TürkçePsödoKodParser.IDENTIFIER, 0); }
		public FunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCall; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterFunctionCall(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitFunctionCall(this);
		}
	}

	public final FunctionCallContext functionCall() throws RecognitionException {
		FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_functionCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			expr();
			setState(143);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__2) {
				{
				{
				setState(139);
				match(T__2);
				setState(140);
				expr();
				}
				}
				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(146);
			match(IDENTIFIER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ConditionContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitCondition(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			expr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExprContext extends ParserRuleContext {
		public LogicalOrExprContext logicalOrExpr() {
			return getRuleContext(LogicalOrExprContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitExpr(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			logicalOrExpr();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalOrExprContext extends ParserRuleContext {
		public List<LogicalAndExprContext> logicalAndExpr() {
			return getRuleContexts(LogicalAndExprContext.class);
		}
		public LogicalAndExprContext logicalAndExpr(int i) {
			return getRuleContext(LogicalAndExprContext.class,i);
		}
		public List<TerminalNode> VEYA() { return getTokens(TürkçePsödoKodParser.VEYA); }
		public TerminalNode VEYA(int i) {
			return getToken(TürkçePsödoKodParser.VEYA, i);
		}
		public LogicalOrExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOrExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterLogicalOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitLogicalOrExpr(this);
		}
	}

	public final LogicalOrExprContext logicalOrExpr() throws RecognitionException {
		LogicalOrExprContext _localctx = new LogicalOrExprContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_logicalOrExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			logicalAndExpr();
			setState(157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VEYA) {
				{
				{
				setState(153);
				match(VEYA);
				setState(154);
				logicalAndExpr();
				}
				}
				setState(159);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LogicalAndExprContext extends ParserRuleContext {
		public List<EqualityExprContext> equalityExpr() {
			return getRuleContexts(EqualityExprContext.class);
		}
		public EqualityExprContext equalityExpr(int i) {
			return getRuleContext(EqualityExprContext.class,i);
		}
		public List<TerminalNode> VE() { return getTokens(TürkçePsödoKodParser.VE); }
		public TerminalNode VE(int i) {
			return getToken(TürkçePsödoKodParser.VE, i);
		}
		public LogicalAndExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalAndExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterLogicalAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitLogicalAndExpr(this);
		}
	}

	public final LogicalAndExprContext logicalAndExpr() throws RecognitionException {
		LogicalAndExprContext _localctx = new LogicalAndExprContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_logicalAndExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			equalityExpr();
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VE) {
				{
				{
				setState(161);
				match(VE);
				setState(162);
				equalityExpr();
				}
				}
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EqualityExprContext extends ParserRuleContext {
		public List<ComparisonExprContext> comparisonExpr() {
			return getRuleContexts(ComparisonExprContext.class);
		}
		public ComparisonExprContext comparisonExpr(int i) {
			return getRuleContext(ComparisonExprContext.class,i);
		}
		public EqualityExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterEqualityExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitEqualityExpr(this);
		}
	}

	public final EqualityExprContext equalityExpr() throws RecognitionException {
		EqualityExprContext _localctx = new EqualityExprContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_equalityExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			comparisonExpr();
			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__7 || _la==T__8) {
				{
				{
				setState(169);
				_la = _input.LA(1);
				if ( !(_la==T__7 || _la==T__8) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(170);
				comparisonExpr();
				}
				}
				setState(175);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ComparisonExprContext extends ParserRuleContext {
		public List<AdditiveExprContext> additiveExpr() {
			return getRuleContexts(AdditiveExprContext.class);
		}
		public AdditiveExprContext additiveExpr(int i) {
			return getRuleContext(AdditiveExprContext.class,i);
		}
		public ComparisonExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterComparisonExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitComparisonExpr(this);
		}
	}

	public final ComparisonExprContext comparisonExpr() throws RecognitionException {
		ComparisonExprContext _localctx = new ComparisonExprContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_comparisonExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			additiveExpr();
			setState(181);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12))) != 0)) {
				{
				{
				setState(177);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__10) | (1L << T__11) | (1L << T__12))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(178);
				additiveExpr();
				}
				}
				setState(183);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AdditiveExprContext extends ParserRuleContext {
		public List<MultiplicativeExprContext> multiplicativeExpr() {
			return getRuleContexts(MultiplicativeExprContext.class);
		}
		public MultiplicativeExprContext multiplicativeExpr(int i) {
			return getRuleContext(MultiplicativeExprContext.class,i);
		}
		public AdditiveExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterAdditiveExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitAdditiveExpr(this);
		}
	}

	public final AdditiveExprContext additiveExpr() throws RecognitionException {
		AdditiveExprContext _localctx = new AdditiveExprContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_additiveExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(184);
			multiplicativeExpr();
			setState(189);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__13 || _la==T__14) {
				{
				{
				setState(185);
				_la = _input.LA(1);
				if ( !(_la==T__13 || _la==T__14) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(186);
				multiplicativeExpr();
				}
				}
				setState(191);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MultiplicativeExprContext extends ParserRuleContext {
		public List<UnaryExprContext> unaryExpr() {
			return getRuleContexts(UnaryExprContext.class);
		}
		public UnaryExprContext unaryExpr(int i) {
			return getRuleContext(UnaryExprContext.class,i);
		}
		public MultiplicativeExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterMultiplicativeExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitMultiplicativeExpr(this);
		}
	}

	public final MultiplicativeExprContext multiplicativeExpr() throws RecognitionException {
		MultiplicativeExprContext _localctx = new MultiplicativeExprContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_multiplicativeExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(192);
			unaryExpr();
			setState(197);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__15 || _la==T__16) {
				{
				{
				setState(193);
				_la = _input.LA(1);
				if ( !(_la==T__15 || _la==T__16) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(194);
				unaryExpr();
				}
				}
				setState(199);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UnaryExprContext extends ParserRuleContext {
		public UnaryExprContext unaryExpr() {
			return getRuleContext(UnaryExprContext.class,0);
		}
		public TerminalNode DEĞİL() { return getToken(TürkçePsödoKodParser.DEĞİL, 0); }
		public PostfixExprContext postfixExpr() {
			return getRuleContext(PostfixExprContext.class,0);
		}
		public UnaryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterUnaryExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitUnaryExpr(this);
		}
	}

	public final UnaryExprContext unaryExpr() throws RecognitionException {
		UnaryExprContext _localctx = new UnaryExprContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_unaryExpr);
		int _la;
		try {
			setState(203);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__14:
			case DEĞİL:
				enterOuterAlt(_localctx, 1);
				{
				setState(200);
				_la = _input.LA(1);
				if ( !(_la==T__14 || _la==DEĞİL) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(201);
				unaryExpr();
				}
				break;
			case T__0:
			case SAYI:
			case YAZI:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(202);
				postfixExpr();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PostfixExprContext extends ParserRuleContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public TerminalNode DEĞİL() { return getToken(TürkçePsödoKodParser.DEĞİL, 0); }
		public PostfixExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterPostfixExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitPostfixExpr(this);
		}
	}

	public final PostfixExprContext postfixExpr() throws RecognitionException {
		PostfixExprContext _localctx = new PostfixExprContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_postfixExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			primary();
			setState(207);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEĞİL) {
				{
				setState(206);
				match(DEĞİL);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimaryContext extends ParserRuleContext {
		public TerminalNode SAYI() { return getToken(TürkçePsödoKodParser.SAYI, 0); }
		public TerminalNode YAZI() { return getToken(TürkçePsödoKodParser.YAZI, 0); }
		public TerminalNode IDENTIFIER() { return getToken(TürkçePsödoKodParser.IDENTIFIER, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).enterPrimary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof TürkçePsödoKodListener ) ((TürkçePsödoKodListener)listener).exitPrimary(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_primary);
		try {
			setState(216);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SAYI:
				enterOuterAlt(_localctx, 1);
				{
				setState(209);
				match(SAYI);
				}
				break;
			case YAZI:
				enterOuterAlt(_localctx, 2);
				{
				setState(210);
				match(YAZI);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 3);
				{
				setState(211);
				match(IDENTIFIER);
				}
				break;
			case T__0:
				enterOuterAlt(_localctx, 4);
				{
				setState(212);
				match(T__0);
				setState(213);
				expr();
				setState(214);
				match(T__1);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3(\u00dd\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\3\2\3\2\7\2\67\n\2\f\2\16\2:\13\2\3\3\3\3\3\3\5\3?\n\3\3\3"+
		"\3\3\3\3\3\3\3\4\3\4\3\4\7\4H\n\4\f\4\16\4K\13\4\3\5\3\5\3\5\3\5\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\5\5W\n\5\3\5\5\5Z\n\5\3\6\3\6\3\6\3\6\5\6`\n\6\3\7"+
		"\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\5\ts"+
		"\n\t\3\n\3\n\7\nw\n\n\f\n\16\nz\13\n\3\n\3\n\3\13\3\13\3\13\3\13\3\13"+
		"\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\7\17\u0090"+
		"\n\17\f\17\16\17\u0093\13\17\3\17\3\17\3\20\3\20\3\21\3\21\3\22\3\22\3"+
		"\22\7\22\u009e\n\22\f\22\16\22\u00a1\13\22\3\23\3\23\3\23\7\23\u00a6\n"+
		"\23\f\23\16\23\u00a9\13\23\3\24\3\24\3\24\7\24\u00ae\n\24\f\24\16\24\u00b1"+
		"\13\24\3\25\3\25\3\25\7\25\u00b6\n\25\f\25\16\25\u00b9\13\25\3\26\3\26"+
		"\3\26\7\26\u00be\n\26\f\26\16\26\u00c1\13\26\3\27\3\27\3\27\7\27\u00c6"+
		"\n\27\f\27\16\27\u00c9\13\27\3\30\3\30\3\30\5\30\u00ce\n\30\3\31\3\31"+
		"\5\31\u00d2\n\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32\u00db\n\32\3"+
		"\32\2\2\33\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\2\7\3"+
		"\2\n\13\3\2\f\17\3\2\20\21\3\2\22\23\4\2\21\21\36\36\2\u00de\28\3\2\2"+
		"\2\4;\3\2\2\2\6D\3\2\2\2\bY\3\2\2\2\n_\3\2\2\2\fa\3\2\2\2\16h\3\2\2\2"+
		"\20l\3\2\2\2\22t\3\2\2\2\24}\3\2\2\2\26\u0082\3\2\2\2\30\u0086\3\2\2\2"+
		"\32\u0089\3\2\2\2\34\u008c\3\2\2\2\36\u0096\3\2\2\2 \u0098\3\2\2\2\"\u009a"+
		"\3\2\2\2$\u00a2\3\2\2\2&\u00aa\3\2\2\2(\u00b2\3\2\2\2*\u00ba\3\2\2\2,"+
		"\u00c2\3\2\2\2.\u00cd\3\2\2\2\60\u00cf\3\2\2\2\62\u00da\3\2\2\2\64\67"+
		"\5\b\5\2\65\67\5\4\3\2\66\64\3\2\2\2\66\65\3\2\2\2\67:\3\2\2\28\66\3\2"+
		"\2\289\3\2\2\29\3\3\2\2\2:8\3\2\2\2;<\7\30\2\2<>\7\3\2\2=?\5\6\4\2>=\3"+
		"\2\2\2>?\3\2\2\2?@\3\2\2\2@A\7\4\2\2AB\7%\2\2BC\5\22\n\2C\5\3\2\2\2DI"+
		"\7%\2\2EF\7\5\2\2FH\7%\2\2GE\3\2\2\2HK\3\2\2\2IG\3\2\2\2IJ\3\2\2\2J\7"+
		"\3\2\2\2KI\3\2\2\2LZ\5\16\b\2MZ\5\20\t\2NZ\5\f\7\2OP\5\24\13\2PQ\7\6\2"+
		"\2QZ\3\2\2\2RV\5\n\6\2SW\7\6\2\2TU\7\"\2\2UW\5\b\5\2VS\3\2\2\2VT\3\2\2"+
		"\2WZ\3\2\2\2XZ\5\22\n\2YL\3\2\2\2YM\3\2\2\2YN\3\2\2\2YO\3\2\2\2YR\3\2"+
		"\2\2YX\3\2\2\2Z\t\3\2\2\2[`\5\34\17\2\\`\5\26\f\2]`\5\30\r\2^`\5\32\16"+
		"\2_[\3\2\2\2_\\\3\2\2\2_]\3\2\2\2_^\3\2\2\2`\13\3\2\2\2ab\7%\2\2bc\7\37"+
		"\2\2cd\7 \2\2de\7%\2\2ef\7!\2\2fg\5\22\n\2g\r\3\2\2\2hi\5\36\20\2ij\7"+
		"\27\2\2jk\5\22\n\2k\17\3\2\2\2lm\7\24\2\2mn\5\36\20\2no\7\25\2\2or\5\22"+
		"\n\2pq\7\26\2\2qs\5\22\n\2rp\3\2\2\2rs\3\2\2\2s\21\3\2\2\2tx\7\7\2\2u"+
		"w\5\b\5\2vu\3\2\2\2wz\3\2\2\2xv\3\2\2\2xy\3\2\2\2y{\3\2\2\2zx\3\2\2\2"+
		"{|\7\b\2\2|\23\3\2\2\2}~\7\33\2\2~\177\7%\2\2\177\u0080\7\t\2\2\u0080"+
		"\u0081\5 \21\2\u0081\25\3\2\2\2\u0082\u0083\7%\2\2\u0083\u0084\7\t\2\2"+
		"\u0084\u0085\5 \21\2\u0085\27\3\2\2\2\u0086\u0087\7\32\2\2\u0087\u0088"+
		"\5 \21\2\u0088\31\3\2\2\2\u0089\u008a\7\31\2\2\u008a\u008b\5 \21\2\u008b"+
		"\33\3\2\2\2\u008c\u0091\5 \21\2\u008d\u008e\7\5\2\2\u008e\u0090\5 \21"+
		"\2\u008f\u008d\3\2\2\2\u0090\u0093\3\2\2\2\u0091\u008f\3\2\2\2\u0091\u0092"+
		"\3\2\2\2\u0092\u0094\3\2\2\2\u0093\u0091\3\2\2\2\u0094\u0095\7%\2\2\u0095"+
		"\35\3\2\2\2\u0096\u0097\5 \21\2\u0097\37\3\2\2\2\u0098\u0099\5\"\22\2"+
		"\u0099!\3\2\2\2\u009a\u009f\5$\23\2\u009b\u009c\7\35\2\2\u009c\u009e\5"+
		"$\23\2\u009d\u009b\3\2\2\2\u009e\u00a1\3\2\2\2\u009f\u009d\3\2\2\2\u009f"+
		"\u00a0\3\2\2\2\u00a0#\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2\u00a7\5&\24\2"+
		"\u00a3\u00a4\7\34\2\2\u00a4\u00a6\5&\24\2\u00a5\u00a3\3\2\2\2\u00a6\u00a9"+
		"\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8%\3\2\2\2\u00a9"+
		"\u00a7\3\2\2\2\u00aa\u00af\5(\25\2\u00ab\u00ac\t\2\2\2\u00ac\u00ae\5("+
		"\25\2\u00ad\u00ab\3\2\2\2\u00ae\u00b1\3\2\2\2\u00af\u00ad\3\2\2\2\u00af"+
		"\u00b0\3\2\2\2\u00b0\'\3\2\2\2\u00b1\u00af\3\2\2\2\u00b2\u00b7\5*\26\2"+
		"\u00b3\u00b4\t\3\2\2\u00b4\u00b6\5*\26\2\u00b5\u00b3\3\2\2\2\u00b6\u00b9"+
		"\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8)\3\2\2\2\u00b9"+
		"\u00b7\3\2\2\2\u00ba\u00bf\5,\27\2\u00bb\u00bc\t\4\2\2\u00bc\u00be\5,"+
		"\27\2\u00bd\u00bb\3\2\2\2\u00be\u00c1\3\2\2\2\u00bf\u00bd\3\2\2\2\u00bf"+
		"\u00c0\3\2\2\2\u00c0+\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c2\u00c7\5.\30\2"+
		"\u00c3\u00c4\t\5\2\2\u00c4\u00c6\5.\30\2\u00c5\u00c3\3\2\2\2\u00c6\u00c9"+
		"\3\2\2\2\u00c7\u00c5\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8-\3\2\2\2\u00c9"+
		"\u00c7\3\2\2\2\u00ca\u00cb\t\6\2\2\u00cb\u00ce\5.\30\2\u00cc\u00ce\5\60"+
		"\31\2\u00cd\u00ca\3\2\2\2\u00cd\u00cc\3\2\2\2\u00ce/\3\2\2\2\u00cf\u00d1"+
		"\5\62\32\2\u00d0\u00d2\7\36\2\2\u00d1\u00d0\3\2\2\2\u00d1\u00d2\3\2\2"+
		"\2\u00d2\61\3\2\2\2\u00d3\u00db\7#\2\2\u00d4\u00db\7$\2\2\u00d5\u00db"+
		"\7%\2\2\u00d6\u00d7\7\3\2\2\u00d7\u00d8\5 \21\2\u00d8\u00d9\7\4\2\2\u00d9"+
		"\u00db\3\2\2\2\u00da\u00d3\3\2\2\2\u00da\u00d4\3\2\2\2\u00da\u00d5\3\2"+
		"\2\2\u00da\u00d6\3\2\2\2\u00db\63\3\2\2\2\25\668>IVY_rx\u0091\u009f\u00a7"+
		"\u00af\u00b7\u00bf\u00c7\u00cd\u00d1\u00da";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}