// Generated from ./TurkishPseudoCode.g4 by ANTLR 4.9.2
package me.yusuf.ecommerce_builder.transpiler.generated;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link TurkishPseudoCodeParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface TurkishPseudoCodeVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#pluginDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPluginDef(TurkishPseudoCodeParser.PluginDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#hataExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHataExpr(TurkishPseudoCodeParser.HataExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(TurkishPseudoCodeParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#functionCall}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCall(TurkishPseudoCodeParser.FunctionCallContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#exprStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStatement(TurkishPseudoCodeParser.ExprStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#foreachStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForeachStatement(TurkishPseudoCodeParser.ForeachStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(TurkishPseudoCodeParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(TurkishPseudoCodeParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(TurkishPseudoCodeParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#varDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVarDeclaration(TurkishPseudoCodeParser.VarDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(TurkishPseudoCodeParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#yazdir}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitYazdir(TurkishPseudoCodeParser.YazdirContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(TurkishPseudoCodeParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(TurkishPseudoCodeParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#logicalAndExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpr(TurkishPseudoCodeParser.LogicalAndExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#equalityExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEqualityExpr(TurkishPseudoCodeParser.EqualityExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#comparisonExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonExpr(TurkishPseudoCodeParser.ComparisonExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#additiveExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAdditiveExpr(TurkishPseudoCodeParser.AdditiveExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#multiplicativeExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplicativeExpr(TurkishPseudoCodeParser.MultiplicativeExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#unaryExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnaryExpr(TurkishPseudoCodeParser.UnaryExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#postfixExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostfixExpr(TurkishPseudoCodeParser.PostfixExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link TurkishPseudoCodeParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrimary(TurkishPseudoCodeParser.PrimaryContext ctx);
}