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
import me.yusuf.ecommerce_builder.transpiler.ast.expression.Expression;
import me.yusuf.utils.ReflectionUtils;

public class CodeGeneratorVisitor {

    public CodeGeneratorVisitor() {}

    public Plugin generate(PluginDef pluginDef) {
        String code = visit(pluginDef);
        return new Plugin(new PluginRegistry.PluginMetadata(null, ReflectionUtils.loadMethodFromFullyQualifiedName(pluginDef.hookedMethod)),
                code); //TODO: Parse arge types.
    }

    public String visit(PluginDef pd) {
        return "public class " + pd.name + "Plugin implements Runnable {\n" +
               "    @Override\n" +
               "    public void run() {\n" +
               indent(visit(pd.block)) +
               "\n    }\n" +
               "}\n";
    }

    public String visit(Block block) {
        StringBuilder sb = new StringBuilder();
        for (Statement stmt : block.statements) {
            sb.append(visit(stmt)).append("\n");
        }
        return sb.toString();
    }

    public String visit(VarDeclarationStatement vds) {
        return "var " + vds.expr.left + " = " + visit(vds.expr.right) + ";";
    }

    public String visit(AssignmentExpr asn) {
        return asn.left + " = " + visit(asn.right) + ";";
    }

    public String visit(FunctionCallExpr fce) {
        String args = "";
        if (fce.args != null && fce.args.length > 0) {
            String[] argStr = new String[fce.args.length];
            for (int i = 0; i < fce.args.length; i++) {
                argStr[i] = visit(fce.args[i]);
            }
            args = String.join(", ", argStr);
        }
        return fce.functionName + "(" + args + ");";
    }

    public String visit(IfStatement ifs) {
        StringBuilder sb = new StringBuilder();
        sb.append("if(").append(visit(ifs.condition)).append(") {\n");
        sb.append(indent(visit(ifs.happyPath))).append("\n}");
        if (ifs.sadPath != null) {
            sb.append(" else {\n").append(indent(visit(ifs.sadPath))).append("\n}");
        }
        return sb.toString();
    }

    public String visit(LoopStatement ls) {
        return "while(" + visit(ls.condition) + ") {\n" +
               indent(visit(ls.block)) + "\n}";
    }

    public String visit(ForeachStatement fe) {
        return "for(var " + fe.elementName + " : " + fe.collectionName + ") {\n    // body\n}";
    }

    public String visit(UnaryExpr ue) {
        String op = ue.operator;
        if (op != null) {
            if (op.equals("değil")) {
                op = "!";
            }
            return "(" + op + visit(ue.operand) + ")";
        } else {
            return visit(ue.operand);
        }
    }

    public String visit(PostfixExpr pe) {
        String code = visit(pe.primary);
        if (pe.hasNot) {
            code += "!";
        }
        return code;
    }

    public String visit(Expression expr) {
        if (expr instanceof UnaryExpr) {
            return visit((UnaryExpr) expr);
        } else if (expr instanceof PostfixExpr) {
            return visit((PostfixExpr) expr);
        } else if (expr instanceof AssignmentExpr) {
            return visit((AssignmentExpr) expr);
        } else if (expr instanceof FunctionCallExpr) {
            return visit((FunctionCallExpr) expr);
        }
        return expr.toString();
    }

    public String visit(ASTNode node) {
        if (node instanceof PluginDef) {
            return visit((PluginDef) node);
        } else if (node instanceof Block) {
            return visit((Block) node);
        } else if (node instanceof VarDeclarationStatement) {
            return visit((VarDeclarationStatement) node);
        } else if (node instanceof AssignmentExpr) {
            return visit((AssignmentExpr) node);
        } else if (node instanceof FunctionCallExpr) {
            return visit((FunctionCallExpr) node);
        } else if (node instanceof IfStatement) {
            return visit((IfStatement) node);
        } else if (node instanceof LoopStatement) {
            return visit((LoopStatement) node);
        } else if (node instanceof ForeachStatement) {
            return visit((ForeachStatement) node);
        } else if (node instanceof UnaryExpr) {
            return visit((UnaryExpr) node);
        } else if (node instanceof PostfixExpr) {
            return visit((PostfixExpr) node);
        } else if (node instanceof Expression) {
            return visit((Expression) node);
        }
        return node.toString();
    }

    private String indent(String code) {
        String indent = "        ";
        return indent + code.replace("\n", "\n" + indent);
    }
}
