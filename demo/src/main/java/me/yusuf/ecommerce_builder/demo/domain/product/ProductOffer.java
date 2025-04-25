package me.yusuf.ecommerce_builder.demo.domain.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.yusuf.ecommerce_builder.demo.domain.z_embeddable.Versioned;
import me.yusuf.ecommerce_builder.demo.domain.seller.Seller;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GeneratedColumn;

import java.util.Objects;

@Entity
@Table(name = "product_offers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOffer extends Versioned {
    @EmbeddedId
    private ProductOfferId id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false,insertable = false,updatable = false)
    private Product product;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id",referencedColumnName = "user_id", nullable = false,insertable = false,updatable = false)
    private Seller seller;
    @Column(name = "description", nullable = true,length = 255*16)
    private String description;
    @Column(name = "stock", nullable = false)
    private int stock;
    @Column(name = "regular_price", nullable = false)
    private double regularPrice;
    @ColumnDefault("0.0")
    @Column(name = "discount", nullable = false, columnDefinition = "numeric")
    private float discount;
    @Column(name = "discounted_price", nullable = false, updatable = false, insertable = false)
    @GeneratedColumn("discount * regular_price")
    private double discountedPrice;
    @Column(name = "in_stock", nullable = false)
    private boolean inStock;
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ProductOffer that)) return false;
        return Objects.equals(getId(), that.getId());
    }
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Embeddable@AllArgsConstructor@NoArgsConstructor@Getter@Setter
    public static class ProductOfferId implements java.io.Serializable{
        @Column(name = "product_id",nullable = false,updatable = false)
        int productId;
        @Column(name = "seller_id",nullable = false,updatable = false)
        int sellerId;
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof ProductOfferId that)) return false;
            return getProductId() == that.getProductId() && getSellerId() == that.getSellerId();
        }
        @Override
        public int hashCode() {
            return Objects.hash(getProductId(), getSellerId());
        }
    }
}