package me.yusuf.ecommerce_builder.shared.types.dto;

import me.yusuf.ecommerce_builder.shared.types.entity.Category;
import me.yusuf.ecommerce_builder.shared.types.entity.Product;
import me.yusuf.ecommerce_builder.shared.types.entity.Tag;
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
