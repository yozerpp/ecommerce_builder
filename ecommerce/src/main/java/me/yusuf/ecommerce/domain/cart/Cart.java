package me.yusuf.ecommerce.domain.cart;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.yusuf.ecommerce.domain.session.Session;
import me.yusuf.ecommerce.domain.z_embeddable.Versioned;
import org.hibernate.annotations.ColumnDefault;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends Versioned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;
    @ColumnDefault("0.0")
    @Column(name = "total", nullable = false)
    private double total;
    @NotNull
    @ColumnDefault("false")
    @Column(name = "ordered", nullable = false, columnDefinition = "boolean")
    private boolean ordered;
    @Column(name = "session_id", nullable = false)
    private String sessionId;
    @NotNull
    @OneToOne(fetch = FetchType.EAGER, mappedBy = "cart")
    @JoinColumn(name = "session_id", insertable = false,updatable = false, referencedColumnName = "id", nullable = false)
    private Session session;
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Cart cart)) return false;
        return Objects.equals(getId(), cart.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}