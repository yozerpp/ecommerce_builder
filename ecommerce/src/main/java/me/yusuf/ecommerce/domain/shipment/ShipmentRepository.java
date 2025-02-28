package me.yusuf.ecommerce.domain.shipment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@org.springframework.stereotype.Repository
@RepositoryRestResource(exported = false)
public interface ShipmentRepository extends Repository<Shipment,Integer> {
    @Nullable Shipment findByOrderIdAndProductIdAndSellerId(Integer userId, Integer productId, Integer sellerId);
    @PreAuthorize("isAuthenticated()")
    void saveAll(Iterable<Shipment> shipments);
    @NonNull Page<Shipment> findAllBySellerId(int sellerId, Pageable pageable);
    @PostAuthorize("returnObject.getOrder().getUser().getUsername() == principal.username or returnObject.getSeller().userId==T(me.yusuf.ecommerce.domain.user.User).cast(principal).id or hasRole('ROLE_ADMIN')")
    @Nullable Shipment findById(Integer id);
    @PostAuthorize("returnObject.content[#this.getSeller().userId == T(me.yusuf.ecommerce.domain.user.User).cast(principal).username] or returnObject.content[ #this.getOrder().getUser().getUsername() == T(me.yusuf.ecommerce.domain.user.User).cast(principal)?.username] or hasRole('ROLE_ADMIN')")
    @NonNull Page<Shipment> findWithOrdersByOrderId(Integer id, Pageable pageable);
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_SELLER')")
    @Query("SELECT s from Shipment s where s.seller.userId = ?#{T(me.yusuf.ecommerce.domain.user.User).cast(principal).id}")
    @NonNull Page<Shipment> getShipmentsOfCurrentSeller(@Nullable Pageable pageable);
    @PreAuthorize("isAuthenticated()")
    @Query("SELECT s from Shipment s join Order o on o=s.order join User u on u = o.user where u.username = ?#{principal?.username}")
    @NonNull Page<Shipment> getShipmentsOfCurrentBuyer(@Nullable Pageable pageable);
    @PreAuthorize("T(me.yusuf.ecommerce.domain.user.User).cast(principal).id == #shipment.getSeller().userId or hasRole('ROLE_ADMIN')")
    <S extends Shipment> S save(@NonNull @Param("shipment") S _shipment);
    @PreAuthorize("T(me.yusuf.ecommerce.domain.user.User).cast(principal).id == shipment.getSeller().userId or hasRole('ROLE_ADMIN')")
    <S extends Shipment> void delete(S shipment);
    @PreAuthorize("shipmentRepository.findById(id).getSeller().userId == T(me.yusuf.ecommerce.domain.user.User).cast(principal).id or hasRole('ROLE_ADMIN')")
    void deleteById(int id);
    @PreAuthorize("shipmentRepository.findById(id).getSeller().userId==T(me.yusuf.ecommerce.domain.user.User).cast(principal).id or hasRole('ROLE_ADMIN')")
    @Modifying
    @Query("UPDATE Shipment s set s.deliveryStatusSeller = ?2 where s.id = ?1")
    void updateSellerShipmentStatusById(int id,@NonNull Shipment.DeliveryStatus status);
    @PreAuthorize("shipmentRepository.findById(id).getOrder().getUser().username==principal.username or hasRole('ROLE_ADMIN')")
    @Modifying
    @Query("UPDATE Shipment s set s.deliveryStatusBuyer = ?2 where s.id = ?1")
    void updateBuyerShipmentStatusById(int id,@NonNull Shipment.DeliveryStatus status);
}
