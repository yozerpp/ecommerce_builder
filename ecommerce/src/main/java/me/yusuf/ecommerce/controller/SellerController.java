package me.yusuf.ecommerce.controller;

import me.yusuf.ecommerce.domain.ServiceBase;
import me.yusuf.ecommerce.domain.seller.Seller;
import me.yusuf.ecommerce.domain.seller.SellerService;
import me.yusuf.ecommerce.utils.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("sellerController")
@RequestMapping("/seller")
public class SellerController extends ControllerBase{
    private final SellerService sellerService;
    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }
    @GetMapping("/register")
    public String register(Model model){
        var user = ServiceBase.getUser();
        if(user==null)
            model.addAttribute("message", "You need to have a user account to create a seller account");
        else if(!user.isEnabled()){
            model.addAttribute("message", "Your account is disabled, please contact administration to re-enable it before creating a seller account");
        } else{
            var fields = Utils.propertyMap(Seller.class);
            model.addAttribute("fields", fields);
        }
        return "seller-register";
    }
    @GetMapping("/seller/products")
    public String products (Model model){
        var offers = this.sellerService.offers();
        model.addAttribute("products",offers);
        return "fragments/seller/products";
    }

}
