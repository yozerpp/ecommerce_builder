package me.yusuf.ecommerce_builder.demo.domain.tag;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface TagRepository extends Repository<Tag, String> {
    @Nullable Tag findByName(@NonNull String name);
    @Nullable Tag findWithProductsByName(@NotNull @Size(max = 32) String name);
    @EntityGraph(attributePaths = {"products"})
    @Nullable Page<Tag> findAllWithProductsByNameLikeIgnoreCase(@NotNull @Size(max = 255) String name, Pageable pageable);
    @PreAuthorize("hasAnyRole('ROLE_STAFF','ROLE_ADMIN')")
    Tag save(@NotNull Tag tag);
}
