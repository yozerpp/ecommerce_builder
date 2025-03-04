package me.yusuf.ecommerce.controller;

import me.yusuf.ecommerce.domain.ServiceBase;
import me.yusuf.ecommerce.domain.cart.Cart;
import me.yusuf.ecommerce.domain.cart.CartItemRepository;
import me.yusuf.ecommerce.domain.cart.CartService;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.hibernate.Hibernate;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemRepository cartItemRepository;

    public CartController(CartService cartService, CartItemRepository cartItemRepository) {
        this.cartService = cartService;
        this.cartItemRepository = cartItemRepository;
    }

    @GetMapping
    public Cart getCart() {
        var c =ServiceBase.getSession().getCart();
        c.setCartItems(cartItemRepository.findByCart(c));
        return c;
    }
    @PostMapping("/{productId}/{sellerId}")
    public ResponseEntity<CartService.AddToCartResponse> addToCart(@PathVariable Integer productId, @PathVariable Integer sellerId) {
        return ResponseEntity.ok(cartService.addToCart(productId, sellerId));
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok().build();
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
        try {
            cartService.removeItem(productId, sellerId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
