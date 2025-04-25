package me.yusuf.ecommerce_builder.demo.service;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce_builder.demo.domain.ServiceBase;
import me.yusuf.ecommerce_builder.demo.domain.coupon.Coupon;
import me.yusuf.ecommerce_builder.demo.domain.coupon.CouponRepository;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodMetadataAnn;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("/coupon")
@RequestMapping("/coupon")
public class CouponService extends ServiceBase {
    CouponRepository couponRepository;
    public CouponService(EntityManager entityManager, CouponRepository couponRepository) {
        super(entityManager);
        this.couponRepository = couponRepository;
    }
    
    @MethodMetadataAnn(methodFriendlyName = "Kupon Oluştur")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_SELLER')")
    public String createCoupon(@RequestBody Coupon coupon){
        coupon.setSeller(getUser().getSeller());
        coupon = couponRepository.save(coupon);
        return coupon.getCode();
    }
    
    @MethodMetadataAnn(methodFriendlyName = "Kupon Getir")
    public @Nullable Coupon getCoupon(@PathVariable String code){
        return couponRepository.findCouponByCode(code);
    }
    
    @MethodMetadataAnn(methodFriendlyName = "Kupon İptal")
    public void invalidateCoupon(@PathVariable String code) {
        couponRepository.deactivateCouponByCode(code);
    }
}
