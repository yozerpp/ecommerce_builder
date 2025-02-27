package me.yusuf.ecommerce.domain.shipment;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce.domain.ServiceBase;
import me.yusuf.ecommerce.domain.order.Order;
import me.yusuf.ecommerce.domain.order.OrderRepository;
import me.yusuf.ecommerce.domain.seller.SellerRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@PreAuthorize("isAuthenticated()")
@Controller("shipments")
@RequestMapping("/shipments")
public class ShipmentService extends ServiceBase {
    private final SellerRepository sellerRepository;
    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;

    public ShipmentService(ApplicationContext context, EntityManager entityManager, ShipmentRepository shipmentRepository, SellerRepository sellerRepository, OrderRepository orderRepository) {
        super(entityManager);
        this.shipmentRepository = shipmentRepository;
        this.sellerRepository = sellerRepository;
        this.orderRepository = orderRepository;
    }
    @GetMapping("/order/{id}")
    public String getShipmentsOfOrder(@PathVariable int id, Model model){
        var shipments = shipmentRepository.findWithOrdersByOrderId(id, null);
        var user = getUser();
        if(shipments.isEmpty());
        else if(!Objects.equals(shipments.stream().findAny().get().getOrder().getUserId(), user.getId()))
            throw new AccessDeniedException("You aren't allowed to view details about this order");
        else model.addAttribute("shipments", shipments);
        return "shipments";
    }
    @GetMapping
    public String getShipments(Model model){
        Page<Shipment> shipments;
        if (getUser().getSeller()!=null)
            shipments = shipmentRepository.getShipmentsOfCurrentSeller(null);
        else shipments = shipmentRepository.getShipmentsOfCurrentBuyer(null);
        model.addAttribute("shipments", shipments);
        return "shipments";
    }
    @GetMapping("/{id}")
    public String getShipment(Model model, @PathVariable int id){
        var ship = shipmentRepository.findById(id);
        if (ship == null) return "redirect:/error?code=404";
        model.addAttribute("shipment", ship);
        return "shipment";
    }
    @PreAuthorize("hasRole('ROLE_SELLER') and T(me.yusuf.ecommerce.domain.user.User).cast(principal).getId().equals(#sellerId)")
    @PostMapping("/seller/{orderId}/{productId}/{sellerId}")
    public ResponseEntity<String> markShipped(@PathVariable(name = "orderId") int orderId, @PathVariable(name = "productId") int productId, @PathVariable(name = "sellerId") int sellerId){
        var seller = getUser().getSeller();
        var shipment = shipmentRepository.findByOrderIdAndProductIdAndSellerId(orderId,productId,sellerId);
        if(shipment==null) return ResponseEntity.notFound().build();
        if(shipment.getDeliveryStatusSeller()!=null) return ResponseEntity.badRequest().body("Buyer has already canceled or approved this shipment.");
        shipment.setDeliveryStatusSeller(Shipment.DeliveryStatus.SHIPPED);
        return ResponseEntity.created(URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().toUriString() + "/shipment/"  + shipment.getId())).build();
    }
    @PreAuthorize("hasRole('ROLE_SELLER') and T(me.yusuf.ecommerce.domain.user.User).cast(principal).getId().equals(#sellerId)")
    @DeleteMapping("/seller/{orderId}/{productId}/{sellerId}")
    public ResponseEntity<Void> cancelShipment(@PathVariable(name = "orderId") int orderId, @PathVariable(name = "productId") int productId, @PathVariable(name = "sellerId") int sellerId){
        var shipment = shipmentRepository.findByOrderIdAndProductIdAndSellerId(orderId,productId,sellerId);
        shipment.setDeliveryStatusSeller(Shipment.DeliveryStatus.CANCELLED);
        shipment.getOrder().setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(shipment.getOrder());
        shipmentRepository.save(shipment);
        return ResponseEntity.ok().build();
    }
    @PreAuthorize("orderRepository.belongsToCurrentUserById(orderId)")
    @PostMapping("/buyer/{orderId}/{productId}/{sellerId}")
    public ResponseEntity<Void> markDelivered(@PathVariable(name = "orderId") int orderId, @PathVariable(name = "productId") int productId, @PathVariable(name = "sellerId") int sellerId){
        var shipment = shipmentRepository.findByOrderIdAndProductIdAndSellerId(orderId,productId,sellerId);
        shipment.setDeliveryStatusBuyer(Shipment.DeliveryStatus.DELIVERED);
        shipmentRepository.save(shipment);
        return ResponseEntity.ok().build();
    }

}