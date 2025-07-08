package me.yusuf.ecommerce_builder.editor.tool.transpiler;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.*;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression.*;
import me.yusuf.ecommerce_builder.shared.types.plugin.EntitySource;
import me.yusuf.ecommerce_builder.shared.types.plugin.IPlugin;
import me.yusuf.ecommerce_builder.shared.types.plugin.PluginDto;
import me.yusuf.utils.ReflectionUtils;
import me.yusuf.utils.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("singleton")
@Component
public class CodeGeneratorVisitor implements ASTModifierVisitor {
    private final Class<?>[] entityClasses;
    public CodeGeneratorVisitor(Class<?>[] entityClasses) {
        this.entityClasses = entityClasses;
    }
    public PluginDto generate(PluginDef pluginDef, Type[] argTypes, int editorId, int version, Integer entityVersion) {
        String code = visitPluginDef(pluginDef, argTypes,editorId, version, entityVersion);
        return new PluginDto(new IPlugin.Id(
                editorId,
                pluginDef.getName(),
                pluginDef.getHookedMethod(),
                version
        ),new IPlugin.PluginMetadata(argTypes),
                new PluginDto.PluginSource(null,code,null));
    }
    public String visitPluginDef(PluginDef pd, Type[] argTypes, int editorId, int pluginVersion, int entityVersion) {
        return "public class " + pd.getName() + "Plugin_" +editorId+ "_v" + pluginVersion + " {\n" +
               "    public static void run(" + Arrays.stream(argTypes).map(t->{
                   var splt = t.getTypeName().split("\\.");
                  return EntitySource.getClassName(splt[splt.length-1],entityVersion,editorId) + ' ' + StringUtils.firstLetterToLowerCase(splt[splt.length-1]);
        }).collect(Collectors.joining(","))+") {\n" +
                indent(visitBlock(pd.getBlock()),2) +
                "    }\n"+
               "}\n";
    }
    @Override
    public String visitBlock(Block block) {
        StringBuilder sb = new StringBuilder();
        for (Statement stmt : block.getStatements()) {
            sb.append(visitStatement(stmt)).append("\n");
        }
        return sb.toString();
    }
    @Override
    public String visitVarDeclarationStatement(VarDeclarationStatement vds) {
        return "var " + vds.getVarName()+ " = " + visitExpression(vds.getValue()) + ";";
    }
    @Override
    public String visitAssignmentExpr(AssignmentExpr asn) {
        var id = asn.getLeft();
        if (id.memberAccess == null) return  id.identifier + " = " + visitExpression(asn.getRight()) + ";";
        else{
            String s = visitIdentifier(id,true,true).replaceAll("\\)$","") +  visitExpression(asn.getRight()) + ");";
            return s;
        }
    }
    @Override
    public String visitFunctionCallExpr(FunctionCallExpr fce) {
        String args = "";
        if (fce.getArgs() != null && fce.getArgs().length > 0) {
            String[] argStr = new String[fce.getArgs().length];
            for (int i = 0; i < fce.getArgs().length; i++) {
                argStr[i] = visitExpression(fce.getArgs()[i]);
            }
            args = String.join(", ", argStr);
        }
        return fce.getFunctionName() + "(" + args + ")";
    }
    @Override
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
    @Override
    public String visitLoopStatement(LoopStatement ls) {
        return "while(" + visitExpression(ls.getCondition()) + ") {\n" +
               indent(visitBlock(ls.getBlock())) + "}";
    }
    @Override
    public String visitForeachStatement(ForeachStatement fe) {
        return "for(var " + fe.getElementName()+ " : " + visitExpression(fe.getCollection()) + ") {\n" +
                indent(visitBlock(fe.getBlock())) + "}";
    }
    @Override
    public String visitUnaryExpr(UnaryExpr ue) {
        String op = ue.operator();
        if (op != null) {
            if (op.equals("değil")) {
                op = "!";
            }
            return op + visitExpression(ue.operand());
        } else {
            return visitExpression(ue.operand());
        }
    }
    @Override
    public String visitPostfixExpr(PostfixExpr pe) {
        String code = visitExpression(pe.primary());
        if (pe.hasNot()) {
            code = "!" + code;
        }
        return code;
    }
    @Override
    public String visitLogicalAndExpr(LogicalAndExpr lae) {
        StringBuilder result = new StringBuilder(visitEqualityExpr(lae.first()));
        if (lae.rest() != null) {
            for (EqualityExpr eq : lae.rest()) {
                result.append(" && ").append(visitEqualityExpr(eq));
            }
        }
        return result.toString();
    }
    @Override
    public String visitEqualityExpr(EqualityExpr ee) {
        StringBuilder result = new StringBuilder(visitComparisonExpr(ee.first()));
        if (ee.ops() != null) {
            for (EqualityExpr.Op op : ee.ops()) {
                result.append(" ").append(op.operator()).append(" ").append(visitComparisonExpr(op.expr()));
            }
        }
        return result.toString();
    }
    @Override
    public String visitComparisonExpr(ComparisonExpr ce) {
        StringBuilder result = new StringBuilder(visitAdditiveExpr(ce.first()));
        if (ce.ops() != null) {
            for (ComparisonExpr.Op op : ce.ops()) {
                result.append(" ").append(op.operator()).append(" ").append(visitAdditiveExpr(op.expr()));
            }
        }
        return result.toString();
    }
    @Override
    public String visitAdditiveExpr(AdditiveExpr ae) {
        StringBuilder result = new StringBuilder(visitMultiplicativeExpr(ae.first()));
        if (ae.ops() != null) {
            for (AdditiveExpr.Op op : ae.ops()) {
                result.append(" ").append(op.operator()).append(" ").append(visitMultiplicativeExpr(op.expr()));
            }
        }
        return result.toString();
    }
    @Override
    public String visitMultiplicativeExpr(MultiplicativeExpr me) {
        StringBuilder result = new StringBuilder(visitUnaryExpr(me.first()));
        if (me.ops() != null) {
            for (MultiplicativeExpr.Op op : me.ops()) {
                result.append(" ").append(op.operator()).append(" ").append(visitUnaryExpr(op.expr()));
            }
        }
        return result.toString();
    }
    @Override
    public String visitExpr(Expr e) {
        StringBuilder result = new StringBuilder(visitLogicalAndExpr(e.first()));
        if (e.rest() != null) {
            for (LogicalAndExpr lae : e.rest()) {
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
    @Override
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
    @Override
    public String visitExpression(Expression expr) {
        return (String) ASTModifierVisitor.super.visitExpression(expr);
    }

    @Override
    public String visitPrimary(Primary p) {
        return switch (p){
            case Primary.Identifier id -> visitIdentifier(id, true,false);
            case Primary.StringLiteral literal -> visitStringLiteral(literal);
            case Primary.Number n-> visitNumber(n);
            case null -> throw new IllegalArgumentException("Primary cannot be null");
            default -> throw new IllegalStateException("Unexpected value: " + p);
        };
    }

    public String visitIdentifier(Primary.Identifier id, boolean root,boolean isSetter) {
            return id.identifier + (root ? "" : "()") + (id.memberAccess != null ? (isSetter && id.memberAccess.memberAccess == null ?".set":".get") + StringUtils.firstLetterToUpperCase(visitIdentifier(id.memberAccess, false,isSetter)):"");
    }

    @Override
    public String visitStringLiteral(Primary.StringLiteral literal) {
        return literal.string;
    }

    @Override
    public String visitNumber(Primary.Number number) {
        return number.number.toString();
    }

    @Override
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
