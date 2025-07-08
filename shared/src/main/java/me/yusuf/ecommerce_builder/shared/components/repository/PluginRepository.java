package me.yusuf.ecommerce_builder.shared.components.repository;

import jakarta.annotation.Nullable;
import me.yusuf.ecommerce_builder.shared.types.plugin.IPlugin;
import me.yusuf.ecommerce_builder.shared.types.plugin.PluginDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@org.springframework.stereotype.Repository
public interface PluginRepository extends Repository<PluginDto, IPlugin.Id> {
    <T extends IPlugin> List<T> findAllBy(Class<T> projection);
    @Nullable
    <T extends IPlugin> T findById(IPlugin.Id id, Class<T> projection);
    <T extends IPlugin>List<T> findById_EditorId(int idEditorId, Pageable pageable, Class<T> projection);
    <PM extends IPlugin> PM save(PM pluginModification);
    void deleteById(IPlugin.Id id);
    @Query("SELECT p.id.version FROM Plugin p WHERE p.id.editorId = ?1 AND p.id.name = ?2 AND p.id.hookedMethod=?3 ORDER BY p.id.version DESC LIMIT 1")
    Integer getLastVersion(int editorId, String name, String hookedMethodName);
    @Query(value = "DELETE FROM public.plugin p WHERE p.version!=((SELECT MAX(p1.version) FROM public.plugin p1 WHERE p1.editor_id=p.editor_id AND p1.hooked_method=p.hooked_method AND p1.name=p.name))",nativeQuery = true)
    @Modifying
    @Transactional
    void deleteOldVersions();
}
