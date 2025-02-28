package me.yusuf.ecommerce.domain.seller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface SellerRepository extends JpaRepository<Seller, Integer> {
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_SELLER')")
    @Query("SELECT s from Seller s join User u on u.id = s.userId where u.username = ?#{principal.username}")
    @Nullable Seller getCurrentSeller();
    @Query("Select s from Seller s join ProductOffer ps on s.userId = ps.seller.userId join Product p on p.id = ps.product.id where p.id = ?1")
    @NonNull Page<Seller> getSellersOfProduct(@NonNull Integer productId, @Nullable Pageable pageable);
    @NonNull Page<Seller> findAllByShopNameLike(@NonNull String shopName,@Nullable Pageable pageable);
    @EntityGraph(attributePaths = {"productOffers", "productOffers.product"})
    @Nullable Seller findWithProductOffersProductByUserId(Integer id);

    java.util.List<Object> findByUserId(int userId);
}
