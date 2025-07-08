package me.yusuf.ecommerce_builder.demo.domain.listener;

import me.yusuf.ecommerce_builder.demo.domain.repository.CouponRepository;
import me.yusuf.ecommerce_builder.shared.types.entity.CartItem;
import me.yusuf.ecommerce_builder.shared.types.entity.Coupon;
import org.springframework.data.rest.core.event.AbstractRepositoryEventListener;
import org.springframework.stereotype.Component;

@Component
public class CouponListener extends AbstractRepositoryEventListener<Coupon> {
    CouponRepository couponRepository;
    public CouponListener(CouponRepository repository) {
        this.couponRepository = repository;
    }
    @Override
    protected void onBeforeLinkSave(Coupon parent, Object linked) {
        if(linked instanceof CartItem item){
            if(!couponRepository.isCouponActive(parent.getCode())){
                throw new IllegalStateException("CouponImpl is inactive");
            } else if(item.getProductOffer().getSeller().getUserId() != parent.getSeller().getUserId()){
                throw new IllegalStateException("CouponImpl belongs to another seller");
            }
        }
    }
    @Override
    protected void onAfterLinkSave(Coupon parent, Object linked) {
        if(linked instanceof CartItem item){
            if(!parent.getMultiple()) {
                parent.setActive(false);
                couponRepository.save(parent);
            }
        }
    }
}
