package me.yusuf.ecommerce.domain.order;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import lombok.*;
import me.yusuf.ecommerce.domain.cart.Cart;
import me.yusuf.ecommerce.domain.shipment.Shipment;
import me.yusuf.ecommerce.domain.z_embeddable.Versioned;
import me.yusuf.ecommerce.domain.user.User;
import me.yusuf.ecommerce.domain.payment.Payment;
import org.springframework.lang.NonNull;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order extends Versioned {

    public Order(int id){
        this.id = id;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;
    @NotNull
    @Column(name = "total", nullable = false)
    private double total;
    @Column(name = "payment_id", nullable = true)
    private Integer paymentId;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = true,insertable = false,updatable = false)
    private Payment payment;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status = OrderStatus.ON_PAYMENT;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "order")
    private Set<Shipment> shipments = new HashSet<>();
    @Column(name = "cart_id", nullable = false,unique = true)
    private int cartId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id", nullable = false,insertable = false,updatable = false)
    private Cart cart;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,insertable = false,updatable = false)
    private User user;
//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(label = "seller", nullable = false)
//    private Seller seller;

    public enum OrderStatus {
        ON_PAYMENT("on payment"),
        AWAITING_SHIPPING("awaiting shipping"),
        SHIPPED("shipped"),
        DELIVERED("delivered"),
        CANCELLED("cancelled");

        private final String value;

        OrderStatus(String value) {
            this.value = value;
        }

        @JsonValue
        public String getValue() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Order order)) return false;
        return Objects.equals(getId(), order.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

}