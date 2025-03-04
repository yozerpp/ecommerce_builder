package me.yusuf.ecommerce_builder.transpiler;

import me.yusuf.ecommerce_builder.transpiler.ast.*;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.*;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeBaseVisitor;
import me.yusuf.ecommerce_builder.transpiler.generated.TurkishPseudoCodeParser;

import java.util.ArrayList;
import java.util.List;

public class ASTBuilderVisitor extends TurkishPseudoCodeBaseVisitor<ASTNode> {

    @Override
    public PluginDef visitPluginDef(TurkishPseudoCodeParser.PluginDefContext ctx) {
        var ret = new PluginDef();
        ret.hookedMethod = ctx.id_with_dots().getText();
        ret.hookedException = ctx.hataExpr() != null ? ctx.hataExpr().id_with_dots().getText() : null;
        ret.block = visitBlock(ctx.block());
        ret.name = ctx.IDENTIFIER().getText();
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
        ret.elementName = ctx.IDENTIFIER(1).getText();
        ret.collectionName = ctx.IDENTIFIER(0).getText();
        ret.block = visitBlock(ctx.block());
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
        ret.functionName = ctx.id_with_dots().getText();
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
        for (int i=1; i < ctx.logicalAndExpr().size(); i++) {
            rest.add((LogicalAndExpr) visit(ctx.logicalAndExpr(i )));
        }
        return new Expr(first, rest);
    }

    @Override
    public LogicalAndExpr visitLogicalAndExpr(TurkishPseudoCodeParser.LogicalAndExprContext ctx) {
        // logicalAndExpr: equalityExpr ( VE equalityExpr )*;
        var first = visitEqualityExpr(ctx.equalityExpr(0));
        var rest = new ArrayList<EqualityExpr>();
        for (int i =1; i < ctx.equalityExpr().size(); i++) {
            rest.add( visitEqualityExpr(ctx.equalityExpr(i)));
        }
        return new LogicalAndExpr(first, rest);
    }

    @Override
    public EqualityExpr visitEqualityExpr(TurkishPseudoCodeParser.EqualityExprContext ctx) {
        // equalityExpr: comparisonExpr ( ('==' | '!=') comparisonExpr )*;
        var first = visitComparisonExpr(ctx.comparisonExpr(0));
        var ops = new ArrayList<EqualityExpr.Op>();
        for (int i =1; i < ctx.comparisonExpr().size(); i++) {
            ops.add(new EqualityExpr.Op(ctx.equalityOp(i - 1).getText(),visitComparisonExpr(ctx.comparisonExpr(i ))));
        }
        return new EqualityExpr(first, ops);
    }

    @Override
    public ComparisonExpr visitComparisonExpr(TurkishPseudoCodeParser.ComparisonExprContext ctx) {
        // comparisonExpr: additiveExpr ( ('>' | '<' | '>=' | '<=') additiveExpr )*;
        var first = visitAdditiveExpr(ctx.additiveExpr(0));
        var ops = new ArrayList<ComparisonExpr.Op>();
        for (int i =1; i < ctx.additiveExpr().size(); i++) {
            ops.add(new ComparisonExpr.Op(ctx.comparisonOp(i - 1).getText(),visitAdditiveExpr(ctx.additiveExpr(i))));
        }
        return new ComparisonExpr(first, ops);
    }

    @Override
    public AdditiveExpr visitAdditiveExpr(TurkishPseudoCodeParser.AdditiveExprContext ctx) {
        // additiveExpr: multiplicativeExpr ( ('+' | '-') multiplicativeExpr )*;
        var first = visitMultiplicativeExpr(ctx.multiplicativeExpr(0));
        var ops = new ArrayList<AdditiveExpr.Op>();
        for (int i=1; i < ctx.multiplicativeExpr().size(); i++) {
            ops.add(new AdditiveExpr.Op(ctx.additiveOp(i- 1).getText(),visitMultiplicativeExpr(ctx.multiplicativeExpr(i ))));
        }
        return new AdditiveExpr(first, ops);
    }

    @Override
    public MultiplicativeExpr visitMultiplicativeExpr(TurkishPseudoCodeParser.MultiplicativeExprContext ctx) {
        // multiplicativeExpr: unaryExpr ( ('*' | '/') unaryExpr )*;
        var first = visitUnaryExpr(ctx.unaryExpr(0));
        var ops = new ArrayList<MultiplicativeExpr.Op>();
        for (int i=1; i < ctx.unaryExpr().size(); i++) {
            ops.add(new MultiplicativeExpr.Op(ctx.multiplicativeOp(i-1).getText() //TODO does this never return null?
                    ,visitUnaryExpr(ctx.unaryExpr(i ))));
        }
        return new MultiplicativeExpr(first, ops);
    }

    @Override
    public UnaryExpr visitUnaryExpr(TurkishPseudoCodeParser.UnaryExprContext ctx) {
        // unaryExpr: ( '-' | DEĞİL ) unaryExpr | postfixExpr;
        if (ctx.getChild(0).getText().equals("-") || ctx.getChild(0).getText().equals("değil")) {
            String operator = ctx.getChild(0).getText();
            ASTNode operand = visit(ctx.unaryExpr());
            return new UnaryExpr(operator, (Expression) operand);
        } else {
            var operand = visitPostfixExpr(ctx.postfixExpr());
            return new UnaryExpr(null, (Expression) operand);
        }
    }

    @Override
    public PostfixExpr visitPostfixExpr(TurkishPseudoCodeParser.PostfixExprContext ctx) {
        // postfixExpr: primary ( DEĞİL )?;
        var primary = visitPrimary(ctx.primary());
        boolean hasNot = ctx.DEĞİL() != null;
        return new PostfixExpr((Expression) primary, hasNot);
    }

    @Override
    public Primary visitPrimary(TurkishPseudoCodeParser.PrimaryContext ctx) {
        if (ctx.SAYI()!=null){
            var ret = new Primary.Number();
            if (isFloat(ctx.SAYI().getText()))
                ret.number = Float.parseFloat(ctx.SAYI().getText());
            else ret.number = Integer.parseInt(ctx.SAYI().getText());
            return ret;
        }else if(ctx.YAZI()!=null){
            var ret =new Primary.Str();
            ret.string = ctx.YAZI().getText();
            return ret;
        }else if(ctx.IDENTIFIER()!=null){
            var ret = new Primary.Identifier();
            ret.identifier = ctx.IDENTIFIER().getText();
            return ret;
        }else if(ctx.exprStatement()!=null){
            if (ctx.exprStatement().assignment()!=null){
                var ret= new AssignmentExpr();
                ret.left = ctx.exprStatement().assignment().IDENTIFIER().getText();
                ret.right = visitExpr(ctx.exprStatement().assignment().expr());
                return ret;
            } else if(ctx.exprStatement().functionCall()!=null){
                var ret = new FunctionCallExpr();
                ret.functionName = ctx.exprStatement().functionCall().id_with_dots().getText();
                ret.args = ctx.exprStatement().functionCall().expr().stream().map(this::visitExpr).toArray(Expression[]::new);
                return ret;
            } else throw new RuntimeException("No type matches.");
        } else throw new RuntimeException("No type matches.");
    }
    public static boolean isFloat(String str) {
        return str.matches("[-+]?[0-9]*\\.[0-9]+([eE][-+]?[0-9]+)?[fF]?");
    }
}
