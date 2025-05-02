package me.yusuf.ecommerce_builder.shared.types.dto;

import me.yusuf.ecommerce_builder.shared.types.entity.CartItem;

public record AddToCartResponse(
        boolean success,
        String message,
        CartItem.CartItemId cartItemId
) {
}
