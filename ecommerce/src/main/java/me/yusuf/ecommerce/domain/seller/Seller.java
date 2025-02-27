package me.yusuf.ecommerce.domain.seller;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.yusuf.ecommerce.domain.order.Order;
import me.yusuf.ecommerce.domain.product.ProductOffer;
import me.yusuf.ecommerce.domain.shipment.Shipment;
import me.yusuf.ecommerce.domain.user.User;
import me.yusuf.ecommerce.domain.z_embeddable.Address;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Set;

@Table(name = "sellers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Seller {
    @Id
    @Column(name = "user_id", nullable = false)
    int userId;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, insertable = false, updatable = false)
    private User user;
    @NotNull
    @Column(name = "shop_name", nullable = false, length = 255)
    private String shopName;
    @NotNull
    @Column(name = "id_verified", nullable = false)
    private Boolean idVerified = false;
    @NotNull
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "seller")
    Set<ProductOffer> productOffers = new java.util.HashSet<>();
    @OneToMany(mappedBy = "seller",fetch = FetchType.LAZY)
    private java.util.Set<Shipment> shipments = new java.util.HashSet<>();
    public Set<Shipment> getShipments(){
        if(!((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId().equals(this.getUserId()))
            throw new AccessDeniedException("You are not authorized to access this resource");
        return this.shipments;
    }
    @NotNull
    @Embedded
    private Address shopAddress;

}