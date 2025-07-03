package me.yusuf.ecommerce_builder.demo.engine;

import me.yusuf.ecommerce_builder.demo.engine.plugin.PluginRegistry;
import me.yusuf.ecommerce_builder.shared.components.repository.EntitySourceRepository;
import me.yusuf.ecommerce_builder.shared.components.repository.PluginRepository;
import me.yusuf.ecommerce_builder.shared.types.plugin.Plugin;
import me.yusuf.ecommerce_builder.shared.types.plugin.PluginDto;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EngineStartupListener implements ApplicationListener<ApplicationStartedEvent> {
    private final PluginRepository pluginRepository;
    private final EntitySourceRepository entitySourceRepository;
    private final EntityRegistry entityRegistry;
    private final PluginRegistry pluginRegistry;
    public EngineStartupListener(PluginRegistry pluginRegistry,PluginRepository pluginRepository, EntityRegistry entityRegistry, EntitySourceRepository entitySourceRepository) {
        this.pluginRepository = pluginRepository;
        this.pluginRegistry = pluginRegistry;
        this.entitySourceRepository = entitySourceRepository;
        this.entityRegistry = entityRegistry;
    }
    //TODO: Should have loaded these lazily
    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        var entitySources = entitySourceRepository.findAllBy();
        entityRegistry.registerAll(entitySources);
        var plugins = pluginRepository.findAllBy(PluginDto.class);
        pluginRegistry.registerAllPlugins(plugins.stream().map(Plugin::new).toList());
    }
}
