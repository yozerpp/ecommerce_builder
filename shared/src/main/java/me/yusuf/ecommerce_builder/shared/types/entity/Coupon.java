package me.yusuf.ecommerce_builder.shared.types.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.Versioned;


import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "coupons")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon extends Versioned {

    @Id
    @NotNull
    @Size(max = 16)
    @Column(name = "code",unique = true, nullable = false, length = 16,updatable = false)
    private String code;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "active", nullable = false, columnDefinition = "boolean")
    private Boolean active = true;
    @Column(name = "seller_id", nullable = false)
    private int sellerId;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id",referencedColumnName = "user_id", nullable = false,insertable = false,updatable = false)
    private Seller seller;
    @Column(name = "discount", columnDefinition = "float8 CHECK(discount >= 0 AND discount <= 1)")
    private float discount;

    @Column(name = "multiple", nullable = false, columnDefinition = "boolean")
    private Boolean multiple = false;

    @Column(name = "start_date", columnDefinition = "timestamptz")
    private Instant startDate;

    @Column(name = "end_date", columnDefinition = "timestamptz")
    private Instant endDate;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coupon coupon)) return false;
        return Objects.equals(getCode(), coupon.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCode()) ;
    }
}
