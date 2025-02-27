package me.yusuf.ecommerce.domain.cart;

import me.yusuf.ecommerce.domain.payment.Payment;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.data.rest.core.event.RepositoryEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CartEventListener extends AbstractRepositoryEventListener<Cart> {
    private final CartRepository cartRepository;
    public CartEventListener(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }
    @Override
    protected void onAfterDelete(Cart entity) {
       String sid= entity.getSessionId();
       Cart c = new Cart();
       c.setSessionId(sid);
       cartRepository.save(c);
    }
    @Override
    protected void onBeforeSave(Cart entity) {
        var total = entity.getCartItems().stream().map(CartItem::getTotalPrice).reduce(0D,Double::sum);
        entity.setTotal(total);
    }
    @Override
    protected void onAfterLinkSave(Cart parent, Object linked) {
        if(linked instanceof CartItem item) {
            parent.setTotal(parent.getTotal() + item.getTotalPrice());
        } else if(linked instanceof Payment payment) {
            parent.setOrdered(true);
        }
        cartRepository.save(parent);
    }
}
