package me.yusuf.ecommerce_builder.demo.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface ProductOfferRepository extends Repository<ProductOffer, ProductOffer.ProductOfferId> {
    @Nullable
    ProductOffer findById(ProductOffer.ProductOfferId id);
    @EntityGraph(attributePaths = "seller")
    @Nullable ProductOffer findWithSellerById(ProductOffer.ProductOfferId id);
    @Query("Select ps from ProductOffer ps join Product p on p.id= ps.product.id where p.id = ?1")
    @NonNull Page<ProductOffer> findOffersOfProduct(@NonNull Integer productId, @Nullable Pageable pageable);
    @Query("Select ps from ProductOffer ps join Seller s on s.userId = ps.seller.userId where s.userId = ?1")
    @NonNull Page<ProductOffer> findAllBySellerId(@NonNull Integer sellerId, @Nullable Pageable pageable);
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_SELLER') and #_productSeller.getSeller().userId == T(me.yusuf.ecommerce_builder.demo.domain.user.User).cast(principal).id")
    <PS extends ProductOffer> PS save(@NonNull PS _productSeller);
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_SELLER') and #_productSeller.getSeller().userId == T(me.yusuf.ecommerce_builder.demo.domain.user.User).cast(principal).id")
    <PS extends ProductOffer> void delete(@NonNull PS _productSeller);

}
