package me.yusuf.ecommerce.domain.product;

import me.yusuf.ecommerce.domain.category.Category;
import me.yusuf.ecommerce.domain.tag.Tag;
import org.springframework.data.rest.core.config.Projection;

import java.util.Set;

@Projection(name = "withTagAndCategory", types = {Product.class})
public interface WithTagAndCategory {
    Integer getId();
    String getName();
    Boolean isTaxable();
    Set<Tag> getTags();
    String getSpecifications();
    Set<Category> getCategories();
}
