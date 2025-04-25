package me.yusuf.ecommerce_builder.editor.transpiler.ast;

public class PluginDef implements ASTNode{
    public String hookedMethod;
    public String hookedException;
    public Block block;
    public String name;
}
