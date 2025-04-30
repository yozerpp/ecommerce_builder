package me.yusuf.ecommerce_builder.editor.transpiler.ast;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForeachStatement implements BlockStatement {
private String collectionName;
private String elementName;
private Block block;
}
