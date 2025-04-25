package me.yusuf.ecommerce_builder.demo.service;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce_builder.demo.domain.ServiceBase;
import me.yusuf.ecommerce_builder.demo.domain.product.ProductOffer;
import me.yusuf.ecommerce_builder.demo.domain.seller.Seller;
import me.yusuf.ecommerce_builder.demo.domain.seller.SellerRepository;
import me.yusuf.ecommerce_builder.demo.domain.shipment.ShipmentRepository;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodMetadataAnn;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @MethodMetadataAnn(methodFriendlyName = "Teklif Getir")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public Set<ProductOffer> offers(){
        return sellerRepository.findWithProductOffersProductByUserId(getUser().getId()).getProductOffers();
    }
    
    @MethodMetadataAnn(methodFriendlyName = "Gönderi Getir")
    @PreAuthorize("hasAnyRole('ROLE_SELLER','ROLE_ADMIN', 'ROLE_STAFF')")
    @GetMapping("/profile/shipments")
    public String shipments(Model model, @RequestParam(required = false, defaultValue = "null") Integer sellerId){
        var user = getUser();
        int sId;
        if (user.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_SELLER"))) {
            Optional<Seller> s;
            if (sellerId == null) return "redirect:/error?code=403";
            else if((s = sellerRepository.findById(sellerId)).isEmpty()) return "redirect:/error?code=404";
            else sId = s.get().getUserId();
        }
        else sId = user.getId();
        var shipments = shipmentRepository.findAllBySellerId(sId, null);
        model.addAttribute("shipments", shipments);
        return "fragments/seller/shipments";
    }
    
    @MethodMetadataAnn(methodFriendlyName = "Satıcı Oluştur")
    @PostMapping
    public String createSeller(@RequestBody Seller seller){
        sellerRepository.save(seller);
        return "redirect:/login";
    }
    
    @MethodMetadataAnn(methodFriendlyName = "Satıcı Profil")
    @GetMapping
    public String sellerProfile(Model model){
        var user = getUser();
        Seller seller;
        if(user == null)
            return "redirect:/login";
        else if((seller = user.getSeller()) != null){
            model.addAttribute("seller", seller);
            return "seller";
        } else return "redirect:/logout?continue=login&message=You+are+not+a+seller";
    }
    
    @MethodMetadataAnn(methodFriendlyName = "Satıcı Sayfa")
    @GetMapping("/{id}")
    public String sellerPage(@PathVariable int id, Model model){
        model.addAttribute("seller", sellerRepository.findWithProductOffersProductByUserId(id));
        return "seller";
    }
}
