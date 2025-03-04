package me.yusuf.ecommerce_builder.transpiler;

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
import me.yusuf.utils.ReflectionUtils;

public class CodeGeneratorVisitor {
    public CodeGeneratorVisitor() {}

    public Plugin generate(PluginDef pluginDef) {
        // Generate a Java source code string from the AST
        String code = generateCode(pluginDef);
        // (Assuming Plugin has a constructor Plugin(String name, String javaSource))
        return new Plugin(new PluginRegistry.PluginMetadata(null, ReflectionUtils.loadMethodFromFullyQualifiedName(pluginDef.hookedMethod)),
                code);
    }

    private String generateCode(ASTNode node) {
        if (node instanceof PluginDef pd) {
            return "public class " + pd.name + "Plugin implements Runnable{\n" +
                    "@Override\n    public void run() {\n" +
                    generateCode(pd.block) +
                    "\n    }\n" +
                    "}\n";
        } else if (node instanceof Block block) {
            StringBuilder sb = new StringBuilder();
            for (Statement stmt : block.statements) {
                sb.append(generateCode(stmt)).append("\n");
            }
            return sb.toString();
        } else if (node instanceof VarDeclarationStatement vds) {
            // Generate a variable declaration from its assignment expression
            return "var " + vds.expr.left + " = " + generateCode(vds.expr.right) + ";";
        } else if (node instanceof AssignmentExpr asn) {
            return asn.left + " = " + generateCode(asn.right) + ";";
        } else if (node instanceof FunctionCallExpr fce) {
            String args = "";
            if (fce.args != null && fce.args.length > 0) {
                String[] argStr = new String[fce.args.length];
                for (int i = 0; i < fce.args.length; i++) {
                    argStr[i] = generateCode(fce.args[i]);
                }
                args = String.join(", ", argStr);
            }
            return fce.functionName + "(" + args + ");";
        } else if (node instanceof IfStatement ifs) {
            StringBuilder sb = new StringBuilder();
            sb.append("if(").append(generateCode(ifs.condition)).append(") {\n");
            sb.append(generateCode(ifs.happyPath)).append("\n}");
            if (ifs.sadPath != null) {
                sb.append(" else {\n").append(generateCode(ifs.sadPath)).append("\n}");
            }
            return sb.toString();
        } else if (node instanceof LoopStatement ls) {
            return "while(" + generateCode(ls.condition) + ") {\n" +
                   generateCode(ls.block) + "\n}";
        } else if (node instanceof ForeachStatement fe) {
            return "for(var " + fe.elementName + " : " + fe.collectionName + ") {\n    // body\n}";
        } else if (node instanceof UnaryExpr ue) {
            String op = ue.operator;
            if (op != null) {
                if (op.equals("değil")) op = "!";
                return "(" + op + generateCode(ue.operand) + ")";
            } else {
                return generateCode(ue.operand);
            }
        } else if (node instanceof PostfixExpr pe) {
            String code = generateCode(pe.primary);
            if (pe.hasNot) {
                code += "!";
            }
            return code;
        }
        // Fallback: use the node’s toString() value (or add more cases as needed)
        return node.toString();
    }
}
