package me.yusuf.ecommerce.service;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce.domain.ServiceBase;
import me.yusuf.ecommerce.domain.coupon.Coupon;
import me.yusuf.ecommerce.domain.coupon.CouponRepository;
import me.yusuf.ecommerce_builder.shared.MethodMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;

@Controller("/coupon")
@RequestMapping("/coupon")
public class CouponService extends ServiceBase {
    CouponRepository couponRepository;
    public CouponService(EntityManager entityManager, CouponRepository couponRepository) {
        super(entityManager);
        this.couponRepository = couponRepository;
    }
    
    @MethodMetadata(name = "Kupon Oluştur")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_SELLER')")
    public String createCoupon(@RequestBody Coupon coupon){
        coupon.setSeller(getUser().getSeller());
        coupon = couponRepository.save(coupon);
        return coupon.getCode();
    }
    
    @MethodMetadata(name = "Kupon Getir")
    public @Nullable Coupon getCoupon(@PathVariable String code){
        return couponRepository.findCouponByCode(code);
    }
    
    @MethodMetadata(name = "Kupon İptal")
    public void invalidateCoupon(@PathVariable String code) {
        couponRepository.deactivateCouponByCode(code);
    }
}
