package me.yusuf.ecommerce_builder.editor.transpiler.ast;

import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Block implements Statement {
    private final List<Statement> statements = new ArrayList<>();
}
