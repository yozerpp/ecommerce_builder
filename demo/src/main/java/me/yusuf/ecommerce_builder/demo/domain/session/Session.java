package me.yusuf.ecommerce_builder.demo.domain.session;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import me.yusuf.ecommerce_builder.demo.domain.cart.Cart;
import me.yusuf.ecommerce_builder.demo.domain.z_embeddable.Versioned;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @Column(name = "cart_id", nullable = true)
    private Integer cartId;
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    @JoinColumn(name = "cart_id", referencedColumnName = "id", insertable = false, updatable = false
    ,foreignKey = @ForeignKey(foreignKeyDefinition = "FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE SET NULL\n" +
            "On UPDATE CASCADE"))
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