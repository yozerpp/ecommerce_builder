package me.yusuf.ecommerce.domain.category;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@org.springframework.stereotype.Repository( "categoryRepository")
public interface CategoryRepository extends Repository<Category, Integer> {

    @Nullable Category findById(Integer id);
    @Query(value = "WITH RECURSIVE category_tree AS (\n" +
            "    SELECT * FROM ecommerce.public.categories WHERE id = :categoryId\n" +
            "    UNION ALL\n" +
            "    SELECT c.* FROM ecommerce.public.categories c\n" +
            "    INNER JOIN category_tree ct ON c.parent_id = ct.id\n" +
            ")\n" +
            "SELECT * FROM category_tree", nativeQuery = true)

    @Nullable Category findWithSubCategoriesById(@NonNull int id);
    @Query("SELECT c FROM Category c where c.parentId=null")
    @NonNull Page<Category> findBaseCategoriesAsTree(@Nullable Pageable pageable);
    @Query(value = "WITH RECURSIVE category_tree AS (\n" +
            "    SELECT * FROM ecommerce.public.categories\n" +
            "    UNION ALL\n" +
            "    SELECT c.* FROM ecommerce.public.categories c\n" +
            "    INNER JOIN category_tree ct ON c.parent_id = ct.id\n" +
            ")\n" +
            "SELECT * FROM category_tree", nativeQuery = true)
    @NonNull Page<Category> findAllAsTree(Pageable pageable);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    <C extends Category> C save(@NonNull C category);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    <C extends Category> void delete(@NonNull C category);

    @NonNull List<Category> findByNameLikeIgnoreCase(@NotNull String name);
}
