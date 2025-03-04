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
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, VE=9, 
		VEYA=10, DEĞİL=11, EŞİTTİR=12, EŞİT_DEĞİLDİR=13, BÜYÜKTÜR=14, KÜÇÜKTÜR=15, 
		BÜYÜK_EŞİTTİR=16, KÜÇÜK_EŞİTTİR=17, ARTI=18, EKSİ=19, ÇARPIM=20, BÖLÜ=21, 
		HATA=22, EĞER=23, İSE=24, DEĞİLSE=25, OLDUĞU_SÜRECE=26, FONKSİYON=27, 
		DÖNDÜR=28, YAZDIR=29, DEĞİŞKEN=30, ICINDEKI=31, HER=32, ICIN=33, SONRA=34, 
		SAYI=35, YAZI=36, IDENTIFIER=37, WS=38, COMMENT=39, MULTILINE_COMMENT=40;
	public static final int
		RULE_pluginDef = 0, RULE_hataExpr = 1, RULE_statement = 2, RULE_functionCall = 3, 
		RULE_exprStatement = 4, RULE_foreachStatement = 5, RULE_loopStatement = 6, 
		RULE_ifStatement = 7, RULE_block = 8, RULE_varDeclaration = 9, RULE_assignment = 10, 
		RULE_yazdir = 11, RULE_returnStatement = 12, RULE_expr = 13, RULE_logicalAndExpr = 14, 
		RULE_equalityExpr = 15, RULE_equalityOp = 16, RULE_comparisonExpr = 17, 
		RULE_comparisonOp = 18, RULE_additiveExpr = 19, RULE_additiveOp = 20, 
		RULE_multiplicativeExpr = 21, RULE_multiplicativeOp = 22, RULE_unaryExpr = 23, 
		RULE_unaryOp = 24, RULE_postfixExpr = 25, RULE_primary = 26;
	private static String[] makeRuleNames() {
		return new String[] {
			"pluginDef", "hataExpr", "statement", "functionCall", "exprStatement", 
			"foreachStatement", "loopStatement", "ifStatement", "block", "varDeclaration", 
			"assignment", "yazdir", "returnStatement", "expr", "logicalAndExpr", 
			"equalityExpr", "equalityOp", "comparisonExpr", "comparisonOp", "additiveExpr", 
			"additiveOp", "multiplicativeExpr", "multiplicativeOp", "unaryExpr", 
			"unaryOp", "postfixExpr", "primary"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'yap'", "';'", "'('", "','", "')'", "'{'", "'}'", "'='", "'ve'", 
			"'veya'", "'de\u011Fil'", "'=='", "'!='", "'>'", "'<'", "'>='", "'<='", 
			"'+'", "'-'", "'*'", "'/'", "'hatas\u0131nda'", "'e\u011Fer'", "'ise'", 
			"'de\u011Filse'", "'oldu\u011Fu s\u00FCrece'", "'fonksiyon'", "'d\u00F6nd\u00FCr'", 
			"'yazd\u0131r'", "'de\u011Fi\u015Fken'", "'i\u00E7indeki'", "'her'", 
			"'i\u00E7in'", "'sonras\u0131nda'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, "VE", "VEYA", "DE\u0002\u0003L", 
			"E\u0001\u0002TT\u0005R", "E\u0001\u0002T_DE\u0007\u0008LD\u000BR", "B\u0001Y\u0003KT\u0006R", 
			"K\u0001\u0002\u0003KT\u0006R", "B\u0001Y\u0003K_E\u0007\u0008TT\u000BR", 
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
		public List<TerminalNode> IDENTIFIER() { return getTokens(TurkishPseudoCodeParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(TurkishPseudoCodeParser.IDENTIFIER, i);
		}
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
			setState(54);
			match(IDENTIFIER);
			setState(57);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				{
				setState(55);
				hataExpr();
				}
				break;
			case SONRA:
				{
				setState(56);
				match(SONRA);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(59);
			match(T__0);
			setState(60);
			match(IDENTIFIER);
			setState(61);
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
			setState(63);
			match(IDENTIFIER);
			setState(64);
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
			setState(79);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(66);
				loopStatement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(67);
				ifStatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(68);
				foreachStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(69);
				varDeclaration();
				setState(70);
				match(T__1);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(72);
				exprStatement();
				{
				setState(76);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__1:
					{
					setState(73);
					match(T__1);
					}
					break;
				case SONRA:
					{
					setState(74);
					match(SONRA);
					setState(75);
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
				setState(78);
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

	public static class FunctionCallContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(TurkishPseudoCodeParser.IDENTIFIER, 0); }
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
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
		enterRule(_localctx, 6, RULE_functionCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(81);
			match(IDENTIFIER);
			setState(82);
			match(T__2);
			setState(83);
			expr();
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(84);
				match(T__3);
				setState(85);
				expr();
				}
				}
				setState(90);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(91);
			match(T__4);
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
		enterRule(_localctx, 8, RULE_exprStatement);
		try {
			setState(95);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(93);
				functionCall();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(94);
				assignment();
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
		enterRule(_localctx, 10, RULE_foreachStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(IDENTIFIER);
			setState(98);
			match(ICINDEKI);
			setState(99);
			match(HER);
			setState(100);
			match(IDENTIFIER);
			setState(101);
			match(ICIN);
			setState(102);
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
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
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
		enterRule(_localctx, 12, RULE_loopStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(104);
			expr();
			setState(105);
			match(OLDUĞU_SÜRECE);
			setState(106);
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
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
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
		enterRule(_localctx, 14, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(108);
			match(EĞER);
			setState(109);
			expr();
			setState(110);
			match(İSE);
			setState(111);
			block();
			setState(114);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEĞİLSE) {
				{
				setState(112);
				match(DEĞİLSE);
				setState(113);
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
		enterRule(_localctx, 16, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(116);
			match(T__5);
			setState(120);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << T__5) | (1L << DEĞİL) | (1L << EKSİ) | (1L << EĞER) | (1L << DEĞİŞKEN) | (1L << SAYI) | (1L << YAZI) | (1L << IDENTIFIER))) != 0)) {
				{
				{
				setState(117);
				statement();
				}
				}
				setState(122);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(123);
			match(T__6);
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
		public AssignmentContext assignment() {
			return getRuleContext(AssignmentContext.class,0);
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
		enterRule(_localctx, 18, RULE_varDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125);
			match(DEĞİŞKEN);
			setState(126);
			assignment();
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
		enterRule(_localctx, 20, RULE_assignment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(128);
			match(IDENTIFIER);
			setState(129);
			match(T__7);
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

	public static class ExprContext extends ParserRuleContext {
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
		enterRule(_localctx, 26, RULE_expr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			logicalAndExpr();
			setState(143);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(139);
					match(VEYA);
					setState(140);
					logicalAndExpr();
					}
					} 
				}
				setState(145);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
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
		enterRule(_localctx, 28, RULE_logicalAndExpr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			equalityExpr();
			setState(151);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(147);
					match(VE);
					setState(148);
					equalityExpr();
					}
					} 
				}
				setState(153);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,8,_ctx);
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
		public List<EqualityOpContext> equalityOp() {
			return getRuleContexts(EqualityOpContext.class);
		}
		public EqualityOpContext equalityOp(int i) {
			return getRuleContext(EqualityOpContext.class,i);
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
		enterRule(_localctx, 30, RULE_equalityExpr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(154);
			comparisonExpr();
			setState(160);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(155);
					equalityOp();
					setState(156);
					comparisonExpr();
					}
					} 
				}
				setState(162);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
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

	public static class EqualityOpContext extends ParserRuleContext {
		public TerminalNode EŞİTTİR() { return getToken(TurkishPseudoCodeParser.EŞİTTİR, 0); }
		public TerminalNode EŞİT_DEĞİLDİR() { return getToken(TurkishPseudoCodeParser.EŞİT_DEĞİLDİR, 0); }
		public EqualityOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equalityOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitEqualityOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EqualityOpContext equalityOp() throws RecognitionException {
		EqualityOpContext _localctx = new EqualityOpContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_equalityOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(163);
			_la = _input.LA(1);
			if ( !(_la==EŞİTTİR || _la==EŞİT_DEĞİLDİR) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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
		public List<ComparisonOpContext> comparisonOp() {
			return getRuleContexts(ComparisonOpContext.class);
		}
		public ComparisonOpContext comparisonOp(int i) {
			return getRuleContext(ComparisonOpContext.class,i);
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
		enterRule(_localctx, 34, RULE_comparisonExpr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			additiveExpr();
			setState(171);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(166);
					comparisonOp();
					setState(167);
					additiveExpr();
					}
					} 
				}
				setState(173);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
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

	public static class ComparisonOpContext extends ParserRuleContext {
		public TerminalNode BÜYÜKTÜR() { return getToken(TurkishPseudoCodeParser.BÜYÜKTÜR, 0); }
		public TerminalNode KÜÇÜKTÜR() { return getToken(TurkishPseudoCodeParser.KÜÇÜKTÜR, 0); }
		public TerminalNode BÜYÜK_EŞİTTİR() { return getToken(TurkishPseudoCodeParser.BÜYÜK_EŞİTTİR, 0); }
		public TerminalNode KÜÇÜK_EŞİTTİR() { return getToken(TurkishPseudoCodeParser.KÜÇÜK_EŞİTTİR, 0); }
		public ComparisonOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitComparisonOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonOpContext comparisonOp() throws RecognitionException {
		ComparisonOpContext _localctx = new ComparisonOpContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_comparisonOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BÜYÜKTÜR) | (1L << KÜÇÜKTÜR) | (1L << BÜYÜK_EŞİTTİR) | (1L << KÜÇÜK_EŞİTTİR))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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
		public List<AdditiveOpContext> additiveOp() {
			return getRuleContexts(AdditiveOpContext.class);
		}
		public AdditiveOpContext additiveOp(int i) {
			return getRuleContext(AdditiveOpContext.class,i);
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
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(176);
			multiplicativeExpr();
			setState(182);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(177);
					additiveOp();
					setState(178);
					multiplicativeExpr();
					}
					} 
				}
				setState(184);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
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

	public static class AdditiveOpContext extends ParserRuleContext {
		public TerminalNode ARTI() { return getToken(TurkishPseudoCodeParser.ARTI, 0); }
		public TerminalNode EKSİ() { return getToken(TurkishPseudoCodeParser.EKSİ, 0); }
		public AdditiveOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_additiveOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitAdditiveOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AdditiveOpContext additiveOp() throws RecognitionException {
		AdditiveOpContext _localctx = new AdditiveOpContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_additiveOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(185);
			_la = _input.LA(1);
			if ( !(_la==ARTI || _la==EKSİ) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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
		public List<MultiplicativeOpContext> multiplicativeOp() {
			return getRuleContexts(MultiplicativeOpContext.class);
		}
		public MultiplicativeOpContext multiplicativeOp(int i) {
			return getRuleContext(MultiplicativeOpContext.class,i);
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
		enterRule(_localctx, 42, RULE_multiplicativeExpr);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(187);
			unaryExpr();
			setState(193);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(188);
					multiplicativeOp();
					setState(189);
					unaryExpr();
					}
					} 
				}
				setState(195);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
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

	public static class MultiplicativeOpContext extends ParserRuleContext {
		public TerminalNode ÇARPIM() { return getToken(TurkishPseudoCodeParser.ÇARPIM, 0); }
		public TerminalNode BÖLÜ() { return getToken(TurkishPseudoCodeParser.BÖLÜ, 0); }
		public MultiplicativeOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiplicativeOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitMultiplicativeOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiplicativeOpContext multiplicativeOp() throws RecognitionException {
		MultiplicativeOpContext _localctx = new MultiplicativeOpContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_multiplicativeOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			_la = _input.LA(1);
			if ( !(_la==ÇARPIM || _la==BÖLÜ) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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
		public UnaryOpContext unaryOp() {
			return getRuleContext(UnaryOpContext.class,0);
		}
		public UnaryExprContext unaryExpr() {
			return getRuleContext(UnaryExprContext.class,0);
		}
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
		enterRule(_localctx, 46, RULE_unaryExpr);
		try {
			setState(202);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DEĞİL:
			case EKSİ:
				enterOuterAlt(_localctx, 1);
				{
				setState(198);
				unaryOp();
				setState(199);
				unaryExpr();
				}
				break;
			case T__2:
			case SAYI:
			case YAZI:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(201);
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

	public static class UnaryOpContext extends ParserRuleContext {
		public TerminalNode EKSİ() { return getToken(TurkishPseudoCodeParser.EKSİ, 0); }
		public TerminalNode DEĞİL() { return getToken(TurkishPseudoCodeParser.DEĞİL, 0); }
		public UnaryOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unaryOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitUnaryOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryOpContext unaryOp() throws RecognitionException {
		UnaryOpContext _localctx = new UnaryOpContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_unaryOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204);
			_la = _input.LA(1);
			if ( !(_la==DEĞİL || _la==EKSİ) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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
		enterRule(_localctx, 50, RULE_postfixExpr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			primary();
			setState(208);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(207);
				match(DEĞİL);
				}
				break;
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
		public ExprStatementContext exprStatement() {
			return getRuleContext(ExprStatementContext.class,0);
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
		enterRule(_localctx, 52, RULE_primary);
		try {
			setState(218);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(210);
				match(SAYI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(211);
				match(YAZI);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(212);
				match(IDENTIFIER);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(213);
				match(T__2);
				setState(214);
				expr();
				setState(215);
				match(T__4);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(217);
				exprStatement();
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3*\u00df\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\2\5\2<\n\2\3\2\3\2\3\2\3\2\3"+
		"\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4O\n\4\3\4\5\4R\n"+
		"\4\3\5\3\5\3\5\3\5\3\5\7\5Y\n\5\f\5\16\5\\\13\5\3\5\3\5\3\6\3\6\5\6b\n"+
		"\6\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t"+
		"\5\tu\n\t\3\n\3\n\7\ny\n\n\f\n\16\n|\13\n\3\n\3\n\3\13\3\13\3\13\3\f\3"+
		"\f\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\7\17\u0090\n\17\f"+
		"\17\16\17\u0093\13\17\3\20\3\20\3\20\7\20\u0098\n\20\f\20\16\20\u009b"+
		"\13\20\3\21\3\21\3\21\3\21\7\21\u00a1\n\21\f\21\16\21\u00a4\13\21\3\22"+
		"\3\22\3\23\3\23\3\23\3\23\7\23\u00ac\n\23\f\23\16\23\u00af\13\23\3\24"+
		"\3\24\3\25\3\25\3\25\3\25\7\25\u00b7\n\25\f\25\16\25\u00ba\13\25\3\26"+
		"\3\26\3\27\3\27\3\27\3\27\7\27\u00c2\n\27\f\27\16\27\u00c5\13\27\3\30"+
		"\3\30\3\31\3\31\3\31\3\31\5\31\u00cd\n\31\3\32\3\32\3\33\3\33\5\33\u00d3"+
		"\n\33\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\5\34\u00dd\n\34\3\34\2\2"+
		"\35\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\66\2\7\3"+
		"\2\16\17\3\2\20\23\3\2\24\25\3\2\26\27\4\2\r\r\25\25\2\u00da\28\3\2\2"+
		"\2\4A\3\2\2\2\6Q\3\2\2\2\bS\3\2\2\2\na\3\2\2\2\fc\3\2\2\2\16j\3\2\2\2"+
		"\20n\3\2\2\2\22v\3\2\2\2\24\177\3\2\2\2\26\u0082\3\2\2\2\30\u0086\3\2"+
		"\2\2\32\u0089\3\2\2\2\34\u008c\3\2\2\2\36\u0094\3\2\2\2 \u009c\3\2\2\2"+
		"\"\u00a5\3\2\2\2$\u00a7\3\2\2\2&\u00b0\3\2\2\2(\u00b2\3\2\2\2*\u00bb\3"+
		"\2\2\2,\u00bd\3\2\2\2.\u00c6\3\2\2\2\60\u00cc\3\2\2\2\62\u00ce\3\2\2\2"+
		"\64\u00d0\3\2\2\2\66\u00dc\3\2\2\28;\7\'\2\29<\5\4\3\2:<\7$\2\2;9\3\2"+
		"\2\2;:\3\2\2\2<=\3\2\2\2=>\7\3\2\2>?\7\'\2\2?@\5\22\n\2@\3\3\2\2\2AB\7"+
		"\'\2\2BC\7\30\2\2C\5\3\2\2\2DR\5\16\b\2ER\5\20\t\2FR\5\f\7\2GH\5\24\13"+
		"\2HI\7\4\2\2IR\3\2\2\2JN\5\n\6\2KO\7\4\2\2LM\7$\2\2MO\5\6\4\2NK\3\2\2"+
		"\2NL\3\2\2\2OR\3\2\2\2PR\5\22\n\2QD\3\2\2\2QE\3\2\2\2QF\3\2\2\2QG\3\2"+
		"\2\2QJ\3\2\2\2QP\3\2\2\2R\7\3\2\2\2ST\7\'\2\2TU\7\5\2\2UZ\5\34\17\2VW"+
		"\7\6\2\2WY\5\34\17\2XV\3\2\2\2Y\\\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2[]\3\2\2"+
		"\2\\Z\3\2\2\2]^\7\7\2\2^\t\3\2\2\2_b\5\b\5\2`b\5\26\f\2a_\3\2\2\2a`\3"+
		"\2\2\2b\13\3\2\2\2cd\7\'\2\2de\7!\2\2ef\7\"\2\2fg\7\'\2\2gh\7#\2\2hi\5"+
		"\22\n\2i\r\3\2\2\2jk\5\34\17\2kl\7\34\2\2lm\5\22\n\2m\17\3\2\2\2no\7\31"+
		"\2\2op\5\34\17\2pq\7\32\2\2qt\5\22\n\2rs\7\33\2\2su\5\22\n\2tr\3\2\2\2"+
		"tu\3\2\2\2u\21\3\2\2\2vz\7\b\2\2wy\5\6\4\2xw\3\2\2\2y|\3\2\2\2zx\3\2\2"+
		"\2z{\3\2\2\2{}\3\2\2\2|z\3\2\2\2}~\7\t\2\2~\23\3\2\2\2\177\u0080\7 \2"+
		"\2\u0080\u0081\5\26\f\2\u0081\25\3\2\2\2\u0082\u0083\7\'\2\2\u0083\u0084"+
		"\7\n\2\2\u0084\u0085\5\34\17\2\u0085\27\3\2\2\2\u0086\u0087\7\37\2\2\u0087"+
		"\u0088\5\34\17\2\u0088\31\3\2\2\2\u0089\u008a\7\36\2\2\u008a\u008b\5\34"+
		"\17\2\u008b\33\3\2\2\2\u008c\u0091\5\36\20\2\u008d\u008e\7\f\2\2\u008e"+
		"\u0090\5\36\20\2\u008f\u008d\3\2\2\2\u0090\u0093\3\2\2\2\u0091\u008f\3"+
		"\2\2\2\u0091\u0092\3\2\2\2\u0092\35\3\2\2\2\u0093\u0091\3\2\2\2\u0094"+
		"\u0099\5 \21\2\u0095\u0096\7\13\2\2\u0096\u0098\5 \21\2\u0097\u0095\3"+
		"\2\2\2\u0098\u009b\3\2\2\2\u0099\u0097\3\2\2\2\u0099\u009a\3\2\2\2\u009a"+
		"\37\3\2\2\2\u009b\u0099\3\2\2\2\u009c\u00a2\5$\23\2\u009d\u009e\5\"\22"+
		"\2\u009e\u009f\5$\23\2\u009f\u00a1\3\2\2\2\u00a0\u009d\3\2\2\2\u00a1\u00a4"+
		"\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3!\3\2\2\2\u00a4"+
		"\u00a2\3\2\2\2\u00a5\u00a6\t\2\2\2\u00a6#\3\2\2\2\u00a7\u00ad\5(\25\2"+
		"\u00a8\u00a9\5&\24\2\u00a9\u00aa\5(\25\2\u00aa\u00ac\3\2\2\2\u00ab\u00a8"+
		"\3\2\2\2\u00ac\u00af\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ad\u00ae\3\2\2\2\u00ae"+
		"%\3\2\2\2\u00af\u00ad\3\2\2\2\u00b0\u00b1\t\3\2\2\u00b1\'\3\2\2\2\u00b2"+
		"\u00b8\5,\27\2\u00b3\u00b4\5*\26\2\u00b4\u00b5\5,\27\2\u00b5\u00b7\3\2"+
		"\2\2\u00b6\u00b3\3\2\2\2\u00b7\u00ba\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8"+
		"\u00b9\3\2\2\2\u00b9)\3\2\2\2\u00ba\u00b8\3\2\2\2\u00bb\u00bc\t\4\2\2"+
		"\u00bc+\3\2\2\2\u00bd\u00c3\5\60\31\2\u00be\u00bf\5.\30\2\u00bf\u00c0"+
		"\5\60\31\2\u00c0\u00c2\3\2\2\2\u00c1\u00be\3\2\2\2\u00c2\u00c5\3\2\2\2"+
		"\u00c3\u00c1\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c4-\3\2\2\2\u00c5\u00c3\3"+
		"\2\2\2\u00c6\u00c7\t\5\2\2\u00c7/\3\2\2\2\u00c8\u00c9\5\62\32\2\u00c9"+
		"\u00ca\5\60\31\2\u00ca\u00cd\3\2\2\2\u00cb\u00cd\5\64\33\2\u00cc\u00c8"+
		"\3\2\2\2\u00cc\u00cb\3\2\2\2\u00cd\61\3\2\2\2\u00ce\u00cf\t\6\2\2\u00cf"+
		"\63\3\2\2\2\u00d0\u00d2\5\66\34\2\u00d1\u00d3\7\r\2\2\u00d2\u00d1\3\2"+
		"\2\2\u00d2\u00d3\3\2\2\2\u00d3\65\3\2\2\2\u00d4\u00dd\7%\2\2\u00d5\u00dd"+
		"\7&\2\2\u00d6\u00dd\7\'\2\2\u00d7\u00d8\7\5\2\2\u00d8\u00d9\5\34\17\2"+
		"\u00d9\u00da\7\7\2\2\u00da\u00dd\3\2\2\2\u00db\u00dd\5\n\6\2\u00dc\u00d4"+
		"\3\2\2\2\u00dc\u00d5\3\2\2\2\u00dc\u00d6\3\2\2\2\u00dc\u00d7\3\2\2\2\u00dc"+
		"\u00db\3\2\2\2\u00dd\67\3\2\2\2\22;NQZatz\u0091\u0099\u00a2\u00ad\u00b8"+
		"\u00c3\u00cc\u00d2\u00dc";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}