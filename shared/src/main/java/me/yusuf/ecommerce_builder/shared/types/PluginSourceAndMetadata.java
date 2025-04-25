package me.yusuf.ecommerce_builder.shared.types;

public record PluginSourceAndMetadata(Id id, PluginMetadata metadata, String source) {

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        PluginSourceAndMetadata that = (PluginSourceAndMetadata) object;
        return id().equals(that.id());
    }
    @Override
    public int hashCode() {
        return id().hashCode();
    }
    public record Id(
            int editorId,
            String name,
            String hookedMethod
    ){
        @Override
        public boolean equals(Object object) {
            if (object == null || getClass() != object.getClass()) return false;

            Id id = (Id) object;
            return editorId() == id.editorId() && name().equals(id.name()) && hookedMethod().equals(id.hookedMethod());
        }

        @Override
        public int hashCode() {
            int result = editorId();
            result = 31 * result + name().hashCode();
            result = 31 * result + hookedMethod().hashCode();
            return result;
        }
    }
    @Override
    public String toString() {
        return "PluginSourceAndMetadata{" +
                "id=" + id +
                ", metadata=" + metadata +
                ", source='" + source + '\'' +
                '}';
    }
}
