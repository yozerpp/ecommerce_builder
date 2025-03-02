// Generated from ./TurkishPseudoCode.g4 by ANTLR 4.9.2
package me.yusuf.ecommerce_builder.transpiler.generated;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class TurkishPseudoCodeParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		HATA=18, EĞER=19, İSE=20, DEĞİLSE=21, OLDUĞU_SÜRECE=22, FONKSİYON=23, 
		DÖNDÜR=24, YAZDIR=25, DEĞİŞKEN=26, VE=27, VEYA=28, DEĞİL=29, ICINDEKI=30, 
		HER=31, ICIN=32, SONRA=33, SAYI=34, YAZI=35, IDENTIFIER=36, WS=37, COMMENT=38, 
		MULTILINE_COMMENT=39;
	public static final int
		RULE_pluginDef = 0, RULE_hataExpr = 1, RULE_statement = 2, RULE_exprStatement = 3, 
		RULE_foreachStatement = 4, RULE_loopStatement = 5, RULE_ifStatement = 6, 
		RULE_block = 7, RULE_varDeclaration = 8, RULE_assignment = 9, RULE_yazdir = 10, 
		RULE_returnStatement = 11, RULE_functionCall = 12, RULE_condition = 13, 
		RULE_expr = 14, RULE_logicalOrExpr = 15, RULE_logicalAndExpr = 16, RULE_equalityExpr = 17, 
		RULE_comparisonExpr = 18, RULE_additiveExpr = 19, RULE_multiplicativeExpr = 20, 
		RULE_unaryExpr = 21, RULE_postfixExpr = 22, RULE_primary = 23;
	private static String[] makeRuleNames() {
		return new String[] {
			"pluginDef", "hataExpr", "statement", "exprStatement", "foreachStatement", 
			"loopStatement", "ifStatement", "block", "varDeclaration", "assignment", 
			"yazdir", "returnStatement", "functionCall", "condition", "expr", "logicalOrExpr", 
			"logicalAndExpr", "equalityExpr", "comparisonExpr", "additiveExpr", "multiplicativeExpr", 
			"unaryExpr", "postfixExpr", "primary"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "';'", "'{'", "'}'", "'='", "','", "'=='", "'!='", "'>'", "'<'", 
			"'>='", "'<='", "'+'", "'-'", "'*'", "'/'", "'('", "')'", "'hatas\u0131nda'", 
			"'e\u011Fer'", "'ise'", "'de\u011Filse'", "'oldu\u011Fu s\u00FCrece'", 
			"'fonksiyon'", "'d\u00F6nd\u00FCr'", "'yazd\u0131r'", "'de\u011Fi\u015Fken'", 
			"'ve'", "'veya'", "'de\u011Fil'", "'i\u00E7indeki'", "'her'", "'i\u00E7in'", 
			"'sonras\u0131nda'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, "HATA", "E\u0001ER", "\u0000SE", 
			"DE\u0002\u0003LSE", "OLDU\u0004U_S\u0008RECE", "FONKS\u0005YON", "D\u0001ND\u0004R", 
			"YAZDIR", "DE\u0002\u0003\u0004KEN", "VE", "VEYA", "DE\u0002\u0003L", 
			"ICINDEKI", "HER", "ICIN", "SONRA", "SAYI", "YAZI", "IDENTIFIER", "WS", 
			"COMMENT", "MULTILINE_COMMENT"
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
	public String getGrammarFileName() { return "TurkishPseudoCode.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public TurkishPseudoCodeParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class PluginDefContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(TurkishPseudoCodeParser.IDENTIFIER, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public HataExprContext hataExpr() {
			return getRuleContext(HataExprContext.class,0);
		}
		public TerminalNode SONRA() { return getToken(TurkishPseudoCodeParser.SONRA, 0); }
		public PluginDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pluginDef; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitPluginDef(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PluginDefContext pluginDef() throws RecognitionException {
		PluginDefContext _localctx = new PluginDefContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_pluginDef);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(48);
			match(IDENTIFIER);
			setState(51);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				{
				setState(49);
				hataExpr();
				}
				break;
			case SONRA:
				{
				setState(50);
				match(SONRA);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(53);
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

	public static class HataExprContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(TurkishPseudoCodeParser.IDENTIFIER, 0); }
		public TerminalNode HATA() { return getToken(TurkishPseudoCodeParser.HATA, 0); }
		public HataExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hataExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitHataExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HataExprContext hataExpr() throws RecognitionException {
		HataExprContext _localctx = new HataExprContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_hataExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(IDENTIFIER);
			setState(56);
			match(HATA);
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
		public TerminalNode SONRA() { return getToken(TurkishPseudoCodeParser.SONRA, 0); }
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_statement);
		try {
			setState(71);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(58);
				loopStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(59);
				ifStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(60);
				foreachStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(61);
				varDeclaration();
				setState(62);
				match(T__0);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(64);
				exprStatement();
				{
				setState(68);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__0:
					{
					setState(65);
					match(T__0);
					}
					break;
				case SONRA:
					{
					setState(66);
					match(SONRA);
					setState(67);
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
				setState(70);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitExprStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprStatementContext exprStatement() throws RecognitionException {
		ExprStatementContext _localctx = new ExprStatementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_exprStatement);
		try {
			setState(77);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(73);
				functionCall();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(74);
				assignment();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(75);
				yazdir();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(76);
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
		public List<TerminalNode> IDENTIFIER() { return getTokens(TurkishPseudoCodeParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(TurkishPseudoCodeParser.IDENTIFIER, i);
		}
		public TerminalNode ICINDEKI() { return getToken(TurkishPseudoCodeParser.ICINDEKI, 0); }
		public TerminalNode HER() { return getToken(TurkishPseudoCodeParser.HER, 0); }
		public TerminalNode ICIN() { return getToken(TurkishPseudoCodeParser.ICIN, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ForeachStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_foreachStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitForeachStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForeachStatementContext foreachStatement() throws RecognitionException {
		ForeachStatementContext _localctx = new ForeachStatementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_foreachStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			match(IDENTIFIER);
			setState(80);
			match(ICINDEKI);
			setState(81);
			match(HER);
			setState(82);
			match(IDENTIFIER);
			setState(83);
			match(ICIN);
			setState(84);
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
		public TerminalNode OLDUĞU_SÜRECE() { return getToken(TurkishPseudoCodeParser.OLDUĞU_SÜRECE, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public LoopStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_loopStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitLoopStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LoopStatementContext loopStatement() throws RecognitionException {
		LoopStatementContext _localctx = new LoopStatementContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_loopStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			condition();
			setState(87);
			match(OLDUĞU_SÜRECE);
			setState(88);
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
		public TerminalNode EĞER() { return getToken(TurkishPseudoCodeParser.EĞER, 0); }
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public TerminalNode İSE() { return getToken(TurkishPseudoCodeParser.İSE, 0); }
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode DEĞİLSE() { return getToken(TurkishPseudoCodeParser.DEĞİLSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			match(EĞER);
			setState(91);
			condition();
			setState(92);
			match(İSE);
			setState(93);
			block();
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEĞİLSE) {
				{
				setState(94);
				match(DEĞİLSE);
				setState(95);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(T__1);
			setState(102);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__12) | (1L << T__15) | (1L << EĞER) | (1L << DÖNDÜR) | (1L << YAZDIR) | (1L << DEĞİŞKEN) | (1L << DEĞİL) | (1L << SAYI) | (1L << YAZI) | (1L << IDENTIFIER))) != 0)) {
				{
				{
				setState(99);
				statement();
				}
				}
				setState(104);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(105);
			match(T__2);
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
		public TerminalNode DEĞİŞKEN() { return getToken(TurkishPseudoCodeParser.DEĞİŞKEN, 0); }
		public TerminalNode IDENTIFIER() { return getToken(TurkishPseudoCodeParser.IDENTIFIER, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public VarDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitVarDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarDeclarationContext varDeclaration() throws RecognitionException {
		VarDeclarationContext _localctx = new VarDeclarationContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_varDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(DEĞİŞKEN);
			setState(108);
			match(IDENTIFIER);
			setState(109);
			match(T__3);
			setState(110);
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
		public TerminalNode IDENTIFIER() { return getToken(TurkishPseudoCodeParser.IDENTIFIER, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public AssignmentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_assignment; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitAssignment(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignmentContext assignment() throws RecognitionException {
		AssignmentContext _localctx = new AssignmentContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(IDENTIFIER);
			setState(113);
			match(T__3);
			setState(114);
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
		public TerminalNode YAZDIR() { return getToken(TurkishPseudoCodeParser.YAZDIR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public YazdirContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yazdir; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitYazdir(this);
			else return visitor.visitChildren(this);
		}
	}

	public final YazdirContext yazdir() throws RecognitionException {
		YazdirContext _localctx = new YazdirContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_yazdir);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			match(YAZDIR);
			setState(117);
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
		public TerminalNode DÖNDÜR() { return getToken(TurkishPseudoCodeParser.DÖNDÜR, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_returnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			match(DÖNDÜR);
			setState(120);
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
		public TerminalNode IDENTIFIER() { return getToken(TurkishPseudoCodeParser.IDENTIFIER, 0); }
		public FunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCall; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionCallContext functionCall() throws RecognitionException {
		FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_functionCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			expr();
			setState(127);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__4) {
				{
				{
				setState(123);
				match(T__4);
				setState(124);
				expr();
				}
				}
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(130);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(134);
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
		public List<TerminalNode> VEYA() { return getTokens(TurkishPseudoCodeParser.VEYA); }
		public TerminalNode VEYA(int i) {
			return getToken(TurkishPseudoCodeParser.VEYA, i);
		}
		public LogicalOrExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalOrExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitLogicalOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalOrExprContext logicalOrExpr() throws RecognitionException {
		LogicalOrExprContext _localctx = new LogicalOrExprContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_logicalOrExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(136);
			logicalAndExpr();
			setState(141);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VEYA) {
				{
				{
				setState(137);
				match(VEYA);
				setState(138);
				logicalAndExpr();
				}
				}
				setState(143);
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
		public List<TerminalNode> VE() { return getTokens(TurkishPseudoCodeParser.VE); }
		public TerminalNode VE(int i) {
			return getToken(TurkishPseudoCodeParser.VE, i);
		}
		public LogicalAndExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalAndExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitLogicalAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalAndExprContext logicalAndExpr() throws RecognitionException {
		LogicalAndExprContext _localctx = new LogicalAndExprContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_logicalAndExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(144);
			equalityExpr();
			setState(149);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VE) {
				{
				{
				setState(145);
				match(VE);
				setState(146);
				equalityExpr();
				}
				}
				setState(151);
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitEqualityExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityExprContext equalityExpr() throws RecognitionException {
		EqualityExprContext _localctx = new EqualityExprContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_equalityExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(152);
			comparisonExpr();
			setState(157);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__5 || _la==T__6) {
				{
				{
				setState(153);
				_la = _input.LA(1);
				if ( !(_la==T__5 || _la==T__6) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(154);
				comparisonExpr();
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitComparisonExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonExprContext comparisonExpr() throws RecognitionException {
		ComparisonExprContext _localctx = new ComparisonExprContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_comparisonExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			additiveExpr();
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10))) != 0)) {
				{
				{
				setState(161);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__7) | (1L << T__8) | (1L << T__9) | (1L << T__10))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(162);
				additiveExpr();
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitAdditiveExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveExprContext additiveExpr() throws RecognitionException {
		AdditiveExprContext _localctx = new AdditiveExprContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_additiveExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			multiplicativeExpr();
			setState(173);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__11 || _la==T__12) {
				{
				{
				setState(169);
				_la = _input.LA(1);
				if ( !(_la==T__11 || _la==T__12) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(170);
				multiplicativeExpr();
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
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitMultiplicativeExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeExprContext multiplicativeExpr() throws RecognitionException {
		MultiplicativeExprContext _localctx = new MultiplicativeExprContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_multiplicativeExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			unaryExpr();
			setState(181);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__13 || _la==T__14) {
				{
				{
				setState(177);
				_la = _input.LA(1);
				if ( !(_la==T__13 || _la==T__14) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(178);
				unaryExpr();
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

	public static class UnaryExprContext extends ParserRuleContext {
		public UnaryExprContext unaryExpr() {
			return getRuleContext(UnaryExprContext.class,0);
		}
		public TerminalNode DEĞİL() { return getToken(TurkishPseudoCodeParser.DEĞİL, 0); }
		public PostfixExprContext postfixExpr() {
			return getRuleContext(PostfixExprContext.class,0);
		}
		public UnaryExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitUnaryExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryExprContext unaryExpr() throws RecognitionException {
		UnaryExprContext _localctx = new UnaryExprContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_unaryExpr);
		int _la;
		try {
			setState(187);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__12:
			case DEĞİL:
				enterOuterAlt(_localctx, 1);
				{
				setState(184);
				_la = _input.LA(1);
				if ( !(_la==T__12 || _la==DEĞİL) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(185);
				unaryExpr();
				}
				break;
			case T__15:
			case SAYI:
			case YAZI:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(186);
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
		public TerminalNode DEĞİL() { return getToken(TurkishPseudoCodeParser.DEĞİL, 0); }
		public PostfixExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_postfixExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitPostfixExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PostfixExprContext postfixExpr() throws RecognitionException {
		PostfixExprContext _localctx = new PostfixExprContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_postfixExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			primary();
			setState(191);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEĞİL) {
				{
				setState(190);
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
		public TerminalNode SAYI() { return getToken(TurkishPseudoCodeParser.SAYI, 0); }
		public TerminalNode YAZI() { return getToken(TurkishPseudoCodeParser.YAZI, 0); }
		public TerminalNode IDENTIFIER() { return getToken(TurkishPseudoCodeParser.IDENTIFIER, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		PrimaryContext _localctx = new PrimaryContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_primary);
		try {
			setState(200);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SAYI:
				enterOuterAlt(_localctx, 1);
				{
				setState(193);
				match(SAYI);
				}
				break;
			case YAZI:
				enterOuterAlt(_localctx, 2);
				{
				setState(194);
				match(YAZI);
				}
				break;
			case IDENTIFIER:
				enterOuterAlt(_localctx, 3);
				{
				setState(195);
				match(IDENTIFIER);
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 4);
				{
				setState(196);
				match(T__15);
				setState(197);
				expr();
				setState(198);
				match(T__16);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3)\u00cd\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\3\2\3\2\3\2\5\2\66\n\2\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3"+
		"\4\3\4\3\4\3\4\5\4G\n\4\3\4\5\4J\n\4\3\5\3\5\3\5\3\5\5\5P\n\5\3\6\3\6"+
		"\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\5\bc\n\b"+
		"\3\t\3\t\7\tg\n\t\f\t\16\tj\13\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\13\3\13"+
		"\3\13\3\13\3\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\7\16\u0080\n\16\f\16"+
		"\16\16\u0083\13\16\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\21\7\21\u008e"+
		"\n\21\f\21\16\21\u0091\13\21\3\22\3\22\3\22\7\22\u0096\n\22\f\22\16\22"+
		"\u0099\13\22\3\23\3\23\3\23\7\23\u009e\n\23\f\23\16\23\u00a1\13\23\3\24"+
		"\3\24\3\24\7\24\u00a6\n\24\f\24\16\24\u00a9\13\24\3\25\3\25\3\25\7\25"+
		"\u00ae\n\25\f\25\16\25\u00b1\13\25\3\26\3\26\3\26\7\26\u00b6\n\26\f\26"+
		"\16\26\u00b9\13\26\3\27\3\27\3\27\5\27\u00be\n\27\3\30\3\30\5\30\u00c2"+
		"\n\30\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u00cb\n\31\3\31\2\2\32\2"+
		"\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\2\7\3\2\b\t\3\2\n\r"+
		"\3\2\16\17\3\2\20\21\4\2\17\17\37\37\2\u00cc\2\62\3\2\2\2\49\3\2\2\2\6"+
		"I\3\2\2\2\bO\3\2\2\2\nQ\3\2\2\2\fX\3\2\2\2\16\\\3\2\2\2\20d\3\2\2\2\22"+
		"m\3\2\2\2\24r\3\2\2\2\26v\3\2\2\2\30y\3\2\2\2\32|\3\2\2\2\34\u0086\3\2"+
		"\2\2\36\u0088\3\2\2\2 \u008a\3\2\2\2\"\u0092\3\2\2\2$\u009a\3\2\2\2&\u00a2"+
		"\3\2\2\2(\u00aa\3\2\2\2*\u00b2\3\2\2\2,\u00bd\3\2\2\2.\u00bf\3\2\2\2\60"+
		"\u00ca\3\2\2\2\62\65\7&\2\2\63\66\5\4\3\2\64\66\7#\2\2\65\63\3\2\2\2\65"+
		"\64\3\2\2\2\66\67\3\2\2\2\678\5\20\t\28\3\3\2\2\29:\7&\2\2:;\7\24\2\2"+
		";\5\3\2\2\2<J\5\f\7\2=J\5\16\b\2>J\5\n\6\2?@\5\22\n\2@A\7\3\2\2AJ\3\2"+
		"\2\2BF\5\b\5\2CG\7\3\2\2DE\7#\2\2EG\5\6\4\2FC\3\2\2\2FD\3\2\2\2GJ\3\2"+
		"\2\2HJ\5\20\t\2I<\3\2\2\2I=\3\2\2\2I>\3\2\2\2I?\3\2\2\2IB\3\2\2\2IH\3"+
		"\2\2\2J\7\3\2\2\2KP\5\32\16\2LP\5\24\13\2MP\5\26\f\2NP\5\30\r\2OK\3\2"+
		"\2\2OL\3\2\2\2OM\3\2\2\2ON\3\2\2\2P\t\3\2\2\2QR\7&\2\2RS\7 \2\2ST\7!\2"+
		"\2TU\7&\2\2UV\7\"\2\2VW\5\20\t\2W\13\3\2\2\2XY\5\34\17\2YZ\7\30\2\2Z["+
		"\5\20\t\2[\r\3\2\2\2\\]\7\25\2\2]^\5\34\17\2^_\7\26\2\2_b\5\20\t\2`a\7"+
		"\27\2\2ac\5\20\t\2b`\3\2\2\2bc\3\2\2\2c\17\3\2\2\2dh\7\4\2\2eg\5\6\4\2"+
		"fe\3\2\2\2gj\3\2\2\2hf\3\2\2\2hi\3\2\2\2ik\3\2\2\2jh\3\2\2\2kl\7\5\2\2"+
		"l\21\3\2\2\2mn\7\34\2\2no\7&\2\2op\7\6\2\2pq\5\36\20\2q\23\3\2\2\2rs\7"+
		"&\2\2st\7\6\2\2tu\5\36\20\2u\25\3\2\2\2vw\7\33\2\2wx\5\36\20\2x\27\3\2"+
		"\2\2yz\7\32\2\2z{\5\36\20\2{\31\3\2\2\2|\u0081\5\36\20\2}~\7\7\2\2~\u0080"+
		"\5\36\20\2\177}\3\2\2\2\u0080\u0083\3\2\2\2\u0081\177\3\2\2\2\u0081\u0082"+
		"\3\2\2\2\u0082\u0084\3\2\2\2\u0083\u0081\3\2\2\2\u0084\u0085\7&\2\2\u0085"+
		"\33\3\2\2\2\u0086\u0087\5\36\20\2\u0087\35\3\2\2\2\u0088\u0089\5 \21\2"+
		"\u0089\37\3\2\2\2\u008a\u008f\5\"\22\2\u008b\u008c\7\36\2\2\u008c\u008e"+
		"\5\"\22\2\u008d\u008b\3\2\2\2\u008e\u0091\3\2\2\2\u008f\u008d\3\2\2\2"+
		"\u008f\u0090\3\2\2\2\u0090!\3\2\2\2\u0091\u008f\3\2\2\2\u0092\u0097\5"+
		"$\23\2\u0093\u0094\7\35\2\2\u0094\u0096\5$\23\2\u0095\u0093\3\2\2\2\u0096"+
		"\u0099\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098#\3\2\2\2"+
		"\u0099\u0097\3\2\2\2\u009a\u009f\5&\24\2\u009b\u009c\t\2\2\2\u009c\u009e"+
		"\5&\24\2\u009d\u009b\3\2\2\2\u009e\u00a1\3\2\2\2\u009f\u009d\3\2\2\2\u009f"+
		"\u00a0\3\2\2\2\u00a0%\3\2\2\2\u00a1\u009f\3\2\2\2\u00a2\u00a7\5(\25\2"+
		"\u00a3\u00a4\t\3\2\2\u00a4\u00a6\5(\25\2\u00a5\u00a3\3\2\2\2\u00a6\u00a9"+
		"\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2\2\2\u00a8\'\3\2\2\2\u00a9"+
		"\u00a7\3\2\2\2\u00aa\u00af\5*\26\2\u00ab\u00ac\t\4\2\2\u00ac\u00ae\5*"+
		"\26\2\u00ad\u00ab\3\2\2\2\u00ae\u00b1\3\2\2\2\u00af\u00ad\3\2\2\2\u00af"+
		"\u00b0\3\2\2\2\u00b0)\3\2\2\2\u00b1\u00af\3\2\2\2\u00b2\u00b7\5,\27\2"+
		"\u00b3\u00b4\t\5\2\2\u00b4\u00b6\5,\27\2\u00b5\u00b3\3\2\2\2\u00b6\u00b9"+
		"\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8+\3\2\2\2\u00b9"+
		"\u00b7\3\2\2\2\u00ba\u00bb\t\6\2\2\u00bb\u00be\5,\27\2\u00bc\u00be\5."+
		"\30\2\u00bd\u00ba\3\2\2\2\u00bd\u00bc\3\2\2\2\u00be-\3\2\2\2\u00bf\u00c1"+
		"\5\60\31\2\u00c0\u00c2\7\37\2\2\u00c1\u00c0\3\2\2\2\u00c1\u00c2\3\2\2"+
		"\2\u00c2/\3\2\2\2\u00c3\u00cb\7$\2\2\u00c4\u00cb\7%\2\2\u00c5\u00cb\7"+
		"&\2\2\u00c6\u00c7\7\22\2\2\u00c7\u00c8\5\36\20\2\u00c8\u00c9\7\23\2\2"+
		"\u00c9\u00cb\3\2\2\2\u00ca\u00c3\3\2\2\2\u00ca\u00c4\3\2\2\2\u00ca\u00c5"+
		"\3\2\2\2\u00ca\u00c6\3\2\2\2\u00cb\61\3\2\2\2\22\65FIObh\u0081\u008f\u0097"+
		"\u009f\u00a7\u00af\u00b7\u00bd\u00c1\u00ca";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}