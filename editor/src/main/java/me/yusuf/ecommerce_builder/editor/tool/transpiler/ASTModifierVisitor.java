package me.yusuf.ecommerce_builder.editor.tool.transpiler;

import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.*;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression.*;

public interface ASTModifierVisitor {
    default Object visitBlock(Block block) {
        for (Statement stmt : block.getStatements()) {
            visitStatement(stmt);
        }
        return null;
    }
    default Object visitPluginDef(PluginDef pd){
        visitBlock(pd.getBlock());
        return null;
    }
    default Object visitStatement(Statement node) {
       return switch (node) {
            case VarDeclarationStatement vds -> visitVarDeclarationStatement(vds);
            case IfStatement ifs -> visitIfStatement(ifs);
            case LoopStatement ls -> visitLoopStatement(ls);
            case ForeachStatement fe -> visitForeachStatement(fe);
            case ExpressionStatement es -> visitExpressionStatement(es);
            case Block b -> visitBlock(b);
            default -> throw new RuntimeException("Unknown statement type: " + node.getClass().getSimpleName());
        };
    }

    default Object visitVarDeclarationStatement(VarDeclarationStatement vds) {
        visitExpression(vds.getValue());
        return null;
    }

    default Object visitIfStatement(IfStatement ifs) {
        visitExpression(ifs.getCondition());
        visitBlock(ifs.getBlock());
        if (ifs.getSadPath() != null) {
            visitBlock(ifs.getSadPath());
        }
        return null;
    }

    default Object visitLoopStatement(LoopStatement ls) {
        visitExpression(ls.getCondition());
        visitBlock(ls.getBlock());
        return null;
    }

    default Object visitForeachStatement(ForeachStatement fe) {
        visitExpression(fe.getCollection());
        visitBlock(fe.getBlock());
        return null;
    }

    default Object visitExpressionStatement(ExpressionStatement a) {
        if (a instanceof FunctionCallExpr fce) {
            return visitFunctionCallExpr(fce);
        } else if (a instanceof AssignmentExpr ae) {
            return visitAssignmentExpr(ae);
        }
        throw new IllegalStateException("Unexpected value: " + a);
    }
    default Object visitExpression(Expression expr) {
        return switch (expr) {
            case UnaryExpr unaryExpr -> visitUnaryExpr(unaryExpr);
            case PostfixExpr postfixExpr -> visitPostfixExpr(postfixExpr);
            case LogicalAndExpr lae -> visitLogicalAndExpr(lae);
            case EqualityExpr ee -> visitEqualityExpr(ee);
            case ComparisonExpr ce -> visitComparisonExpr(ce);
            case AdditiveExpr ae -> visitAdditiveExpr(ae);
            case MultiplicativeExpr me -> visitMultiplicativeExpr(me);
            case Expr e -> visitExpr(e);
            case FunctionCallExpr fc -> visitFunctionCallExpr(fc);
            case AssignmentExpr as -> visitAssignmentExpr(as);
            case Primary p-> visitPrimary(p);
            default ->
                    throw new RuntimeException("Unknown expression type: " + expr.getClass().getSimpleName());
        };
    }
    default Object visitPrimary(Primary p){
        if (p instanceof Primary.StringLiteral sl)
           return visitStringLiteral(sl);
        else if (p instanceof Primary.Identifier id)
            return visitIdentifier(id);
        else
            return visitNumber((Primary.Number) p);
    }
    default Object visitStringLiteral(Primary.StringLiteral literal){
        return null;
    }
    default Object visitIdentifier(Primary.Identifier id){
        if (id.memberAccess!=null) visitIdentifier(id.memberAccess);
        return null;
    }
    default Object visitNumber(Primary.Number number){
        return null;
    }
    default Object visitAssignmentExpr(AssignmentExpr asn) {
        visitExpression(asn.getRight());
        return null;
    }

    default Object visitFunctionCallExpr(FunctionCallExpr fce) {
        if (fce.getArgs() != null) {
            for (Expression arg : fce.getArgs()) {
                visitExpression(arg);
            }
        }
        return null;
    }

    default Object visitUnaryExpr(UnaryExpr ue) {
        visitPostfixExpr(ue.operand());
        return null;
    }

    default Object visitPostfixExpr(PostfixExpr pe) {
        visitExpression(pe.primary());
        return null;
    }

    default Object visitLogicalAndExpr(LogicalAndExpr lae) {
        visitEqualityExpr(lae.first());
        if (lae.rest() != null) {
            for (EqualityExpr eq : lae.rest()) {
                visitEqualityExpr(eq);
            }
        }
        return null;
    }

    default Object visitEqualityExpr(EqualityExpr ee) {
        visitComparisonExpr(ee.first());
        if (ee.ops() != null) {
            for (EqualityExpr.Op op : ee.ops()) {
                visitComparisonExpr(op.expr());
            }
        }
        return null;
    }

    default Object visitComparisonExpr(ComparisonExpr ce) {
        visitAdditiveExpr(ce.first());
        if (ce.ops() != null) {
            for (ComparisonExpr.Op op : ce.ops()) {
                visitAdditiveExpr(op.expr());
            }
        }
        return null;
    }

    default Object visitAdditiveExpr(AdditiveExpr ae) {
        visitMultiplicativeExpr(ae.first());
        if (ae.ops() != null) {
            for (AdditiveExpr.Op op : ae.ops()) {
                visitMultiplicativeExpr(op.expr());
            }
        }
        return null;
    }

    default Object visitMultiplicativeExpr(MultiplicativeExpr me) {
        visitUnaryExpr(me.first());
        if (me.ops() != null) {
            for (MultiplicativeExpr.Op op : me.ops()) {
                visitUnaryExpr(op.expr());
            }
        }
        return null;
    }

    default Object visitExpr(Expr e) {
        visitLogicalAndExpr(e.first());
        if (e.rest() != null) {
            for (LogicalAndExpr lae : e.rest()) {
                visitLogicalAndExpr(lae);
            }
        }
        return null;
    }
}
