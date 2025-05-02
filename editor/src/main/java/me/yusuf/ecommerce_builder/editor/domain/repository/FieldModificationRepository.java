package me.yusuf.ecommerce_builder.editor.domain.repository;

import me.yusuf.ecommerce_builder.editor.domain.entity.FieldModification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import javax.annotation.Nullable;
import java.util.List;

@org.springframework.stereotype.Repository
public interface FieldModificationRepository extends Repository<FieldModification,FieldModification.Id> {
    @Nullable
    FieldModification findById(FieldModification.Id id);
    List<FieldModification> findById_EditorIdAndId_EntityClass(Integer idEditorId, Class<?> idEntityClass, Pageable pageable);
    <FM extends FieldModification> FM save(FM fieldModification);
    void deleteById(FieldModification.Id id);
}
