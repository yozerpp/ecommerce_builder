package me.yusuf.ecommerce_builder.demo.domain.service;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce_builder.shared.types.entity.Coupon;
import me.yusuf.ecommerce_builder.demo.domain.repository.CouponRepository;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodInfo;
import me.yusuf.ecommerce_builder.shared.types.entity.Seller;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple3;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller("/coupon")
@RequestMapping("/coupon")
public class CouponService extends ServiceBase {
    CouponRepository couponRepository;
    public CouponService( CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }
    
    @MethodInfo(userFriendlyName = "Kupon Oluştur")
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_SELLER')")
    public Tuple3<String, Coupon, Seller> createCoupon(@RequestBody Coupon coupon){
        coupon.setSeller(getUser().getSeller());
        coupon = couponRepository.save(coupon);
        return new Tuple3<>(coupon.getCode(), coupon, coupon.getSeller());
    }

    @MethodInfo(userFriendlyName = "Kupon Getir")
    public @Nullable Coupon getCoupon(@PathVariable String code){
        return couponRepository.findCouponByCode(code);
    }
    
    @MethodInfo(userFriendlyName = "Kupon İptal")
    public Coupon invalidateCoupon(@PathVariable String code) throws NotFoundException {
        var c = couponRepository.findCouponByCode(code);
        if (c==null) throw new  NotFoundException( code + " kodlu kupon bulunamadı.");
        couponRepository.delete(c);
        return c;
    }
}
