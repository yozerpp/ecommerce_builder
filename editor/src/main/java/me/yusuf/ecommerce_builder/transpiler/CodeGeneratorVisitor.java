package me.yusuf.ecommerce_builder.transpiler;

import me.yusuf.ecommerce_builder.shared.PluginRegistry;
import me.yusuf.ecommerce_builder.transpiler.ast.PluginDef;
import me.yusuf.utils.ReflectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class CodeGeneratorVisitor {
    public CodeGeneratorVisitor() {}
    public Plugin generate(PluginDef pluginDef) {
        var name = pluginDef.name;
        var method = ReflectionUtils.loadMethodFromFullyQualifiedName(pluginDef.name);
        var except = pluginDef.hookedException;
    }

}
