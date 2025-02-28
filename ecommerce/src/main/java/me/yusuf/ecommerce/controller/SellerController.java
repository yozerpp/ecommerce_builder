package me.yusuf.ecommerce.controller;

import me.yusuf.ecommerce.domain.seller.Seller;
import me.yusuf.ecommerce.domain.seller.SellerService;
import me.yusuf.ecommerce.utils.Utils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/seller")
public class SellerController extends ControllerBase {
    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @GetMapping("/register")
    public Map<String, Object> register(){
        Map<String, Object> response = new HashMap<>();
        var user = ServiceBase.getUser();
        if(user == null)
            response.put("message", "You need to have a user account to create a seller account");
        else if(!user.isEnabled()){
            response.put("message", "Your account is disabled, please contact administration to re-enable it before creating a seller account");
        } else{
            var fields = Utils.propertyMap(Seller.class);
            response.put("fields", fields);
        }
        return response;
    }

    @GetMapping("/products")
    public Map<String, Object> products(){
        Map<String, Object> response = new HashMap<>();
        var offers = this.sellerService.offers();
        response.put("products", offers);
        return response;
    }
}
