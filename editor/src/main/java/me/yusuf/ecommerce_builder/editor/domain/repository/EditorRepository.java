package me.yusuf.ecommerce_builder.editor.domain.repository;

import me.yusuf.ecommerce_builder.editor.domain.entity.Editor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import javax.annotation.Nullable;
import java.util.Set;

public interface EditorRepository extends Repository<Editor, Integer> {
    @Nullable
    Editor findById(Integer id);
    Editor findByUsernameAndPassword(String username, String password);
    <E> E save(E entity);
    void deleteById(Integer id);
}