package me.yusuf.ecommerce_builder.shared.types.plugin;

public record Plugin (
    Id id,
    ClassFileObject classFile,
    PluginMetadata metadata) implements IPlugin{
    @Override
    public Id getId() {
        return id;
    }
    @Override
    public PluginMetadata getMetadata() {
        return metadata;
    }
    private static final String pluginPackagePrefix = "me.yusuf.ecommerce_builder.demo.engine.plugin.";
    public Plugin(PluginDto dto){
        //TODO: app logic in record..
        this(dto.id,new ClassFileObject( pluginPackagePrefix +dto.id.name + "Plugin_" + dto.id.editorId + "_v" + dto.id.version, dto.source.byteEncoded),dto.metadata);
    }
    public static PluginDto toDto(Plugin plugin){
        return new PluginDto(
            plugin.getId(),
            plugin.getMetadata(),
            new PluginDto.PluginSource(null,null, plugin.classFile.getClassBytes())
        );
    }
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        Plugin that = (Plugin) object;
        return id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return id.hashCode() *10;
    }
}
