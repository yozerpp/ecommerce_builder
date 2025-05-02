package me.yusuf.ecommerce_builder.editor.tool.transpiler.ast;

import lombok.Getter;
import lombok.Setter;
import me.yusuf.ecommerce_builder.editor.tool.transpiler.ast.expression.Expression;

@Getter
@Setter
public class ForeachStatement implements BlockStatement {
private Expression collection;
private String elementName;
private Block block;
}
