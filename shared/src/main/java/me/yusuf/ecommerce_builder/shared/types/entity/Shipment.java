package me.yusuf.ecommerce_builder.shared.types.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.Address;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.Versioned;
import org.hibernate.annotations.GeneratedColumn;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "shipments")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Shipment extends Versioned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @Column(name = "order_id",nullable = false)
    private int orderId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false,insertable = false,updatable = false)
    private Order order;
    @Embedded
    @AttributeOverrides({@AttributeOverride(name = "offerId.productId", column = @Column(name = "offer_product_id")),@AttributeOverride(name = "offerId.sellerId",column = @Column(name = "offer_seller_id"))})
    private CartItem.CartItemId cartItemId;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({@JoinColumn(name = "offer_product_id", referencedColumnName ="product_id" , insertable = false,updatable = false), @JoinColumn(name = "offer_seller_id",referencedColumnName = "seller_id",insertable = false,updatable = false),@JoinColumn(name = "cart_id",referencedColumnName = "cart_id",insertable = false,updatable = false)})
    private CartItem cartItem;
    @Column(name = "product_id",nullable = false)
    private int productId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false,insertable = false,updatable = false)
    private Product product;
    @Column(name = "seller_id",nullable = false)
    private int sellerId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id",referencedColumnName = "user_id", nullable = false,insertable = false,updatable = false)
    private Seller seller;
    @Embedded
    private Address deliveryAddress;
    @NotNull
    @Column(name = "ship_date", nullable = false)
    private LocalDate shipDate;

    @Column(name = "delivered_date")
    private LocalDate deliveredDate;

    @Enumerated(EnumType.STRING)
    @GeneratedColumn("CASE WHEN delivery_status_buyer = 'DELIVERED' THEN 'DELIVERED' WHEN delivery_status_seller = 'SHIPPED' THEN 'SHIPPED' ELSE 'PENDING' END")
    @Column(name = "delivery_status", nullable = false, updatable = false,insertable = false, length = 20)
    private DeliveryStatus deliveryStatus;
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status_buyer", nullable = true, insertable = false,updatable = true, length = 20)
    private DeliveryStatus deliveryStatusBuyer;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status_seller", nullable = false, length = 20)
    private DeliveryStatus deliveryStatusSeller;
    public static enum DeliveryStatus {
        PENDING,SHIPPED, DELIVERED, CANCELLED;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Shipment shipment)) return false;
        return Objects.equals(getId(), shipment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
