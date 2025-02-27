package me.yusuf.ecommerce.domain.coupon;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;

public interface CouponRepository extends Repository<Coupon, String> {
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_SELLER') and T(me.yusuf.ecommerce.domain.user.User).cast(principal).userId = #_coupon.seller.userId")
    <C extends Coupon> C save(@NonNull @P("_coupon") C coupon);
    @PreAuthorize("isAuthenticated() and (hasRole('ROLE_SELLER') and T(me.yusuf.ecommerce.domain.user.User).cast(principal).userId = #_coupon.seller.userId or hasRole('ROLE_ADMIN'))")
    <C extends Coupon> void delete(@NonNull @P("_coupon") C _coupon);
    @Nullable Coupon findCouponByCode(@NotNull @Size(max = 255) String code);
    @Nullable Coupon findCouponByCodeAndActiveIsTrue(@NotNull @Size(max = 255) String code);
    @NonNull Page<Coupon> findCouponByCodeLike(@NotNull @Size(max = 255) String code, Pageable pageable);
    @NonNull Page<Coupon> findCouponByCodeLikeAndActiveIsTrue(@NotNull @Size(max = 255) String code, Pageable pageable);

    @Query("SELECT c.active from Coupon c where c.code = ?1")
    boolean isCouponActive(@NotNull String code);
    //how to authorize this?
    @Query("UPDATE Coupon c set c.active = false where c.code=?1")
    @Modifying
    void deactivateCouponByCode(@NotNull String code);
    @Nullable Coupon getCouponByCode(@NotNull @Size(max = 255) String code);
}