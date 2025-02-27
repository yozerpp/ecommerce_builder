package me.yusuf.ecommerce.domain.coupon;

import me.yusuf.ecommerce.domain.cart.CartItem;
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
                throw new IllegalStateException("Coupon is inactive");
            } else if(item.getProductOffer().getSeller().getUserId() != parent.getSeller().getUserId()){
                throw new IllegalStateException("Coupon belongs to another seller");
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
