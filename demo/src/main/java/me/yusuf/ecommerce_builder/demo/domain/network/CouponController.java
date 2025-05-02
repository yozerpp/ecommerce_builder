package me.yusuf.ecommerce_builder.demo.domain.network;

import me.yusuf.ecommerce_builder.shared.types.entity.Coupon;
import me.yusuf.ecommerce_builder.demo.domain.service.CouponService;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static me.yusuf.ecommerce_builder.demo.domain.network.ControllerBase.basePath;

@RestController
@RequestMapping(basePath+"/coupon")
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
        var context = this.service.createCoupon(coupon);
        response.put("message", createdMessage(context._1(), "/coupon"));
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
        } catch (NotFoundException e){
            response.put("message", e.getMessage());
        }
        return response;
    }
}
