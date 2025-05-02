package me.yusuf.ecommerce_builder.shared.types.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.Versioned;
import org.hibernate.annotations.JdbcTypeCode;

import java.util.*;

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends Versioned {
    public static final Category NONE = new Category(0,"UNCATEGORIZED","This product hasn't been categorized",new HashMap<String,Object>(),null,null,null,new HashSet<Product>());
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @NotNull
    @Column(name = "label", nullable = false, length = 100)
    private String name;

    @Column(name = "description", length = 255)
    private String description;

    @Column(name = "specifications", columnDefinition = "jsonb")
    @JdbcTypeCode(org.hibernate.type.SqlTypes.JSON)
    private Map<String, Object> specifications;

    @Column(name = "parent_id")
    private Integer parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Category parentCategory;
    @OneToMany(mappedBy = "parentCategory", fetch = FetchType.LAZY)
    private Set<Category> subCategories = new HashSet<>();
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private java.util.Set<Product> products = new java.util.HashSet<>();
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Category category)) return false;
        return Objects.equals(getId(), category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}