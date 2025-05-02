package me.yusuf.ecommerce_builder.shared.components.repository;

import jakarta.annotation.Nullable;
import me.yusuf.ecommerce_builder.shared.types.plugin.EntitySource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@org.springframework.stereotype.Repository
public interface EntitySourceRepository extends Repository<EntitySource, EntitySource.Id> {
    List<EntitySource> findAllBy();
    @Nullable
    EntitySource findById(EntitySource.Id id);
    List<EntitySource> findById_EditorId(Integer idEditorId, Pageable pageable);
    @Query("SELECT es FROM EntitySource es WHERE es.id.editorId=:editorId AND es.id.entityClass=:entityClass ORDER BY es.id.version DESC LIMIT 1")
    @Nullable
    EntitySource findLatestForEditorAndClass(Integer editorId, Class<?> entityClass);
    @Query("SELECT es.id.version FROM EntitySource es WHERE es.id.editorId=:editorId ORDER BY es.id.version DESC LIMIT 1")
    @Nullable
    Integer findLatestVersionForEditor(Integer editorId);
    @Query(value ="DELETE FROM entity_source es WHERE ((SELECT COUNT(*) FROM entity_source es1 WHERE es1.entity_class = es.entity_class AND es1.editor_id = es.editor_id)) - es.version > 5", nativeQuery = true)
    @Modifying
    void deleteOldVersions(); //untested
    <ES extends EntitySource> ES save(ES entitySource);
}
