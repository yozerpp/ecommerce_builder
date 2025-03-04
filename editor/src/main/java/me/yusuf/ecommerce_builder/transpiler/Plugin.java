package me.yusuf.ecommerce_builder.transpiler;

import me.yusuf.ecommerce_builder.shared.PluginRegistry;

public record Plugin(PluginRegistry.PluginMetadata metadata, String source) {
}
