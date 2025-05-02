package me.yusuf.ecommerce_builder.shared.types.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.Versioned;
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
    @ColumnDefault("0")
    @Column(name = "item_count",nullable = false)
    private int item_count;
    @NotNull
    @ColumnDefault("false")
    @Column(name = "ordered", nullable = false, columnDefinition = "boolean")
    private boolean ordered;
    @OneToMany(mappedBy = "cart", fetch = FetchType.LAZY, cascade= CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("cartItems")
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