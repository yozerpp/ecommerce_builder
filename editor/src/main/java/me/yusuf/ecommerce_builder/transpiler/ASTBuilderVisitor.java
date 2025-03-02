package me.yusuf.ecommerce_builder.transpiler;

import me.yusuf.ecommerce_builder.transpiler.ast.ASTNode;
import me.yusuf.ecommerce_builder.transpiler.ast.Block;
import me.yusuf.ecommerce_builder.transpiler.ast.PluginDef;
import me.yusuf.ecommerce_builder.transpiler.ast.Statement;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeBaseVisitor;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeParser;
import me.yusuf.utils.StringUtils;

public class ASTBuilderVisitor extends TurkishPseudoCodeBaseVisitor<ASTNode> {
    @Override
    public PluginDef visitPluginDef(TurkishPseudoCodeParser.PluginDefContext ctx) {
        var ret = new PluginDef();
        ret.hookedMethod = ctx.IDENTIFIER().getText();
        ret.hookedException = ctx.hataExpr() != null ? ctx.hataExpr().IDENTIFIER().getText() : null;
        ret.block = visitBlock(ctx.block());
        return ret;
    }

    @Override
    public Block visitBlock(TurkishPseudoCodeParser.BlockContext ctx) {
        var ret = new Block();
        for (var stmt : ctx.statement()){
            ret.statements.add(visitStatement(stmt));
        }
        return ret;
    }

    @Override
    public Statement visitStatement(TurkishPseudoCodeParser.StatementContext ctx) {
        if (ctx.loopStatement() != null) {
            return visitLoopStatement(ctx.loopStatement());
        } else if (ctx.ifStatement() != null) {
            return visitIfStatement(ctx.ifStatement());
        } else if (ctx.foreachStatement() != null) {
            return visitForeachStatement(ctx.foreachStatement());
        } else if (ctx.varDeclaration() != null) {
            return visitVarDeclaration(ctx.varDeclaration());
        } else if (ctx.exprStatement() != null) {
            return visitExprStatement(ctx.exprStatement());
        } else if (ctx.block() != null) {
            return visitBlock(ctx.block());
        }
        throw new IllegalArgumentException("Unknown statement type: " + ctx.getText());
    }

//    @Override
//    public  visitLoopStatement(TurkishPseudoCodeParser.LoopStatementContext ctx) {
//        ctx.
//    }
}
