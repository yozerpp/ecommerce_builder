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
		VE=10, VEYA=11, DEĞİL=12, EŞİTTİR=13, EŞİT_DEĞİLDİR=14, BÜYÜKTÜR=15, KÜÇÜKTÜR=16, 
		BÜYÜK_EŞİTTİR=17, KÜÇÜK_EŞİTTİR=18, ARTI=19, EKSİ=20, ÇARPIM=21, BÖLÜ=22, 
		HATA=23, EĞER=24, İSE=25, DEĞİLSE=26, IKEN=27, FONKSİYON=28, DÖNDÜR=29, 
		DEĞİŞKEN=30, ICINDEKI=31, HER=32, ICIN=33, SONRA=34, SAYI=35, YAZI=36, 
		IDENTIFIER=37, WS=38, COMMENT=39, MULTILINE_COMMENT=40;
	public static final int
		RULE_pluginDef = 0, RULE_hataExpr = 1, RULE_statement = 2, RULE_functionCall = 3, 
		RULE_exprStatement = 4, RULE_foreachStatement = 5, RULE_loopStatement = 6, 
		RULE_ifStatement = 7, RULE_block = 8, RULE_varDeclaration = 9, RULE_assignment = 10, 
		RULE_returnStatement = 11, RULE_expr = 12, RULE_logicalAndExpr = 13, RULE_equalityExpr = 14, 
		RULE_equalityOp = 15, RULE_comparisonExpr = 16, RULE_comparisonOp = 17, 
		RULE_additiveExpr = 18, RULE_additiveOp = 19, RULE_multiplicativeExpr = 20, 
		RULE_multiplicativeOp = 21, RULE_unaryExpr = 22, RULE_unaryOp = 23, RULE_postfixExpr = 24, 
		RULE_id_with_dots = 25, RULE_primary = 26;
	private static String[] makeRuleNames() {
		return new String[] {
			"pluginDef", "hataExpr", "statement", "functionCall", "exprStatement", 
			"foreachStatement", "loopStatement", "ifStatement", "block", "varDeclaration", 
			"assignment", "returnStatement", "expr", "logicalAndExpr", "equalityExpr", 
			"equalityOp", "comparisonExpr", "comparisonOp", "additiveExpr", "additiveOp", 
			"multiplicativeExpr", "multiplicativeOp", "unaryExpr", "unaryOp", "postfixExpr", 
			"id_with_dots", "primary"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'yap'", "'('", "','", "')'", "';'", "'{'", "'}'", "'='", "'.'", 
			"'ve'", "'veya'", "'de\u011Fil'", "'=='", "'!='", "'>'", "'<'", "'>='", 
			"'<='", "'+'", "'-'", "'*'", "'/'", "'hatas\u0131nda'", "'e\u011Fer'", 
			"'ise'", "'de\u011Filse'", "'iken'", "'fonksiyon'", "'d\u00F6nd\u00FCr'", 
			"'de\u011Fi\u015Fken'", "'i\u00E7indeki'", "'her'", "'i\u00E7in'", "'sonras\u0131nda'"
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
			"IKEN", "FONKS\u0005YON", "D\u0001ND\u0004R", "DE\u0002\u0003\u0004KEN", 
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
		public Id_with_dotsContext id_with_dots() {
			return getRuleContext(Id_with_dotsContext.class,0);
		}
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
			setState(54);
			id_with_dots();
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
		public Id_with_dotsContext id_with_dots() {
			return getRuleContext(Id_with_dotsContext.class,0);
		}
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
			id_with_dots();
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
			setState(72);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
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
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(70);
				exprStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(71);
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
		public Id_with_dotsContext id_with_dots() {
			return getRuleContext(Id_with_dotsContext.class,0);
		}
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
			setState(74);
			id_with_dots();
			setState(75);
			match(T__1);
			setState(84);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << DEĞİL) | (1L << EKSİ) | (1L << SAYI) | (1L << YAZI) | (1L << IDENTIFIER))) != 0)) {
				{
				setState(76);
				expr();
				setState(81);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__2) {
					{
					{
					setState(77);
					match(T__2);
					setState(78);
					expr();
					}
					}
					setState(83);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(86);
			match(T__3);
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
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				{
				setState(88);
				functionCall();
				}
				break;
			case 2:
				{
				setState(89);
				assignment();
				}
				break;
			}
			setState(92);
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
			setState(94);
			match(IDENTIFIER);
			setState(95);
			match(ICINDEKI);
			setState(96);
			match(HER);
			setState(97);
			match(IDENTIFIER);
			setState(98);
			match(ICIN);
			setState(99);
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
		public TerminalNode IKEN() { return getToken(TurkishPseudoCodeParser.IKEN, 0); }
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
			setState(101);
			expr();
			setState(102);
			match(IKEN);
			setState(103);
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
			setState(105);
			match(EĞER);
			setState(106);
			expr();
			setState(107);
			match(İSE);
			setState(108);
			block();
			setState(111);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEĞİLSE) {
				{
				setState(109);
				match(DEĞİLSE);
				setState(110);
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
			setState(113);
			match(T__5);
			setState(117);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__5) | (1L << DEĞİL) | (1L << EKSİ) | (1L << EĞER) | (1L << DEĞİŞKEN) | (1L << SAYI) | (1L << YAZI) | (1L << IDENTIFIER))) != 0)) {
				{
				{
				setState(114);
				statement();
				}
				}
				setState(119);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(120);
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
			setState(122);
			match(DEĞİŞKEN);
			setState(123);
			assignment();
			setState(124);
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
			setState(126);
			match(IDENTIFIER);
			setState(127);
			match(T__7);
			setState(128);
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
			setState(130);
			match(DÖNDÜR);
			setState(131);
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
		enterRule(_localctx, 24, RULE_expr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(133);
			logicalAndExpr();
			setState(138);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VEYA) {
				{
				{
				setState(134);
				match(VEYA);
				setState(135);
				logicalAndExpr();
				}
				}
				setState(140);
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
		enterRule(_localctx, 26, RULE_logicalAndExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(141);
			equalityExpr();
			setState(146);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VE) {
				{
				{
				setState(142);
				match(VE);
				setState(143);
				equalityExpr();
				}
				}
				setState(148);
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
		enterRule(_localctx, 28, RULE_equalityExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			comparisonExpr();
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EŞİTTİR || _la==EŞİT_DEĞİLDİR) {
				{
				{
				setState(150);
				equalityOp();
				setState(151);
				comparisonExpr();
				}
				}
				setState(157);
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
		enterRule(_localctx, 30, RULE_equalityOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
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
		enterRule(_localctx, 32, RULE_comparisonExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			additiveExpr();
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BÜYÜKTÜR) | (1L << KÜÇÜKTÜR) | (1L << BÜYÜK_EŞİTTİR) | (1L << KÜÇÜK_EŞİTTİR))) != 0)) {
				{
				{
				setState(161);
				comparisonOp();
				setState(162);
				additiveExpr();
				}
				}
				setState(168);
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
		enterRule(_localctx, 34, RULE_comparisonOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(169);
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
		enterRule(_localctx, 36, RULE_additiveExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
			multiplicativeExpr();
			setState(177);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ARTI || _la==EKSİ) {
				{
				{
				setState(172);
				additiveOp();
				setState(173);
				multiplicativeExpr();
				}
				}
				setState(179);
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
		enterRule(_localctx, 38, RULE_additiveOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(180);
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
		enterRule(_localctx, 40, RULE_multiplicativeExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			unaryExpr();
			setState(188);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ÇARPIM || _la==BÖLÜ) {
				{
				{
				setState(183);
				multiplicativeOp();
				setState(184);
				unaryExpr();
				}
				}
				setState(190);
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
		enterRule(_localctx, 42, RULE_multiplicativeOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
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
		enterRule(_localctx, 44, RULE_unaryExpr);
		try {
			setState(197);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DEĞİL:
			case EKSİ:
				enterOuterAlt(_localctx, 1);
				{
				setState(193);
				unaryOp();
				setState(194);
				unaryExpr();
				}
				break;
			case T__1:
			case SAYI:
			case YAZI:
			case IDENTIFIER:
				enterOuterAlt(_localctx, 2);
				{
				setState(196);
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
		enterRule(_localctx, 46, RULE_unaryOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
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
		enterRule(_localctx, 48, RULE_postfixExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			primary();
			setState(203);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==DEĞİL) {
				{
				setState(202);
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

	public static class Id_with_dotsContext extends ParserRuleContext {
		public List<TerminalNode> IDENTIFIER() { return getTokens(TurkishPseudoCodeParser.IDENTIFIER); }
		public TerminalNode IDENTIFIER(int i) {
			return getToken(TurkishPseudoCodeParser.IDENTIFIER, i);
		}
		public Id_with_dotsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id_with_dots; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof TurkishPseudoCodeVisitor ) return ((TurkishPseudoCodeVisitor<? extends T>)visitor).visitId_with_dots(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Id_with_dotsContext id_with_dots() throws RecognitionException {
		Id_with_dotsContext _localctx = new Id_with_dotsContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_id_with_dots);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			match(IDENTIFIER);
			setState(210);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8) {
				{
				{
				setState(206);
				match(T__8);
				setState(207);
				match(IDENTIFIER);
				}
				}
				setState(212);
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
			setState(221);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(213);
				match(SAYI);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(214);
				match(YAZI);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(215);
				match(IDENTIFIER);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(216);
				match(T__1);
				setState(217);
				expr();
				setState(218);
				match(T__3);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(220);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3*\u00e2\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\3\2\3\2\3\2\5\2<\n\2\3\2\3\2\3\2\3\2\3"+
		"\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\5\4K\n\4\3\5\3\5\3\5\3\5\3\5\7\5R\n"+
		"\5\f\5\16\5U\13\5\5\5W\n\5\3\5\3\5\3\6\3\6\5\6]\n\6\3\6\3\6\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\5\tr\n\t\3"+
		"\n\3\n\7\nv\n\n\f\n\16\ny\13\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\16\3\16\3\16\7\16\u008b\n\16\f\16\16\16\u008e\13\16"+
		"\3\17\3\17\3\17\7\17\u0093\n\17\f\17\16\17\u0096\13\17\3\20\3\20\3\20"+
		"\3\20\7\20\u009c\n\20\f\20\16\20\u009f\13\20\3\21\3\21\3\22\3\22\3\22"+
		"\3\22\7\22\u00a7\n\22\f\22\16\22\u00aa\13\22\3\23\3\23\3\24\3\24\3\24"+
		"\3\24\7\24\u00b2\n\24\f\24\16\24\u00b5\13\24\3\25\3\25\3\26\3\26\3\26"+
		"\3\26\7\26\u00bd\n\26\f\26\16\26\u00c0\13\26\3\27\3\27\3\30\3\30\3\30"+
		"\3\30\5\30\u00c8\n\30\3\31\3\31\3\32\3\32\5\32\u00ce\n\32\3\33\3\33\3"+
		"\33\7\33\u00d3\n\33\f\33\16\33\u00d6\13\33\3\34\3\34\3\34\3\34\3\34\3"+
		"\34\3\34\3\34\5\34\u00e0\n\34\3\34\2\2\35\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\32\34\36 \"$&(*,.\60\62\64\66\2\7\3\2\17\20\3\2\21\24\3\2\25\26\3\2\27"+
		"\30\4\2\16\16\26\26\2\u00de\28\3\2\2\2\4A\3\2\2\2\6J\3\2\2\2\bL\3\2\2"+
		"\2\n\\\3\2\2\2\f`\3\2\2\2\16g\3\2\2\2\20k\3\2\2\2\22s\3\2\2\2\24|\3\2"+
		"\2\2\26\u0080\3\2\2\2\30\u0084\3\2\2\2\32\u0087\3\2\2\2\34\u008f\3\2\2"+
		"\2\36\u0097\3\2\2\2 \u00a0\3\2\2\2\"\u00a2\3\2\2\2$\u00ab\3\2\2\2&\u00ad"+
		"\3\2\2\2(\u00b6\3\2\2\2*\u00b8\3\2\2\2,\u00c1\3\2\2\2.\u00c7\3\2\2\2\60"+
		"\u00c9\3\2\2\2\62\u00cb\3\2\2\2\64\u00cf\3\2\2\2\66\u00df\3\2\2\28;\5"+
		"\64\33\29<\5\4\3\2:<\7$\2\2;9\3\2\2\2;:\3\2\2\2<=\3\2\2\2=>\7\3\2\2>?"+
		"\7\'\2\2?@\5\22\n\2@\3\3\2\2\2AB\5\64\33\2BC\7\31\2\2C\5\3\2\2\2DK\5\16"+
		"\b\2EK\5\20\t\2FK\5\f\7\2GK\5\24\13\2HK\5\n\6\2IK\5\22\n\2JD\3\2\2\2J"+
		"E\3\2\2\2JF\3\2\2\2JG\3\2\2\2JH\3\2\2\2JI\3\2\2\2K\7\3\2\2\2LM\5\64\33"+
		"\2MV\7\4\2\2NS\5\32\16\2OP\7\5\2\2PR\5\32\16\2QO\3\2\2\2RU\3\2\2\2SQ\3"+
		"\2\2\2ST\3\2\2\2TW\3\2\2\2US\3\2\2\2VN\3\2\2\2VW\3\2\2\2WX\3\2\2\2XY\7"+
		"\6\2\2Y\t\3\2\2\2Z]\5\b\5\2[]\5\26\f\2\\Z\3\2\2\2\\[\3\2\2\2]^\3\2\2\2"+
		"^_\7\7\2\2_\13\3\2\2\2`a\7\'\2\2ab\7!\2\2bc\7\"\2\2cd\7\'\2\2de\7#\2\2"+
		"ef\5\22\n\2f\r\3\2\2\2gh\5\32\16\2hi\7\35\2\2ij\5\22\n\2j\17\3\2\2\2k"+
		"l\7\32\2\2lm\5\32\16\2mn\7\33\2\2nq\5\22\n\2op\7\34\2\2pr\5\22\n\2qo\3"+
		"\2\2\2qr\3\2\2\2r\21\3\2\2\2sw\7\b\2\2tv\5\6\4\2ut\3\2\2\2vy\3\2\2\2w"+
		"u\3\2\2\2wx\3\2\2\2xz\3\2\2\2yw\3\2\2\2z{\7\t\2\2{\23\3\2\2\2|}\7 \2\2"+
		"}~\5\26\f\2~\177\7\7\2\2\177\25\3\2\2\2\u0080\u0081\7\'\2\2\u0081\u0082"+
		"\7\n\2\2\u0082\u0083\5\32\16\2\u0083\27\3\2\2\2\u0084\u0085\7\37\2\2\u0085"+
		"\u0086\5\32\16\2\u0086\31\3\2\2\2\u0087\u008c\5\34\17\2\u0088\u0089\7"+
		"\r\2\2\u0089\u008b\5\34\17\2\u008a\u0088\3\2\2\2\u008b\u008e\3\2\2\2\u008c"+
		"\u008a\3\2\2\2\u008c\u008d\3\2\2\2\u008d\33\3\2\2\2\u008e\u008c\3\2\2"+
		"\2\u008f\u0094\5\36\20\2\u0090\u0091\7\f\2\2\u0091\u0093\5\36\20\2\u0092"+
		"\u0090\3\2\2\2\u0093\u0096\3\2\2\2\u0094\u0092\3\2\2\2\u0094\u0095\3\2"+
		"\2\2\u0095\35\3\2\2\2\u0096\u0094\3\2\2\2\u0097\u009d\5\"\22\2\u0098\u0099"+
		"\5 \21\2\u0099\u009a\5\"\22\2\u009a\u009c\3\2\2\2\u009b\u0098\3\2\2\2"+
		"\u009c\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e\37"+
		"\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u00a1\t\2\2\2\u00a1!\3\2\2\2\u00a2"+
		"\u00a8\5&\24\2\u00a3\u00a4\5$\23\2\u00a4\u00a5\5&\24\2\u00a5\u00a7\3\2"+
		"\2\2\u00a6\u00a3\3\2\2\2\u00a7\u00aa\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a8"+
		"\u00a9\3\2\2\2\u00a9#\3\2\2\2\u00aa\u00a8\3\2\2\2\u00ab\u00ac\t\3\2\2"+
		"\u00ac%\3\2\2\2\u00ad\u00b3\5*\26\2\u00ae\u00af\5(\25\2\u00af\u00b0\5"+
		"*\26\2\u00b0\u00b2\3\2\2\2\u00b1\u00ae\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3"+
		"\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\'\3\2\2\2\u00b5\u00b3\3\2\2\2"+
		"\u00b6\u00b7\t\4\2\2\u00b7)\3\2\2\2\u00b8\u00be\5.\30\2\u00b9\u00ba\5"+
		",\27\2\u00ba\u00bb\5.\30\2\u00bb\u00bd\3\2\2\2\u00bc\u00b9\3\2\2\2\u00bd"+
		"\u00c0\3\2\2\2\u00be\u00bc\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf+\3\2\2\2"+
		"\u00c0\u00be\3\2\2\2\u00c1\u00c2\t\5\2\2\u00c2-\3\2\2\2\u00c3\u00c4\5"+
		"\60\31\2\u00c4\u00c5\5.\30\2\u00c5\u00c8\3\2\2\2\u00c6\u00c8\5\62\32\2"+
		"\u00c7\u00c3\3\2\2\2\u00c7\u00c6\3\2\2\2\u00c8/\3\2\2\2\u00c9\u00ca\t"+
		"\6\2\2\u00ca\61\3\2\2\2\u00cb\u00cd\5\66\34\2\u00cc\u00ce\7\16\2\2\u00cd"+
		"\u00cc\3\2\2\2\u00cd\u00ce\3\2\2\2\u00ce\63\3\2\2\2\u00cf\u00d4\7\'\2"+
		"\2\u00d0\u00d1\7\13\2\2\u00d1\u00d3\7\'\2\2\u00d2\u00d0\3\2\2\2\u00d3"+
		"\u00d6\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d5\3\2\2\2\u00d5\65\3\2\2"+
		"\2\u00d6\u00d4\3\2\2\2\u00d7\u00e0\7%\2\2\u00d8\u00e0\7&\2\2\u00d9\u00e0"+
		"\7\'\2\2\u00da\u00db\7\4\2\2\u00db\u00dc\5\32\16\2\u00dc\u00dd\7\6\2\2"+
		"\u00dd\u00e0\3\2\2\2\u00de\u00e0\5\n\6\2\u00df\u00d7\3\2\2\2\u00df\u00d8"+
		"\3\2\2\2\u00df\u00d9\3\2\2\2\u00df\u00da\3\2\2\2\u00df\u00de\3\2\2\2\u00e0"+
		"\67\3\2\2\2\23;JSV\\qw\u008c\u0094\u009d\u00a8\u00b3\u00be\u00c7\u00cd"+
		"\u00d4\u00df";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}