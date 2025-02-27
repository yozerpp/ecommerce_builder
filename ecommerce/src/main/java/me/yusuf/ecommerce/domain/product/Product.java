package me.yusuf.ecommerce.domain.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.yusuf.ecommerce.domain.category.Category;
import me.yusuf.ecommerce.domain.tag.Tag;
import me.yusuf.ecommerce.domain.z_embeddable.Versioned;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends Versioned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @NotNull
    @Size(max = 255)
    @Column(name = "label", nullable = false, length = 255)
    private String name;
    @Column(name = "image", length = 255)
    @JdbcTypeCode(SqlTypes.BLOB)
    private byte[] image;
    @Column(name = "taxable", nullable = false, columnDefinition = "boolean")
    private Boolean taxable = false;
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "product")
    private java.util.Set<ProductOffer> offers = new HashSet<>();
    @Column(name = "description", length = 255*16)
    private String description;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "product_tag",joinColumns = @JoinColumn(name = "product_id")
            ,inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private java.util.Set<Tag> tags = new java.util.HashSet<>();
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "specifications", nullable = true, columnDefinition = "jsonb")
    private Map<String, Object> specifications;
    @Column(name = "category_id", length = 255)
    private int categoryId;
    @JoinColumn(name = "category_id", referencedColumnName = "id",insertable = false, updatable = false)
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    private Category category;
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Product product)) return false;
        return Objects.equals(getId(), product.getId());
    }
    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}