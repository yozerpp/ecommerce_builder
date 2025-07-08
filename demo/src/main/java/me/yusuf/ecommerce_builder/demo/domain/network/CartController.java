package me.yusuf.ecommerce_builder.demo.domain.network;

import me.yusuf.ecommerce_builder.demo.domain.service.ServiceBase;
import me.yusuf.ecommerce_builder.shared.types.dto.AddToCartResponse;
import me.yusuf.ecommerce_builder.shared.types.entity.Cart;
import me.yusuf.ecommerce_builder.demo.domain.repository.CartItemRepository;
import me.yusuf.ecommerce_builder.demo.domain.service.CartService;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static me.yusuf.ecommerce_builder.demo.domain.network.ControllerBase.basePath;

@RestController
@RequestMapping(basePath+"/cart")
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
    @PostMapping("/item")
    public ResponseEntity<AddToCartResponse> addToCart(@RequestParam Integer productId, @RequestParam Integer sellerId) {
        return ResponseEntity.ok(cartService.addToCart(productId, sellerId)._1());
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart() {
        cartService.clearCart();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/item")
    public ResponseEntity<Void> changeQuantity(
        @RequestParam(required = false, defaultValue = "0") int quantity,
        @RequestParam(required = false, defaultValue = "null") Boolean increment,
        @PathVariable int productId,
        @PathVariable int sellerId
    ) throws ChangeSetPersister.NotFoundException, BadRequestException {
        cartService.changeQuantity(quantity, increment, productId, sellerId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/item")
    public ResponseEntity<Void> removeItem(@RequestParam int productId, @RequestParam int sellerId) {
        try {
            cartService.removeItem(productId, sellerId);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
