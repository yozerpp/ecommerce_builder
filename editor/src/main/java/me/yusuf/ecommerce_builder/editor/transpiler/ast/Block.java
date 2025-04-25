package me.yusuf.ecommerce_builder.editor.transpiler.ast;

import java.util.ArrayList;
import java.util.List;

public class Block implements Statement{
public final List<Statement> statements = new ArrayList<>();
}
