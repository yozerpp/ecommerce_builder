package me.yusuf.ecommerce.utils;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

public class TurkceTranspiler extends TurkceBaseVisitor<String> {
    private int indentLevel = 0;

    private String indent() {
        return "    ".repeat(indentLevel);
    }

    @Override
    public String visitProg(TurkceParser.ProgContext ctx) {
        StringBuilder sb = new StringBuilder();
        sb.append("import java.util.*;\n\n");
        sb.append("public class Main {\n");
        indentLevel++;

        // Process functions first
        for (var child : ctx.children) {
            if (child instanceof TurkceParser.FunctionDefContext) {
                sb.append(visit(child));
            }
        }

        // Generate main method
        sb.append(indent()).append("public static void main(String[] args) {\n");
        indentLevel++;
        for (var child : ctx.children) {
            if (child instanceof TurkceParser.StatementContext) {
                sb.append(visit(child));
            }
        }
        indentLevel--;
        sb.append(indent()).append("}\n");

        indentLevel--;
        sb.append("}\n");
        return sb.toString();
    }

    @Override
    public String visitFunctionDef(TurkceParser.FunctionDefContext ctx) {
        String funcName = ctx.IDENTIFIER().getText();
        String params = ctx.paramList() != null ?
                String.join(", ", ctx.paramList().IDENTIFIER().stream()
                        .map(id -> "Object " + id.getText())
                        .toArray(String[]::new)) : "";

        String block = visit(ctx.block());
        return indent() + String.format(
                "static Object %s(%s) %s\n",
                funcName, params, block
        );
    }

    @Override
    public String visitForeachStatement(TurkceParser.ForeachStatementContext ctx) {
        String collection = ctx.IDENTIFIER(0).getText();
        String item = ctx.IDENTIFIER(1).getText();
        String block = visit(ctx.block());

        return indent() + String.format(
                "for (Object %s : (Iterable<?>)%s) %s\n",
                item, collection, block
        );
    }

    @Override
    public String visitFunctionCall(TurkceParser.FunctionCallContext ctx) {
        String functionName = ctx.IDENTIFIER().getText();
        String args = ctx.expr().stream()
                .map(expr -> visit(expr))
                .collect(java.util.stream.Collectors.joining(", "));

        return functionName + "(" + args + ")";
    }

    @Override
    public String visitIfStatement(TurkceParser.IfStatementContext ctx) {
        String condition = visit(ctx.condition());
        String ifBlock = visit(ctx.block(0));
        String elseBlock = ctx.block().size() > 1 ? " else " + visit(ctx.block(1)) : "";

        return indent() + String.format(
                "if (%s) %s%s\n",
                condition, ifBlock, elseBlock
        );
    }

    @Override
    public String visitLoopStatement(TurkceParser.LoopStatementContext ctx) {
        String condition = visit(ctx.condition());
        String block = visit(ctx.block());
        return indent() + String.format("while (%s) %s\n", condition, block);
    }

    @Override
    public String visitVarDeclaration(TurkceParser.VarDeclarationContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        String value = visit(ctx.expr());
        return indent() + String.format("Object %s = %s;\n", varName, value);
    }

    @Override
    public String visitYazdir(TurkceParser.YazdirContext ctx) {
        return indent() + "System.out.println(" + visit(ctx.expr()) + ");\n";
    }

    @Override
    public String visitBlock(TurkceParser.BlockContext ctx) {
        indentLevel++;
        StringBuilder sb = new StringBuilder("{\n");
        for (var stmt : ctx.statement()) {
            sb.append(visit(stmt));
        }
        indentLevel--;
        sb.append(indent()).append("}");
        return sb.toString();
    }

    @Override
    public String visitExpr(TurkceParser.ExprContext ctx) {
        return visitChildren(ctx);
    }

    @Override
    public String visitAssignment(TurkceParser.AssignmentContext ctx) {
        String varName = ctx.IDENTIFIER().getText();
        String value = visit(ctx.expr());
        return indent() + String.format("%s = %s;\n", varName, value);
    }

    public static void main(String[] args) throws Exception {
        CharStream input = CharStreams.fromFileName("input.turkce");
        TurkceLexer lexer = new TurkceLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        TurkceParser parser = new TurkceParser(tokens);
        ParseTree tree = parser.prog();

        TurkceTranspiler transpiler = new TurkceTranspiler();
        String javaCode = transpiler.visit(tree);
        System.out.println(javaCode);
    }
}
