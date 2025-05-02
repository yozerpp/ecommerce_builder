package me.yusuf.ecommerce_builder.demo.domain.network;

import me.yusuf.ecommerce_builder.demo.domain.service.ServiceBase;
import me.yusuf.ecommerce_builder.shared.types.entity.Seller;
import me.yusuf.ecommerce_builder.demo.domain.service.SellerService;
import me.yusuf.ecommerce_builder.demo.utils.Utils;
import me.yusuf.ecommerce_builder.shared.types.entity.Shipment;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static me.yusuf.ecommerce_builder.demo.domain.network.ControllerBase.basePath;

@RestController
@RequestMapping(basePath+"/seller")
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
    
    @PostMapping
    public ResponseEntity<String> createSeller(@RequestBody Seller seller) {
        sellerService.createSeller(seller);
        return ResponseEntity.ok("redirect:/login");
    }
    
    @GetMapping
    public String sellerProfile(Model model) {
        Seller seller = sellerService.getSellerProfile();
        if(seller == null)
            return "redirect:/login";
        else if(seller != null){
            model.addAttribute("seller", seller);
            return "seller";
        } else return "redirect:/logout?continue=login&message=You+are+not+a+seller";
    }
    
    @GetMapping("/{id}")
    public String sellerPage(@PathVariable int id, Model model) {
        model.addAttribute("seller", sellerService.getSellerPage(id));
        return "seller";
    }
    
    @GetMapping("/profile/shipments")
    public String shipments(Model model, @RequestParam(required = false, defaultValue = "null") Integer sellerId) {
        Page<Shipment> result = null;
        try {
            result = sellerService.getShipments(sellerId);
        } catch (NotFoundException e) {
            return "redirect:/error?code=404";
        } catch (BadRequestException e) {
            return "redirect:/error?code=403";
        }
        model.addAttribute("shipments", result);
        return "fragments/seller/shipments";
    }
}
