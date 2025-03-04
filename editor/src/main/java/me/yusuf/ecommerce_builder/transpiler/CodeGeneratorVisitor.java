package me.yusuf.ecommerce_builder.transpiler;

import java.lang.reflect.Type;
import java.util.List;

import me.yusuf.ecommerce_builder.shared.PluginRegistry;
import me.yusuf.ecommerce_builder.transpiler.ast.*;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.*;
import me.yusuf.utils.ReflectionUtils;
import me.yusuf.utils.StringUtils;

public class CodeGeneratorVisitor {

    public CodeGeneratorVisitor() {}

    public Plugin generate(PluginDef pluginDef) {
        String code = visitPluginDef(pluginDef);
        Type[] argTypes = getMethodArguementTypes(code);
        return new Plugin(new PluginRegistry.PluginMetadata(argTypes, ReflectionUtils.loadMethodFromFullyQualifiedName(pluginDef.hookedMethod, null)),
                code);
    }
    public String visitPluginDef(PluginDef pd) {
        return "public class " + pd.name + "Plugin implements Runnable {\n" +
               "    @Override\n" +
               "    public void run() " +
               indentBlock(visitBlock(pd.block)) +
               "}\n";
    }

    public String visitBlock(Block block) {
        StringBuilder sb = new StringBuilder("{\n");
        for (Statement stmt : block.statements) {
            sb.append(visitStatement(stmt)).append("\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

    public String visitVarDeclarationStatement(VarDeclarationStatement vds) {
        return "var " + vds.expr.left + " = " + visitExpression(vds.expr.right) + ";";
    }

    public String visitAssignmentExpr(AssignmentExpr asn) {
        return asn.left + " = " + visitExpression(asn.right);
    }

    public String visitFunctionCallExpr(FunctionCallExpr fce) {
        String args = "";
        if (fce.args != null && fce.args.length > 0) {
            String[] argStr = new String[fce.args.length];
            for (int i = 0; i < fce.args.length; i++) {
                argStr[i] = visitExpression(fce.args[i]);
            }
            args = String.join(", ", argStr);
        }
        return fce.functionName + "(" + args + ")";
    }

    public String visitIfStatement(IfStatement ifs) {
        StringBuilder sb = new StringBuilder();
        sb.append("if(").append(visitExpression(ifs.condition)).append(")");
        sb.append(indentBlock(visitBlock(ifs.happyPath)));
        if (ifs.sadPath != null) {
            sb.append(" else").append(indentBlock(visitBlock(ifs.sadPath)));
        }
        return sb.toString();
    }

    public String visitLoopStatement(LoopStatement ls) {
        return "while(" + visitExpression(ls.condition) + ")" +
               indentBlock(visitBlock(ls.block));
    }

    public String visitForeachStatement(ForeachStatement fe) {
        return "for(var " + fe.elementName + " : " + fe.collectionName + ")" +
                visitBlock(fe.block);
    }

    public String visitUnaryExpr(UnaryExpr ue) {
        String op = ue.operator;
        if (op != null) {
            if (op.equals("değil")) {
                op = "!";
            }
            return "(" + op + visitExpression(ue.operand) + ")";
        } else {
            return visitExpression(ue.operand);
        }
    }

    public String visitPostfixExpr(PostfixExpr pe) {
        String code = visitExpression(pe.primary);
        if (pe.hasNot) {
            code += "!";
        }
        return code;
    }

    public String visitLogicalAndExpr(LogicalAndExpr lae) {
        StringBuilder result = new StringBuilder(visitEqualityExpr(lae.first));
        if (lae.rest != null) {
            for (EqualityExpr eq : lae.rest) {
                result.append(" && ").append(visitEqualityExpr(eq));
            }
        }
        return result.toString();
    }

    public String visitEqualityExpr(EqualityExpr ee) {
        StringBuilder result = new StringBuilder(visitComparisonExpr(ee.first));
        if (ee.ops != null) {
            for (EqualityExpr.Op op : ee.ops) {
                result.append(" ").append(op.operator).append(" ").append(visitComparisonExpr(op.expr));
            }
        }
        return result.toString();
    }

    public String visitComparisonExpr(ComparisonExpr ce) {
        StringBuilder result = new StringBuilder(visitAdditiveExpr(ce.first));
        if (ce.ops != null) {
            for (ComparisonExpr.Op op : ce.ops) {
                result.append(" ").append(op.operator).append(" ").append(visitAdditiveExpr(op.expr));
            }
        }
        return result.toString();
    }

    public String visitAdditiveExpr(AdditiveExpr ae) {
        StringBuilder result = new StringBuilder(visitMultiplicativeExpr(ae.first));
        if (ae.ops != null) {
            for (AdditiveExpr.Op op : ae.ops) {
                result.append(" ").append(op.operator).append(" ").append(visitMultiplicativeExpr(op.expr));
            }
        }
        return result.toString();
    }

    public String visitMultiplicativeExpr(MultiplicativeExpr me) {
        StringBuilder result = new StringBuilder(visitUnaryExpr(me.first));
        if (me.ops != null) {
            for (MultiplicativeExpr.Op op : me.ops) {
                result.append(" ").append(op.operator).append(" ").append(visitUnaryExpr(op.expr));
            }
        }
        return result.toString();
    }

    public String visitExpr(Expr e) {
        StringBuilder result = new StringBuilder(visitLogicalAndExpr(e.first));
        if (e.rest != null) {
            for (LogicalAndExpr lae : e.rest) {
                result.append(" || ").append(visitLogicalAndExpr(lae));
            }
        }
        return result.toString();
    }
    public String visitASTNode(ASTNode node) {
        if (node instanceof PluginDef pd) {
            return visitPluginDef(pd);
        } else if (node instanceof Expression ex)
            return visitExpression(ex);
        else if (node instanceof Statement st)
            return visitStatement(st);
        return node.toString();
    }
    public String visitStatement(Statement node) {
        return switch (node) {
            case IfStatement ifStatement -> visitIfStatement(ifStatement);
            case LoopStatement loopStatement -> visitLoopStatement(loopStatement);
            case ForeachStatement foreachStatement -> visitForeachStatement(foreachStatement);
            case VarDeclarationStatement varDeclarationStatement ->
                    visitVarDeclarationStatement(varDeclarationStatement);
            case ExpressionStatement es -> visitExpressionStatement(es);
            case Block block -> visitBlock(block);
            default -> throw new RuntimeException("Unknown node type: " + node.getClass().getSimpleName());
        };
    }
    public String visitExpression(Expression expr) {
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
            case Primary p -> p.toString();
            default ->
                    throw new RuntimeException("Unknown expression type: " + expr.getClass().getSimpleName());
        };
    }
    public String visitExpressionStatement(ExpressionStatement a) {
        return switch (a) {
            case FunctionCallExpr fe -> visitFunctionCallExpr(fe);
            case AssignmentExpr as -> visitAssignmentExpr(as);
            default -> throw new IllegalStateException("Unexpected value: " + a);
        } + ";";
    }
    // first line, last and before the last lines are not indented.
    private String indentBlock(String code) {
        String indent = "        ";
        String[] lines = code.split("\n", -1);
        if (lines.length < 3) {
            return code;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.length; i++) {
            if (i == 0 || i >= lines.length - 2) {
                sb.append(lines[i]);
            } else {
                sb.append(indent).append(lines[i]);
            }
            if (i < lines.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
    
    public Type[] getMethodArguementTypes(String source) {
        // Assumes methodSignature is in the format:
        // "public void methodName(java.lang.String, java.lang.Integer)"
        String paramStr = StringUtils.find(source, "run\\((.*)\\)\\s*\\{").getFirst()[0];
        List<String[]> params = StringUtils.findNamed(paramStr, "(?<argTp>\\w+(?:\\.\\w+)*)\\s+\\w+,?", new String[]{"argTp"});
        return params.stream().map(p -> p[0]).map(p -> {
            try {
                return Class.forName(p);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toArray(Type[]::new);
    }

}
