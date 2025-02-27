// Generated from TürkçePsödoKod.g4 by ANTLR 4.9.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link TürkçePsödoKodParser}.
 */
public interface TürkçePsödoKodListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#prog}.
	 * @param ctx the parse tree
	 */
	void enterProg(TürkçePsödoKodParser.ProgContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#prog}.
	 * @param ctx the parse tree
	 */
	void exitProg(TürkçePsödoKodParser.ProgContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDef(TürkçePsödoKodParser.FunctionDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDef(TürkçePsödoKodParser.FunctionDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#paramList}.
	 * @param ctx the parse tree
	 */
	void enterParamList(TürkçePsödoKodParser.ParamListContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#paramList}.
	 * @param ctx the parse tree
	 */
	void exitParamList(TürkçePsödoKodParser.ParamListContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(TürkçePsödoKodParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(TürkçePsödoKodParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#exprStatement}.
	 * @param ctx the parse tree
	 */
	void enterExprStatement(TürkçePsödoKodParser.ExprStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#exprStatement}.
	 * @param ctx the parse tree
	 */
	void exitExprStatement(TürkçePsödoKodParser.ExprStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#foreachStatement}.
	 * @param ctx the parse tree
	 */
	void enterForeachStatement(TürkçePsödoKodParser.ForeachStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#foreachStatement}.
	 * @param ctx the parse tree
	 */
	void exitForeachStatement(TürkçePsödoKodParser.ForeachStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(TürkçePsödoKodParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(TürkçePsödoKodParser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(TürkçePsödoKodParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(TürkçePsödoKodParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(TürkçePsödoKodParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(TürkçePsödoKodParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVarDeclaration(TürkçePsödoKodParser.VarDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#varDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVarDeclaration(TürkçePsödoKodParser.VarDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(TürkçePsödoKodParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(TürkçePsödoKodParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#yazdir}.
	 * @param ctx the parse tree
	 */
	void enterYazdir(TürkçePsödoKodParser.YazdirContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#yazdir}.
	 * @param ctx the parse tree
	 */
	void exitYazdir(TürkçePsödoKodParser.YazdirContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(TürkçePsödoKodParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(TürkçePsödoKodParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCall(TürkçePsödoKodParser.FunctionCallContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#functionCall}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCall(TürkçePsödoKodParser.FunctionCallContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#condition}.
	 * @param ctx the parse tree
	 */
	void enterCondition(TürkçePsödoKodParser.ConditionContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#condition}.
	 * @param ctx the parse tree
	 */
	void exitCondition(TürkçePsödoKodParser.ConditionContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(TürkçePsödoKodParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(TürkçePsödoKodParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpr(TürkçePsödoKodParser.LogicalOrExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#logicalOrExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpr(TürkçePsödoKodParser.LogicalOrExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpr(TürkçePsödoKodParser.LogicalAndExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpr(TürkçePsödoKodParser.LogicalAndExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void enterEqualityExpr(TürkçePsödoKodParser.EqualityExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#equalityExpr}.
	 * @param ctx the parse tree
	 */
	void exitEqualityExpr(TürkçePsödoKodParser.EqualityExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#comparisonExpr}.
	 * @param ctx the parse tree
	 */
	void enterComparisonExpr(TürkçePsödoKodParser.ComparisonExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#comparisonExpr}.
	 * @param ctx the parse tree
	 */
	void exitComparisonExpr(TürkçePsödoKodParser.ComparisonExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void enterAdditiveExpr(TürkçePsödoKodParser.AdditiveExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#additiveExpr}.
	 * @param ctx the parse tree
	 */
	void exitAdditiveExpr(TürkçePsödoKodParser.AdditiveExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void enterMultiplicativeExpr(TürkçePsödoKodParser.MultiplicativeExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 */
	void exitMultiplicativeExpr(TürkçePsödoKodParser.MultiplicativeExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(TürkçePsödoKodParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#unaryExpr}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(TürkçePsödoKodParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void enterPostfixExpr(TürkçePsödoKodParser.PostfixExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#postfixExpr}.
	 * @param ctx the parse tree
	 */
	void exitPostfixExpr(TürkçePsödoKodParser.PostfixExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link TürkçePsödoKodParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterPrimary(TürkçePsödoKodParser.PrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link TürkçePsödoKodParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitPrimary(TürkçePsödoKodParser.PrimaryContext ctx);
}