package me.yusuf.ecommerce.domain.cart;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce.domain.ServiceBase;
import me.yusuf.ecommerce.domain.product.ProductOffer;
import me.yusuf.ecommerce.domain.user.User;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CartService extends ServiceBase {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    public CartService(EntityManager entityManager, CartRepository cartRepository, CartItemRepository cartItemRepository) {
        super(entityManager);
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }
    public AddToCartResponse addToCart(Integer productId, Integer sellerId) {
        var session =  ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getActiveSessionRef();
        var cart= session.getCart();
        var newItem = new CartItem();
        var ps = new ProductOffer();
        ps.setId(new ProductOffer.ProductOfferId(productId,sellerId));
        newItem.setProductOffer(ps);
        newItem.setCart(cart);
        return new AddToCartResponse(true, null,cartItemRepository.save(newItem).getId());
    }
    public void clearCart() {
        var cart=getCart();
        cartRepository.delete(cart);
        var user = getUser();
        user.getActiveSessionRef().setCart(cartRepository.getCartOfCurrentSession(user.getActiveSession()));
    }
    public void changeQuantity(int quantity, Boolean increment, int productId, int sellerId) throws ChangeSetPersister.NotFoundException, BadRequestException {
        var cart = getCart();
        CartItem cartItem;
        if(increment!=null){
            cartItem = cartItemRepository.findById(new CartItem.CartItemId(new ProductOffer.ProductOfferId(productId,sellerId),cart.getId()));
            if(cartItem==null) throw new ChangeSetPersister.NotFoundException();
            if (increment)
                cartItem.setQuantity(cartItem.getQuantity()+1);
            else cartItem.setQuantity(cartItem.getQuantity()-1);
        } else if(quantity!=0){
            cartItem = new CartItem();
            var ps = new ProductOffer();
            ps.setId(new ProductOffer.ProductOfferId(productId,sellerId));
            cartItem.setProductOffer(ps);
            cartItem.setCart(cart);
            cartItem.setQuantity(quantity);
        } else throw new BadRequestException();
        cartItemRepository.save(cartItem);
    }
    public void removeItem(int productId, int sellerId) {
        this.cartItemRepository.deleteById(new CartItem.CartItemId(new ProductOffer.ProductOfferId(productId,sellerId),getCart().getId()));
    }
    public static Cart getCart(){
        var session =  ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getActiveSessionRef();
        var cart= session.getCart();
        if(cart==null) throw new IllegalStateException("User has no cart");
        else if(cart.isOrdered()) throw new IllegalStateException("Cart is already ordered");
        return cart;
    }
    public record AddToCartResponse(
            boolean success,
            String message,
            CartItem.CartItemId cartItemId
    ) {
    }
}
