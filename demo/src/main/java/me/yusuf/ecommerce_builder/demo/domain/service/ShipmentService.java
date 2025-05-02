package me.yusuf.ecommerce_builder.demo.domain.service;

import me.yusuf.ecommerce_builder.shared.types.entity.Order;
import me.yusuf.ecommerce_builder.demo.domain.repository.OrderRepository;
import me.yusuf.ecommerce_builder.demo.domain.repository.SellerRepository;
import me.yusuf.ecommerce_builder.shared.types.entity.Shipment;
import me.yusuf.ecommerce_builder.demo.domain.repository.ShipmentRepository;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodInfo;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@PreAuthorize("isAuthenticated()")
@Service
public class ShipmentService extends ServiceBase {
    private final SellerRepository sellerRepository;
    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;

    public ShipmentService(ShipmentRepository shipmentRepository, SellerRepository sellerRepository, OrderRepository orderRepository) {
        this.shipmentRepository = shipmentRepository;
        this.sellerRepository = sellerRepository;
        this.orderRepository = orderRepository;
    }
    
    @MethodInfo(userFriendlyName = "Sipariş Gönderi")
    public Page<Shipment> getShipmentsOfOrder(int orderId) {
        return shipmentRepository.findWithOrdersByOrderId(orderId, null);
    }
    
    @MethodInfo(userFriendlyName = "Gönderi Liste")
    public Page<Shipment> getShipments() {
        if (getUser().getSeller() != null)
            return shipmentRepository.getShipmentsOfCurrentSeller(null);
        else 
            return shipmentRepository.getShipmentsOfCurrentBuyer(null);
    }
    
    @MethodInfo(userFriendlyName = "Gönderi Getir")
    public Shipment getShipment(int id) {
        return shipmentRepository.findById(id);
    }
    
    @MethodInfo(userFriendlyName = "Kargo Gönder")
    @PreAuthorize("hasRole('ROLE_SELLER') and T(me.yusuf.ecommerce_builder.shared.types.entity.User).cast(principal).getId().equals(#sellerId)")
    public Shipment markShipped(int orderId, int productId, int sellerId) {
        var shipment = shipmentRepository.findByOrderIdAndProductIdAndSellerId(orderId, productId, sellerId);
        if (shipment != null && shipment.getDeliveryStatusSeller() == null) {
            shipment.setDeliveryStatusSeller(Shipment.DeliveryStatus.SHIPPED);
            return shipmentRepository.save(shipment);
        }
        return null;
    }
    
    @MethodInfo(userFriendlyName = "Kargo İptal")
    @PreAuthorize("hasRole('ROLE_SELLER') and T(me.yusuf.ecommerce_builder.shared.types.entity.User).cast(principal).getId().equals(#sellerId)")
    public boolean cancelShipment(int orderId, int productId, int sellerId) {
        var shipment = shipmentRepository.findByOrderIdAndProductIdAndSellerId(orderId, productId, sellerId);
        if (shipment != null) {
            shipment.setDeliveryStatusSeller(Shipment.DeliveryStatus.CANCELLED);
            shipment.getOrder().setStatus(Order.OrderStatus.CANCELLED);
            orderRepository.save(shipment.getOrder());
            shipmentRepository.save(shipment);
            return true;
        }
        return false;
    }
    
    @MethodInfo(userFriendlyName = "Teslim Et")
    @PreAuthorize("orderRepository.belongsToCurrentUserById(#orderId)")
    public boolean markDelivered(int orderId, int productId, int sellerId) {
        var shipment = shipmentRepository.findByOrderIdAndProductIdAndSellerId(orderId, productId, sellerId);
        if (shipment != null) {
            shipment.setDeliveryStatusBuyer(Shipment.DeliveryStatus.DELIVERED);
            shipmentRepository.save(shipment);
            return true;
        }
        return false;
    }
}
