package me.yusuf.ecommerce.controller;

import me.yusuf.ecommerce.domain.coupon.Coupon;
import me.yusuf.ecommerce.domain.coupon.CouponService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/coupon")
public class CouponController extends ControllerBase {
    private final CouponService service;

    public CouponController(CouponService service) {
        this.service = service;
    }

    @GetMapping("/{code}")
    public Coupon one(@PathVariable String code) {
        return this.service.getCoupon(code);
    }

    @PostMapping
    public Map<String, Object> createCoupon(@RequestBody Coupon coupon) {
        Map<String, Object> response = new HashMap<>();
        var code = this.service.createCoupon(coupon);
        response.put("message", createdMessage(code, "/coupon"));
        return response;
    }

    @DeleteMapping("/{code}")
    public Map<String, Object> deleteCoupon(@PathVariable String code) {
        Map<String, Object> response = new HashMap<>();
        try {
            this.service.invalidateCoupon(code);
            response.put("message", "Coupon successfully deleted");
        } catch (AccessDeniedException e) {
            response.put("message", "You do not have permission to delete this coupon");
        }
        return response;
    }
}
