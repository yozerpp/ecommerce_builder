package me.yusuf.ecommerce_builder.transpiler;

import me.yusuf.ecommerce_builder.transpiler.ast.*;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.*;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeBaseVisitor;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeParser;

import java.util.ArrayList;

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

    @Override
    public VarDeclarationStatement visitVarDeclaration(TurkishPseudoCodeParser.VarDeclarationContext ctx) {
        var ret = new VarDeclarationStatement();
        ret.expr = visitAssignment(ctx.assignment());
        return ret;
    }

    @Override
    public LoopStatement visitLoopStatement(TurkishPseudoCodeParser.LoopStatementContext ctx) {
        var ret = new LoopStatement();
        ret.condition = visitExpr(ctx.expr());
        ret.block = visitBlock(ctx.block());
        return ret;
    }
    // Expression visitor methods

    @Override
    public IfStatement visitIfStatement(TurkishPseudoCodeParser.IfStatementContext ctx) {
        var ret = new IfStatement();
        ret.condition = visitExpr(ctx.expr());
        ret.happyPath = visitBlock(ctx.block(0));
        if (ctx.block().size() > 1) {
            ret.sadPath = visitBlock(ctx.block(1));
        }
        return ret;
    }

    @Override
    public ForeachStatement visitForeachStatement(TurkishPseudoCodeParser.ForeachStatementContext ctx) {
        var ret= new ForeachStatement();
        ret.elementName = ctx.IDENTIFIER(0).getText();
        ret.collectionName = ctx.IDENTIFIER(1).getText();
        return ret;
    }

    @Override
    public ExpressionStatement visitExprStatement(TurkishPseudoCodeParser.ExprStatementContext ctx) {
        if (ctx.functionCall()!=null)
            return visitFunctionCall(ctx.functionCall());
        else if (ctx.assignment()!=null)
            return visitAssignment(ctx.assignment());
        else throw new RuntimeException("Unknown expression type: " + ctx.getText());
    }

    @Override
    public FunctionCallExpr visitFunctionCall(TurkishPseudoCodeParser.FunctionCallContext ctx) {
        var ret = new FunctionCallExpr();
        ret.args = ctx.expr().stream().map(this::visitExpr).toArray(Expression[]::new);
        ret.functionName = ctx.IDENTIFIER().getText();
        return ret;
    }

    @Override
    public AssignmentExpr visitAssignment(TurkishPseudoCodeParser.AssignmentContext ctx) {
        var ret = new AssignmentExpr();
        ret.left = ctx.IDENTIFIER().getText();
        ret.right = visitExpr(ctx.expr());
        return ret;
    }

    @Override
    public Expr visitExpr(TurkishPseudoCodeParser.ExprContext ctx) {
        // logicalOrExpr: logicalAndExpr ( VEYA logicalAndExpr )*;
        var first = (LogicalAndExpr) visitLogicalAndExpr(ctx.logicalAndExpr(0));
        var rest = new ArrayList<LogicalAndExpr>();
        for (int i = 1; i < ctx.logicalAndExpr().size(); i++) {
            rest.add((LogicalAndExpr) visit(ctx.logicalAndExpr(i)));
        }
        return new Expr(first, rest);
    }

    @Override
    public LogicalAndExpr visitLogicalAndExpr(TurkishPseudoCodeParser.LogicalAndExprContext ctx) {
        // logicalAndExpr: equalityExpr ( VE equalityExpr )*;
        var first = visitEqualityExpr(ctx.equalityExpr(0));
        var rest = new ArrayList<EqualityExpr>();
        for (int i = 1; i < ctx.equalityExpr().size(); i++) {
            rest.add( visitEqualityExpr(ctx.equalityExpr(i)));
        }
        return new LogicalAndExpr(first, rest);
    }

    @Override
    public EqualityExpr visitEqualityExpr(TurkishPseudoCodeParser.EqualityExprContext ctx) {
        // equalityExpr: comparisonExpr ( ('==' | '!=') comparisonExpr )*;
        var first = visitComparisonExpr(ctx.comparisonExpr(0));
        var ops = new ArrayList<EqualityExpr.Op>();
        for (int i = 1; i < ctx.comparisonExpr().size(); i++) {
            String operator = ctx.getChild(2 * i - 1).getText();
            var rhs =  visitComparisonExpr(ctx.comparisonExpr(i));
            ops.add(new EqualityExpr.Op(operator, rhs));
        }
        return new EqualityExpr(first, ops);
    }

    @Override
    public ComparisonExpr visitComparisonExpr(TurkishPseudoCodeParser.ComparisonExprContext ctx) {
        // comparisonExpr: additiveExpr ( ('>' | '<' | '>=' | '<=') additiveExpr )*;
        var first = visitAdditiveExpr(ctx.additiveExpr(0));
        var ops = new ArrayList<ComparisonExpr.Op>();
        for (int i = 1; i < ctx.additiveExpr().size(); i++) {
            String operator = ctx.getChild(2 * i - 1).getText();
            var rhs = visitAdditiveExpr(ctx.additiveExpr(i));
            ops.add(new ComparisonExpr.Op(operator, rhs));
        }
        return new ComparisonExpr(first, ops);
    }

    @Override
    public AdditiveExpr visitAdditiveExpr(TurkishPseudoCodeParser.AdditiveExprContext ctx) {
        // additiveExpr: multiplicativeExpr ( ('+' | '-') multiplicativeExpr )*;
        var first = visitMultiplicativeExpr(ctx.multiplicativeExpr(0));
        var ops = new ArrayList<AdditiveExpr.Op>();
        for (int i = 1; i < ctx.multiplicativeExpr().size(); i++) {
            String operator = ctx.getChild(2 * i - 1).getText();
            var rhs = visitMultiplicativeExpr(ctx.multiplicativeExpr(i));
            ops.add(new AdditiveExpr.Op(operator, rhs));
        }
        return new AdditiveExpr(first, ops);
    }

    @Override
    public ASTNode visitMultiplicativeExpr(TurkishPseudoCodeParser.MultiplicativeExprContext ctx) {
        // multiplicativeExpr: unaryExpr ( ('*' | '/') unaryExpr )*;
        var first = (UnaryExpr) visit(ctx.unaryExpr(0));
        var ops = new ArrayList<MultiplicativeExpr.Op>();
        for (int i = 1; i < ctx.unaryExpr().size(); i++) {
            String operator = ctx.getChild(2 * i - 1).getText();
            var rhs = (UnaryExpr) visit(ctx.unaryExpr(i));
            ops.add(new MultiplicativeExpr.Op(operator, rhs));
        }
        return new MultiplicativeExpr(first, ops);
    }

    @Override
    public ASTNode visitUnaryExpr(TurkishPseudoCodeParser.UnaryExprContext ctx) {
        // unaryExpr: ( '-' | DEĞİL ) unaryExpr | postfixExpr;
        if (ctx.getChild(0).getText().equals("-") || ctx.getChild(0).getText().equals("değil")) {
            String operator = ctx.getChild(0).getText();
            ASTNode operand = visit(ctx.unaryExpr());
            return new UnaryExpr(operator, (Expression) operand);
        } else {
            ASTNode operand = visit(ctx.postfixExpr());
            return new UnaryExpr(null, (Expression) operand);
        }
    }

    @Override
    public ASTNode visitPostfixExpr(TurkishPseudoCodeParser.PostfixExprContext ctx) {
        // postfixExpr: primary ( DEĞİL )?;
        ASTNode primary = visit(ctx.primary());
        boolean hasNot = ctx.DEĞİL() != null;
        return new PostfixExpr((Expression) primary, hasNot);
    }
}
