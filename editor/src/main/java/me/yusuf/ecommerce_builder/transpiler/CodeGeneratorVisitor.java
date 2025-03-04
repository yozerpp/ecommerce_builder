package me.yusuf.ecommerce_builder.transpiler;

import java.lang.reflect.Type;
import java.util.ArrayList;
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

public class CodeGeneratorVisitor {

    public CodeGeneratorVisitor() {}

    public Plugin generate(PluginDef pluginDef) {
        String code = visitPluginDef(pluginDef);
        return new Plugin(new PluginRegistry.PluginMetadata(null, ReflectionUtils.loadMethodFromFullyQualifiedName(pluginDef.hookedMethod)),
                code); //TODO: Parse arge types.
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

    public String visitStatement(Statement stmt) {
        return visitASTNode((ASTNode) stmt);
    }

    public String visitVarDeclarationStatement(VarDeclarationStatement vds) {
        return "var " + vds.expr.left + " = " + visitAssignmentExpr(vds.expr.right) + ";";
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
        if (expr instanceof UnaryExpr) {
            return visitUnaryExpr((UnaryExpr) expr);
        } else if (expr instanceof PostfixExpr) {
            return visitPostfixExpr((PostfixExpr) expr);
        } else if (expr instanceof AssignmentExpr) {
            return visitAssignmentExpr((AssignmentExpr) expr);
        } else if (expr instanceof FunctionCallExpr) {
            return visitFunctionCallExpr((FunctionCallExpr) expr);
        }
        return expr.toString();
    }

    public String visitASTNode(ASTNode node) {
        if (node instanceof PluginDef) {
            return visitPluginDef((PluginDef) node);
        } else if (node instanceof Block) {
            return visitBlock((Block) node);
        } else if (node instanceof VarDeclarationStatement) {
            return visitVarDeclarationStatement((VarDeclarationStatement) node);
        } else if (node instanceof AssignmentExpr) {
            return visitAssignmentExpr((AssignmentExpr) node);
        } else if (node instanceof FunctionCallExpr) {
            return visitFunctionCallExpr((FunctionCallExpr) node);
        } else if (node instanceof IfStatement) {
            return visitIfStatement((IfStatement) node);
        } else if (node instanceof LoopStatement) {
            return visitLoopStatement((LoopStatement) node);
        } else if (node instanceof ForeachStatement) {
            return visitForeachStatement((ForeachStatement) node);
        } else if (node instanceof UnaryExpr) {
            return visitUnaryExpr((UnaryExpr) node);
        } else if (node instanceof PostfixExpr) {
            return visitPostfixExpr((PostfixExpr) node);
        } else if (node instanceof Expression) {
            return visitExpression((Expression) node);
        }
        return node.toString();
    }

    private String indent(String code) {
        String indent = "        ";
        return indent + code.replace("\n", "\n" + indent);
    }
    
    public Type[] parseMethodArgumentTypes(String methodSignature) throws ClassNotFoundException {
        // Assumes methodSignature is in the format:
        // "public void methodName(java.lang.String, java.lang.Integer)" 
        int start = methodSignature.indexOf('(');
        int end = methodSignature.indexOf(')');
        if (start == -1 || end == -1 || end < start) {
            return new Type[0];
        }
        String args = methodSignature.substring(start + 1, end).trim();
        if (args.isEmpty()) {
            return new Type[0];
        }
        String[] argTypes = args.split(",");
        List<Type> types = new ArrayList<>();
        for (String argType : argTypes) {
            String trimmed = argType.trim();
            types.add(Class.forName(trimmed));
        }
        return types.toArray(new Type[0]);
    }
}
