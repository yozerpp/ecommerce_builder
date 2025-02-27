package me.yusuf.ecommerce.domain.session;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.yusuf.ecommerce.domain.cart.Cart;
import me.yusuf.ecommerce.domain.z_embeddable.Versioned;
import org.hibernate.annotations.JdbcTypeCode;

import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Session extends Versioned {
    @Id
    @Column(name = "id", nullable = false, updatable = false, length = 255)
    private String id;
    @Column(name = "data", columnDefinition = "jsonb")
    @JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    private Map<String,Object> data;
    @Column(name = "cart_id")
    private Integer cartId;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cart_id", referencedColumnName = "id", updatable = false,insertable = false)
    private Cart cart;
    // Relationships can be added if needed when referenced in other entities

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Session session)) return false;
        return Objects.equals(getId(), session.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}