package me.yusuf.ecommerce_builder.demo.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@Repository
@RepositoryRestResource(exported = false)
public interface UserRepository extends org.springframework.data.repository.Repository<User,Integer> {
    @PreAuthorize("isAuthenticated()")
    @Query("SELECT u from User u where u.username = ?#{principal?.username}")
    @Nullable User getCurrentUser();
    boolean existsUserByUsername(@Email String username);
    @Nullable User findUserByUsername(@NotNull @Email String userName);
    @PreAuthorize("user.id==null or isAuthenticated() and user.username == principal.username")
    <U extends User> U save(@NotNull U user);
    @PreAuthorize("isAuthenticated() and user.username == principal.username")
    <U extends User> void delete(@NotNull U user);
    @Modifying
    @Query("delete User u where u.username = ?#{principal?.username}")
    @PreAuthorize("isAuthenticated()")
    void deleteCurrentUser();
    @Nullable User findById(@NotNull Integer id);
    @Modifying
    @PreAuthorize("isAuthenticated()")
    @Query(value = "UPDATE User u SET u.password = :#{#newPassword} WHERE u.username = :#{principal?.username} and u.password = :#{principal?.password}")
    void updatePasswordOfCurrentUser(@NotNull @Param("newPassword") String newPassword);
}
