package me.yusuf.ecommerce_builder.editor.transpiler.ast;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PluginDef implements ASTNode {
    private String hookedMethod;
    private String hookedException;
    private Block block;
    private String name;
}
