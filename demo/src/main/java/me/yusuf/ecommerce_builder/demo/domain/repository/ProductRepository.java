package me.yusuf.ecommerce_builder.demo.domain.repository;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import me.yusuf.ecommerce_builder.shared.types.entity.Category;
import me.yusuf.ecommerce_builder.shared.types.entity.Product;
import me.yusuf.ecommerce_builder.shared.types.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository("productRepository")
public interface ProductRepository extends PagingAndSortingRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    <P extends Product> P save(@NonNull P _product);
    @Nullable Product findById(Integer id);
    @NonNull Page<Product> findAllByNameLikeIgnoreCase(@NotNull @Size(max = 255) String name, Pageable pageable);
    @EntityGraph(attributePaths = {"categories", "tags"})
    @NonNull Page<Product> findAllWithCategoriesAndTagsByNameLikeIgnoreCase(@NotNull @Size(max = 255) String name, Pageable pageable);
    @Query("SELECT p FROM Product p where p.category in :#{categories}")
    @NonNull Page<Product> findProductsOfCategory(@NonNull @Param("categories") Iterable<Category> categories, Pageable pageable);
    @EntityGraph(attributePaths = {"categories", "tags"})
    @Query("SELECT p FROM Product p where p.category in :#{categories}")
    @NonNull Page<Product> findProductsOfCategoryWithCategoriesAndTags(@NonNull @Param("categories") Iterable<Category> categories, Pageable pageable);
    @Query("SELECT p FROM Product p join p.tags t where t in :#{tags}")
    @NonNull Page<Product> findProductsOfTag(@NonNull @Param("tags") Iterable<Tag> tags, Pageable pageable);
    @EntityGraph(attributePaths = {"categories", "tags"})
    @Query("SELECT p FROM Product p join p.tags t where t in :#{tags}")
    @NonNull Page<Product> findProductsOfTagWithCategoriesAndTags(@NonNull @Param("tags") Iterable<Tag> tags, Pageable pageable);

}
