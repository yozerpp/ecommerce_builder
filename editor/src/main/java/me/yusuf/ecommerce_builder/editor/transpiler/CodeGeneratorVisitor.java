package me.yusuf.ecommerce_builder.editor.transpiler;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import me.yusuf.ecommerce_builder.editor.transpiler.ast.*;
import me.yusuf.ecommerce_builder.editor.transpiler.ast.expression.*;
import me.yusuf.ecommerce_builder.shared.types.Plugin;
import me.yusuf.ecommerce_builder.shared.types.PluginSourceAndMetadata;
import me.yusuf.ecommerce_builder.shared.types.PluginMetadata;
import me.yusuf.utils.StringUtils;

public class CodeGeneratorVisitor {

    private final Map<String, String> defines;
    public CodeGeneratorVisitor(Map<String, String> defines) {
        this.defines = defines;
    }

    public PluginSourceAndMetadata generate(PluginDef pluginDef, int editorId) {
        String code = visitPluginDef(pluginDef,editorId);
        Type[] argTypes = getMethodArguementTypes(code);
        return new PluginSourceAndMetadata(new Plugin.Id(editorId,pluginDef.getName(), pluginDef.getHookedMethod()),new PluginMetadata(argTypes),
                code);
    }
    public String visitPluginDef(PluginDef pd, int editorId) {
        return "public class " + pd.getName() + "Plugin_" +editorId+ " {\n" +
               "    public static void run() {\n" +
                indent(visitBlock(pd.getBlock()),2) +
                "    }\n"+
               "}\n";
    }

    public String visitBlock(Block block) {
        StringBuilder sb = new StringBuilder();
        for (Statement stmt : block.getStatements()) {
            sb.append(visitStatement(stmt)).append("\n");
        }
        return sb.toString();
    }

    public String visitVarDeclarationStatement(VarDeclarationStatement vds) {
        return "var " + vds.getVarName()+ " = " + visitExpression(vds.getValue()) + ";";
    }

    public String visitAssignmentExpr(AssignmentExpr asn) {
        return asn.getLeft() + " = " + visitExpression(asn.getRight());
    }

    public String visitFunctionCallExpr(FunctionCallExpr fce) {
        String args = "";
        if (fce.getArgs() != null && fce.getArgs().length > 0) {
            String[] argStr = new String[fce.getArgs().length];
            for (int i = 0; i < fce.getArgs().length; i++) {
                argStr[i] = visitExpression(fce.getArgs()[i]);
            }
            args = String.join(", ", argStr);
        }
        String fname = defines.containsKey(fce.getFunctionName())?defines.get(fce.getFunctionName()):fce.getFunctionName();
        return fname + "(" + args + ")";
    }

    public String visitIfStatement(IfStatement ifs) {
        StringBuilder sb = new StringBuilder();
        sb.append("if(").append(visitExpression(ifs.getCondition())).append(") {\n");
        sb.append(indent(visitBlock(ifs.getBlock())));
        sb.append("}");
        if (ifs.getSadPath() != null) {
            sb.append(" else {\n").append(indent(visitBlock(ifs.getSadPath())));
            sb.append("}");
        }
        return sb.toString();
    }

    public String visitLoopStatement(LoopStatement ls) {
        return "while(" + visitExpression(ls.getCondition()) + ") {\n" +
               indent(visitBlock(ls.getBlock())) + "}";
    }

    public String visitForeachStatement(ForeachStatement fe) {
        return "for(var " + fe.getElementName()+ " : " + fe.getCollectionName() + ") {\n" +
                indent(visitBlock(fe.getBlock())) + "}";
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
        if (node instanceof Expression ex)
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
            case Primary p-> p.toString();
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
    private String indent(String code, int level) {
        if (code.isEmpty()) return code;
        String indent = "    ".repeat(level);
        return indent + code.replaceAll("\n(?!$)", "\n" + indent);
    }
    private String indent(String code){
        return indent(code, 1);
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
