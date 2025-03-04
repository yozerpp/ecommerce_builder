package me.yusuf.ecommerce_builder.transpiler;

import java.lang.reflect.Type;
import java.util.List;

import me.yusuf.ecommerce_builder.shared.PluginRegistry;
import me.yusuf.ecommerce_builder.transpiler.ast.PluginDef;
import me.yusuf.ecommerce_builder.transpiler.ast.ASTNode;
import me.yusuf.ecommerce_builder.transpiler.ast.Block;
import me.yusuf.ecommerce_builder.transpiler.ast.Statement;
import me.yusuf.ecommerce_builder.transpiler.ast.VarDeclarationStatement;
import me.yusuf.ecommerce_builder.transpiler.ast.AssignmentExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.FunctionCallExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.IfStatement;
import me.yusuf.ecommerce_builder.transpiler.ast.LoopStatement;
import me.yusuf.ecommerce_builder.transpiler.ast.ForeachStatement;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.UnaryExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.PostfixExpr;
import me.yusuf.ecommerce_builder.transpiler.ast.expression.Expression;
import me.yusuf.utils.ReflectionUtils;
import me.yusuf.utils.StringUtils;

public class CodeGeneratorVisitor {

    public CodeGeneratorVisitor() {}

    public Plugin generate(PluginDef pluginDef) {
        String code = visitPluginDef(pluginDef);
        Type[] argTypes = getMethodArguementTypes(code);
        return new Plugin(new PluginRegistry.PluginMetadata(argTypes, ReflectionUtils.loadMethodFromFullyQualifiedName(pluginDef.hookedMethod)),
                code);
    }
    public String visitPluginDef(PluginDef pd) {
        return "public class " + pd.name + "Plugin implements Runnable {\n" +
               "    @Override\n" +
               "    public void run() {\n" +
               indent(visitBlock(pd.block)) +
               "\n    }\n" +
               "}\n";
    }

    public String visitBlock(Block block) {
        StringBuilder sb = new StringBuilder();
        for (Statement stmt : block.statements) {
            sb.append(visitStatement(stmt)).append("\n");
        }
        return sb.toString();
    }

    public String visitStatement(Statement node) {
        return switch (node) {
            case IfStatement ifStatement -> visitIfStatement(ifStatement);
            case LoopStatement loopStatement -> visitLoopStatement(loopStatement);
            case ForeachStatement foreachStatement -> visitForeachStatement(foreachStatement);
            case VarDeclarationStatement varDeclarationStatement ->
                    visitVarDeclarationStatement(varDeclarationStatement);
            case AssignmentExpr assignmentExpr -> visitAssignmentExpr(assignmentExpr);
            case FunctionCallExpr functionCallExpr -> visitFunctionCallExpr(functionCallExpr);
            case Block block -> visitBlock(block);
            case null -> "";
            default -> throw new RuntimeException("Unknown node type: " + node.getClass().getSimpleName());
        };
    }

    public String visitVarDeclarationStatement(VarDeclarationStatement vds) {
        return "var " + vds.expr.left + " = " + visitExpression(vds.expr.right) + ";";
    }

    public String visitAssignmentExpr(AssignmentExpr asn) {
        return asn.left + " = " + visitExpression(asn.right) + ";";
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
        return fce.functionName + "(" + args + ");";
    }

    public String visitIfStatement(IfStatement ifs) {
        StringBuilder sb = new StringBuilder();
        sb.append("if(").append(visitExpression(ifs.condition)).append(") {\n");
        sb.append(indent(visitBlock(ifs.happyPath))).append("\n}");
        if (ifs.sadPath != null) {
            sb.append(" else {\n").append(indent(visitBlock(ifs.sadPath))).append("\n}");
        }
        return sb.toString();
    }

    public String visitLoopStatement(LoopStatement ls) {
        return "while(" + visitExpression(ls.condition) + ") {\n" +
               indent(visitBlock(ls.block)) + "\n}";
    }

    public String visitForeachStatement(ForeachStatement fe) {
        return "for(var " + fe.elementName + " : " + fe.collectionName + ") {\n    // body\n}";
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

    public String visitExpression(Expression expr) {
        return switch (expr) {
            case UnaryExpr unaryExpr -> visitUnaryExpr(unaryExpr);
            case PostfixExpr postfixExpr -> visitPostfixExpr(postfixExpr);
            case AssignmentExpr assignmentExpr -> visitAssignmentExpr(assignmentExpr);
            case FunctionCallExpr functionCallExpr -> visitFunctionCallExpr(functionCallExpr);
            case null-> throw new RuntimeException("expression is null.");
            default ->
                    throw new RuntimeException("Unknown expression type: " + expr.getClass().getSimpleName());
        };
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

    private String indent(String code) {
        String indent = "        ";
        return indent + code.replace("\n", "\n" + indent);
    }
    
    public Type[] getMethodArguementTypes(String source) {
        // Assumes methodSignature is in the format:
        // "public void methodName(java.lang.String, java.lang.Integer)"
        String paramStr =  StringUtils.find(source, "run\\((.*)\\)\\s*\\{").getFirst()[0];
        List<String[]> params = StringUtils.findNamed(paramStr, "(?<argTp>\\w+(?:\\.\\w+)*)\\s+\\w+,?",new String[]{"argTp"});
        return params.stream().map(p -> p[0]).map(p->{
            try {
                return Class.forName(p);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toArray(Type[]::new);
    }

}
