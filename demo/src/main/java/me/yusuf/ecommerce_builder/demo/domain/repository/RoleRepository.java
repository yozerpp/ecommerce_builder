package me.yusuf.ecommerce_builder.demo.domain.repository;

import jakarta.validation.constraints.NotNull;
import me.yusuf.ecommerce_builder.shared.types.entity.Role;
import org.springframework.data.repository.Repository;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;

@org.springframework.stereotype.Repository("roleRepository")
public interface RoleRepository extends Repository<Role, String> {
    @Nullable Role findByAuthority(@NotNull String authority);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void save(@NotNull Role role);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(@NotNull Role role);
}
