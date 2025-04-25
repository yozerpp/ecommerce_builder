package me.yusuf.ecommerce_builder.demo.domain.cart;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;

@RepositoryRestResource(exported = false)
public interface CartRepository extends Repository<Cart, Integer> {
    @PreAuthorize("isAuthenticated()")
    @Query("SELECT c from Cart c join Session s on s.cart.id = c.id join User u on u.activeSession = s.id where u.username = ?#{principal.username}")
    @Nullable Cart getCartOfCurrentUser();
    @Query("SELECT c from Cart c join Session s on s.cart.id = c.id where s.id = ?1 and not c.ordered order by c.createdAt DESC LIMIT 1")
    @Nullable Cart getCartOfCurrentSession(@NonNull String sessionId);
    <C extends Cart> C save(@NonNull C cart);
    void delete(@NonNull Cart cart);

}
