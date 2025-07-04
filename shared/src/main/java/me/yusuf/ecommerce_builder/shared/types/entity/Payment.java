package me.yusuf.ecommerce_builder.shared.types.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.Versioned;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;

@Entity
@Table(name = "payment")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends Versioned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;
    @ColumnDefault("false")
    @Column(name = "successful", nullable = false, columnDefinition = "boolean")
    private Boolean successful ;
    @Column(name = "order_id", nullable = false)
    private Integer orderId;
    @Column(name = "last_payment_attempt",nullable = true)
    private Long lastPaymentAttempt;
    @OneToOne(optional = false, mappedBy = "payment")
    @JoinColumn(name = "order_id",nullable = false,insertable = false,updatable = false)
    private Order order;
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PaymentType type;
    public enum PaymentType {
        CREDIT_CARD("credit_card"),
        PAYPAL("paypal"),
        CASH("cash");
        private final String value;
        PaymentType(String value) {
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
        if (!(o instanceof Payment payment)) return false;
        return Objects.equals(getId(), payment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
