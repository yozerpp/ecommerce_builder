package me.yusuf.ecommerce_builder.shared.types.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "tags")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tag{
    @Id
    @NotNull
    @Size(max = 32)
    @Column(name = "id", nullable = false, length = 32)
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_tag",joinColumns = @JoinColumn(name = "tag_id"),inverseJoinColumns = @JoinColumn(name = "product_id"))
    private java.util.Set<Product> products = new java.util.HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Tag tag)) return false;
        return Objects.equals(getName(), tag.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName());
    }
}
