package me.yusuf.ecommerce.service;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce.domain.ServiceBase;
import me.yusuf.ecommerce.domain.product.ProductOffer;
import me.yusuf.ecommerce.domain.seller.Seller;
import me.yusuf.ecommerce.domain.seller.SellerRepository;
import me.yusuf.ecommerce.domain.shipment.ShipmentRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Service
public class SellerService extends ServiceBase {
    private final ShipmentRepository shipmentRepository;
    private final SellerRepository sellerRepository;
    public SellerService(EntityManager entityManager, SellerRepository sellerRepository, ShipmentRepository shipmentRepository) {
        super(entityManager);
        this.sellerRepository = sellerRepository;
        this.shipmentRepository = shipmentRepository;
    }

    @PreAuthorize("hasRole('ROLE_SELLER')")
    public Set<ProductOffer> offers(){
        return sellerRepository.findWithProductOffersProductByUserId(getUser().getId()).getProductOffers();
    }
    @PreAuthorize("hasAnyRole('ROLE_SELLER','ROLE_ADMIN', 'ROLE_STAFF')")
    @GetMapping("/profile/shipments")
    public String shipments (Model model,@RequestParam(required = false, defaultValue = "null") Integer sellerId){
        var user = getUser();
        int sId;
        if (user.getAuthorities().stream().noneMatch(a->a.getAuthority().equals("ROLE_SELLER"))) {
            Optional<Seller> s;
            if (sellerId == null) return "redirect:/error?code=403";
            else if((s=sellerRepository.findById(sellerId)).isEmpty()) return "redirect:/error?code=404";
            else sId = s.get().getUserId();
        }
        else sId = user.getId();
        var shipments = shipmentRepository.findAllBySellerId(sId,null);
        model.addAttribute("shipments", shipments);
        return "fragments/seller/shipments";
    }
    @PostMapping
    public String createSeller(@RequestBody Seller seller){

        sellerRepository.save(seller);
        return "redirect:/login";
    }
    @GetMapping
    public String sellerProfile(Model model){
        var user = getUser();
        Seller seller;
        if(user == null)
            return "redirect:/login";
        else if((seller = user.getSeller())!=null){
            model.addAttribute("seller", seller);
            return "seller";
        } else return "redirect:/logout?continue=login&message=You+are+not+a+seller";
    }
    @GetMapping("/{id}")
    public String sellerPage(@PathVariable int id, Model model){
        model.addAttribute("seller",sellerRepository.findWithProductOffersProductByUserId(id));
        return "seller";
    }

}
