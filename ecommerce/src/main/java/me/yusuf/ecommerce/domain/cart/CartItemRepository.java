package me.yusuf.ecommerce.domain.cart;

import org.springframework.data.repository.Repository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Set;

@RepositoryRestResource(exported = false)
public interface CartItemRepository extends Repository<CartItem, CartItem.CartItemId> {
    Set<CartItem> findByCart(Cart cart);
    <CI extends CartItem> CI save(@NonNull CI cartItem);
    void delete(@NonNull CartItem cartItem);
    void deleteById(@NonNull CartItem.CartItemId cartItemId);
    @Nullable CartItem findById(@NonNull CartItem.CartItemId cartItemId);

}
