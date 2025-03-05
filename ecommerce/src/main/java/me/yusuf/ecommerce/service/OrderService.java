package me.yusuf.ecommerce.service;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce.domain.ServiceBase;
import me.yusuf.ecommerce.domain.order.Order;
import me.yusuf.ecommerce.domain.order.OrderRepository;
import me.yusuf.ecommerce.domain.payment.Payment;
import me.yusuf.ecommerce.domain.shipment.Shipment;
import me.yusuf.ecommerce.domain.shipment.ShipmentRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Service
public class OrderService extends ServiceBase
{
    ShipmentRepository shipmentRepository;
    OrderRepository orderRepository;
    public OrderService(EntityManager entityManager, OrderRepository orderRepository, ShipmentRepository shipmentRepository) {
        super(entityManager);
        this.orderRepository = orderRepository;
        this.shipmentRepository = shipmentRepository;
    }
    @PreAuthorize("isAuthenticated()")
    public List<Order> getOrders(Pageable pageable) {
        return orderRepository.getOrdersOfCurrentUser(pageable);
    }
    @PreAuthorize("isAuthenticated()")
    public @Nullable Order getOrder(int id) {
        return orderRepository.findById(id);
    }

    @PreAuthorize("isAuthenticated()")
    public void updateOrder(@RequestParam int id,@RequestParam Order newOrder) {
        newOrder.setId(id);
        orderRepository.save(newOrder);
    }
    @PreAuthorize("!isAnonymous()")
    public Integer createOrder() {
        var user = getUser();
        var cart=  user.getActiveSessionRef().getCart();
        var order = new Order();
        order.setUser(user);
        order.setCart(cart);
        var payment = new Payment(); //dummy object, have to replace with a real thing.
        payment.setOrder(order);
        payment.setSuccessful(true);
        order.setPayment(payment);
        var shipments = cart.getCartItems().stream().map(i->{
            Shipment ship = new Shipment();
            var o = i.getProductOffer();
            ship.setProduct(o.getProduct());
            ship.setOrder(order);
            ship.setSeller(o.getSeller());
            ship.setCartItemId(i.getId());
            ship.setCartItem(i);
            ship.setDeliveryAddress(user.getAddress());
            order.getShipments().add(ship);
            return ship;
        }).toArray(Shipment[]::new);
        shipmentRepository.saveAll(List.of(shipments));
        return orderRepository.save(order).getId();
    }
    @PreAuthorize("isAuthenticated()")
    public void cancelOrder(@PathVariable int id){
        orderRepository.cancelById(id);
    }
}
