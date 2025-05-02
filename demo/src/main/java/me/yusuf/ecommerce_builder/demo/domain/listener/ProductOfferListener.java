package me.yusuf.ecommerce_builder.demo.domain.listener;

import me.yusuf.ecommerce_builder.shared.types.entity.ProductOffer;
import me.yusuf.ecommerce_builder.demo.domain.repository.ProductRepository;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

@Component
public class ProductOfferListener extends AbstractRepositoryEventListener<ProductOffer> {
    private final ProductRepository productRepository;
    public ProductOfferListener(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    protected void onBeforeSave(ProductOffer entity) {
        if(entity.getStock()==0) entity.setInStock(false);
    }

    @Override
    protected void onAfterSave(ProductOffer entity) {
        if (entity.getProduct().getDescription().length() < entity.getDescription().length())
            entity.getProduct().setDescription(entity.getDescription());
        productRepository.save(entity.getProduct());
    }
}
