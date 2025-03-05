package me.yusuf.ecommerce.service;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce.domain.ServiceBase;
import me.yusuf.ecommerce.domain.cart.Cart;
import me.yusuf.ecommerce.domain.cart.CartItem;
import me.yusuf.ecommerce.domain.cart.CartItemRepository;
import me.yusuf.ecommerce.domain.cart.CartRepository;
import me.yusuf.ecommerce.domain.product.ProductOffer;
import me.yusuf.ecommerce.domain.session.SessionRepository;
import me.yusuf.ecommerce.domain.user.User;
import me.yusuf.ecommerce.utils.exception.ContextedException;
import me.yusuf.ecommerce_builder.shared.types.Tuple2;
import me.yusuf.ecommerce_builder.shared.types.Tuple3;
import me.yusuf.ecommerce_builder.shared.types.exception.ExceptionCause;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import me.yusuf.ecommerce_builder.shared.MethodMetadata;

@Service
public class CartService extends ServiceBase {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final SessionRepository sessionRepository;

    public CartService(EntityManager entityManager, CartRepository cartRepository, CartItemRepository cartItemRepository, SessionRepository sessionRepository) {
        super(entityManager);
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.sessionRepository = sessionRepository;
    }
    
    @MethodMetadata(name = "Ekle")
    public AddToCartResponse addToCart(Integer productId, Integer sellerId) {
        var session = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getActiveSessionRef();
        var cart = session.getCart();
        var newItem = new CartItem();
        var ps = new ProductOffer();
        ps.setId(new ProductOffer.ProductOfferId(productId, sellerId));
        newItem.setProductOffer(ps);
        newItem.setCart(cart);
        return new AddToCartResponse(true, null, cartItemRepository.save(newItem).getId());
    }
    
    @MethodMetadata(name = "Temizle")
    public Tuple2<Cart, User> clearCart() {
        var cart = getCart();
        cartRepository.delete(cart);
        var user = getUser();
        if (user != null)
            user.getActiveSessionRef().setCart(cartRepository.getCartOfCurrentSession(user.getActiveSession()));
        var newCart = cartRepository.save(new Cart()); //TODO this should be managed by triggers
        var s = getSession();
        s.setCartId(newCart.getId());
        s.setCart(newCart);
        sessionRepository.save(s);
        return new Tuple2<>(cart, user);
    }
    
    @MethodMetadata(name = "Değiştir")
    public Tuple3<User, Cart, CartItem> changeQuantity(int quantity, Boolean increment, int productId, int sellerId) throws ChangeSetPersister.NotFoundException, BadRequestException {
        var cart = getCart();
        CartItem cartItem;
        if (increment != null) {
            cartItem = cartItemRepository.findById(new CartItem.CartItemId(new ProductOffer.ProductOfferId(productId, sellerId), cart.getId()));
            if (cartItem == null) throw new ChangeSetPersister.NotFoundException();
            if (increment)
                cartItem.setQuantity(cartItem.getQuantity() + 1);
            else 
                cartItem.setQuantity(cartItem.getQuantity() - 1);
        } else if (quantity != 0) {
            cartItem = new CartItem();
            var ps = new ProductOffer();
            ps.setId(new ProductOffer.ProductOfferId(productId, sellerId));
            cartItem.setProductOffer(ps);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
        } else throw new BadRequestException();
        cartItemRepository.save(cartItem);
        return new Tuple3<>(getUser(), cart, cartItem);
    }
    
    @MethodMetadata(name = "Kaldır")
    public Tuple3<User, Cart, CartItem> removeItem(int productId, int sellerId) throws NotFoundException {
        var item = cartItemRepository.findById(new CartItem.CartItemId(new ProductOffer.ProductOfferId(productId, sellerId), getCart().getId()));
        var context = new Tuple3<>(getUser(), getCart(), item);
        if (item == null) throw new ContextedException(context, new NotFoundException(ExceptionCause.NOT_FOUND_CART_ITEM,
                "Cart item not found."));
        cartItemRepository.delete(item);
        return context;
    }
    
    @MethodMetadata(name = "Getir")
    public static Cart getCart(){
        var session = getSession();
        var cart = session.getCart();
        if (cart == null) throw new IllegalStateException("User has no cart");
        else if (cart.isOrdered()) throw new IllegalStateException("Cart is already ordered");
        return cart;
    }
    
    public record AddToCartResponse(
            boolean success,
            String message,
            CartItem.CartItemId cartItemId
    ) {
    }
}
