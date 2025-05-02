package me.yusuf.ecommerce_builder.demo.domain.repository;

import me.yusuf.ecommerce_builder.shared.types.entity.Cart;
import me.yusuf.ecommerce_builder.shared.types.entity.CartItem;
import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Set;

@org.springframework.stereotype.Repository("cartItemRepository")
public interface CartItemRepository extends Repository<CartItem, CartItem.CartItemId> {
    Set<CartItem> findByCart(Cart cart);
    <CI extends CartItem> CI save(@NonNull CI cartItem);
    void delete(@NonNull CartItem cartItem);
    void deleteById(@NonNull CartItem.CartItemId cartItemId);
    @Nullable CartItem findById(@NonNull CartItem.CartItemId cartItemId);

}
