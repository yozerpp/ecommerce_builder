package me.yusuf.ecommerce.controller;

import me.yusuf.ecommerce.domain.coupon.Coupon;
import me.yusuf.ecommerce.domain.coupon.CouponRepository;
import me.yusuf.ecommerce.domain.coupon.CouponService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/coupon")
public class CouponController extends ControllerBase {
    private final CouponService service;

    public CouponController(CouponService service) {
        this.service = service;
    }

    @GetMapping("/{code}")
    public String one(@PathVariable String code, Model model) {
        var c = this.service.getCoupon(code);
        model.addAttribute("coupon", c);
        return "fragments/coupon";
    }

    @PostMapping
    public String createCoupon(@RequestBody Coupon coupon, Model model) {
        var code = this.service.createCoupon(coupon);
        model.addAttribute("message", createdMessage(code, "/coupon"));
        return "fragments/seller/coupon";
    }

    @DeleteMapping("/{code}")
    public String deleteCoupon(@PathVariable String code, Model model) {
        try {
            this.service.invalidateCoupon(code);
            model.addAttribute("message", "Coupon successfully deleted");
        } catch (AccessDeniedException e) {
            model.addAttribute("message", "You do not have permission to delete this coupon");
        }
        return null;
    }
}