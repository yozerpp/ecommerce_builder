package me.yusuf.ecommerce.domain.cart;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import me.yusuf.ecommerce.domain.coupon.Coupon;
import me.yusuf.ecommerce.domain.product.ProductOffer;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor@NoArgsConstructor
@Entity
public class CartItem {
    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor@NoArgsConstructor
    public static class CartItemId implements Serializable {
        @Embedded
        private ProductOffer.ProductOfferId offerId;
        @Column(name = "cart_id")
        private Integer cartId;

        @Override
        public boolean equals(Object o) {
            if (!(o instanceof CartItemId that)) return false;
            return Objects.equals(offerId, that.offerId) && Objects.equals(cartId, that.cartId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(offerId, cartId);
        }
    }
    @EmbeddedId
    private CartItemId id;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "price")
    @Getter(AccessLevel.NONE)
    @Transient
    private double totalPrice;
    @Column(name = "coupon_code")
    private String couponCode;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "coupon_code", insertable = false, updatable = false)
    private Coupon coupon;
    @Transient
    public double getTotalPrice() {
        return getProductOffer().getDiscountedPrice() * getQuantity() * (getCoupon()!=null?getCoupon().getDiscount():1);
    }
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cart_id", insertable = false, updatable = false)
    @JsonBackReference("cartItems")
    private Cart cart;
    @ManyToOne
    @JoinColumns({@JoinColumn(name = "product_id",insertable = false, updatable = false),@JoinColumn(name = "seller_id",insertable = false, updatable = false)})
    private ProductOffer productOffer;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CartItem cartItem)) return false;
        return Objects.equals(getId(), cartItem.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}
