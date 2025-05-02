package me.yusuf.ecommerce_builder.editor.tool.transpiler.ast;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PluginDef implements BlockStatement {
    private String entityName;
    private String hookedMethod;
    private String hookedException;
    private Block block;
    private String name;
}
