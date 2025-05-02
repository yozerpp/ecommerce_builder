package me.yusuf.ecommerce_builder.demo.domain.service;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce_builder.shared.types.entity.ProductOffer;
import me.yusuf.ecommerce_builder.shared.types.entity.Seller;
import me.yusuf.ecommerce_builder.demo.domain.repository.SellerRepository;
import me.yusuf.ecommerce_builder.demo.domain.repository.ShipmentRepository;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodInfo;
import me.yusuf.ecommerce_builder.shared.types.entity.Shipment;
import me.yusuf.ecommerce_builder.shared.types.exception.NotFoundException;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class SellerService extends ServiceBase {
    private final ShipmentRepository shipmentRepository;
    private final SellerRepository sellerRepository;
    public SellerService(SellerRepository sellerRepository, ShipmentRepository shipmentRepository) {
        this.sellerRepository = sellerRepository;
        this.shipmentRepository = shipmentRepository;
    }

    @MethodInfo(userFriendlyName = "Teklif Getir")
    @PreAuthorize("hasRole('ROLE_SELLER')")
    public Set<ProductOffer> offers(){
        return sellerRepository.findWithProductOffersProductByUserId(getUser().getId()).getProductOffers();
    }
    
    @MethodInfo(userFriendlyName = "Gönderi Getir")
    @PreAuthorize("hasAnyRole('ROLE_SELLER','ROLE_ADMIN', 'ROLE_STAFF')")
    public Page<Shipment> getShipments(Integer sellerId) throws NotFoundException, BadRequestException {
        var user = getUser();
        int sId;
        if (user.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_SELLER"))) {
            Optional<Seller> s;
            if (sellerId == null) throw new BadRequestException("sellerId is null");
            else if((s = sellerRepository.findById(sellerId)).isEmpty()) throw new NotFoundException("Satıcı bulunamadı");
            else sId = s.get().getUserId();
        }
        else sId = user.getId();
        return shipmentRepository.findAllBySellerId(sId, null);
    }
    
    @MethodInfo(userFriendlyName = "Satıcı Oluştur")
    public Seller createSeller(Seller seller){
        return sellerRepository.save(seller);
    }
    
    @MethodInfo(userFriendlyName = "Satıcı Profil")
    public Seller getSellerProfile(){
        var user = getUser();
        if(user == null)
            return null;
        return user.getSeller();
    }
    
    @MethodInfo(userFriendlyName = "Satıcı Sayfa")
    public Seller getSellerPage(int id){
        return sellerRepository.findWithProductOffersProductByUserId(id);
    }
}
