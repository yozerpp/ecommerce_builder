package me.yusuf.ecommerce_builder.demo.domain.service;

import me.yusuf.ecommerce_builder.shared.types.dto.AddToCartResponse;
import me.yusuf.ecommerce_builder.shared.types.entity.*;
import me.yusuf.ecommerce_builder.demo.domain.repository.CartItemRepository;
import me.yusuf.ecommerce_builder.demo.domain.repository.CartRepository;
import me.yusuf.ecommerce_builder.demo.domain.repository.SessionRepository;
import me.yusuf.ecommerce_builder.demo.utils.exception.ContextedException;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodInfo;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple3;
import me.yusuf.ecommerce_builder.shared.types.exception.ExceptionCause;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple4;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class CartService extends ServiceBase {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final SessionRepository sessionRepository;

    public CartService( CartRepository cartRepository, CartItemRepository cartItemRepository, SessionRepository sessionRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.sessionRepository = sessionRepository;
    }

    @MethodInfo(userFriendlyName = "Sepete Ekle")
    public Tuple4<AddToCartResponse, Cart, ProductOffer, Product> addToCart(Integer productId, Integer sellerId) {
        var cart = getCart();
        var newItem = new CartItem();
        var ps = new ProductOffer();
        ps.setId(new ProductOffer.ProductOfferId(productId, sellerId));
        newItem.setId(new CartItem.CartItemId(ps.getId(), cart.getId()));
        newItem.setProductOffer(ps);
        newItem.setCart(cart);
        return new Tuple4<>(
                new AddToCartResponse(true, null, cartItemRepository.save(newItem).getId()),
                cart,
                ps,
                ps.getProduct());
    }
    
    @MethodInfo(userFriendlyName = "Sepeti Temizle")
    public Tuple3<Void,Cart, User> clearCart() {
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
        return new Tuple3<>(Void.TYPE.cast(null), cart, user);
    }
    
    @MethodInfo(userFriendlyName = "Adet Değiştir")
    public Tuple4<Void,User, Cart, CartItem> changeQuantity(int quantity, Boolean increment, int productId, int sellerId) throws ChangeSetPersister.NotFoundException, BadRequestException {
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
        return new Tuple4<>(Void.TYPE.cast(null), getUser(), cart, cartItem);
    }
    
    @MethodInfo(userFriendlyName = "Sepetten Kaldır")
    public Tuple4<Void,User, Cart, CartItem> removeItem(int productId, int sellerId) throws NotFoundException {
        var item = cartItemRepository.findById(new CartItem.CartItemId(new ProductOffer.ProductOfferId(productId, sellerId), getCart().getId()));
        var context = new Tuple4<>(Void.TYPE.cast(null),getUser(), getCart(), item);
        if (item == null) throw new ContextedException(context,new NotFoundException(ExceptionCause.NOT_FOUND_CART_ITEM,
                "Cart item not found."));
        cartItemRepository.delete(item);
        return context;
    }

}
