package me.yusuf.ecommerce.controller;

import me.yusuf.ecommerce.domain.cart.CartService;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{productId}/{sellerId}")
    public ResponseEntity<CartService.AddToCartResponse> addToCart(@PathVariable Integer productId, @PathVariable Integer sellerId) {
        return ResponseEntity.ok(cartService.addToCart(productId, sellerId));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{productId}/{sellerId}")
    public ResponseEntity<Void> changeQuantity(
        @RequestParam(required = false, defaultValue = "0") int quantity,
        @RequestParam(required = false, defaultValue = "null") Boolean increment,
        @PathVariable int productId,
        @PathVariable int sellerId
    ) throws ChangeSetPersister.NotFoundException, BadRequestException {
        cartService.changeQuantity(quantity, increment, productId, sellerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{productId}/{sellerId}")
    public ResponseEntity<Void> removeItem(@PathVariable int productId, @PathVariable int sellerId) {
        cartService.removeItem(productId, sellerId);
        return ResponseEntity.noContent().build();
    }
}
