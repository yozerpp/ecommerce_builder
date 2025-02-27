package me.yusuf.ecommerce.domain.role;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.repository.Repository;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;

public interface RoleRepository extends Repository<Role, String> {
    @Nullable Role findByAuthority(@NotNull String authority);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void save(@NotNull Role role);
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    void delete(@NotNull Role role);
}
