package me.yusuf.ecommerce.domain.order;

import me.yusuf.ecommerce.domain.cart.CartItem;
import me.yusuf.ecommerce.domain.payment.Payment;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;

import java.math.BigDecimal;

public class OrderListener extends AbstractRepositoryEventListener<Order> {
    OrderRepository repository;
    public OrderListener(OrderRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void onAfterLinkSave(Order parent, Object linked) {
        if (linked instanceof Payment payment) {
            if (payment.getSuccessful())
                parent.setStatus( Order.OrderStatus.AWAITING_SHIPPING);
            else if(payment.getLastPaymentAttempt() - System.currentTimeMillis() > 1000*60*10) {
                parent.setStatus( Order.OrderStatus.CANCELLED);
            }
        }
    }
    @Override
    protected void onBeforeSave(Order entity) {
        entity.setTotal(entity.getCart().getCartItems().stream().map(CartItem::getTotalPrice).reduce((double) 0, Double::sum));
    }
}
