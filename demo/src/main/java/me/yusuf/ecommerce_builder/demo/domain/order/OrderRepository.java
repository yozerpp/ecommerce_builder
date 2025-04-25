package me.yusuf.ecommerce_builder.demo.domain.order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.SortDefault;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface OrderRepository extends Repository<Order, Integer> {
    @PreAuthorize("isAuthenticated()")
    @Query("SELECT o FROM Order o where o.user.username = ?#{principal.username}")
    List<Order> getOrdersOfCurrentUser(@SortDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable);
    @PreAuthorize("isAuthenticated() and #_order.getUser().username == principal.username")
    <O extends Order> O save(@NonNull O _order);
    @PreAuthorize("isAuthenticated() and orderRepository.getOrdersOfCurrentUser(null).contains(new me.yusuf.ecommerce_builder.demo.domain.order.Order(#_id))")
    @Query("UPDATE Order o SET o.status = 'CANCELED' where o.id = :_id")
    @Modifying
    void cancelById(@NonNull @Param("_id") Integer _id);
    @Query("SELECT count (o) > 0 from Order o where o.id = :_id and o.user.username = :#{principal.username}")
    boolean belongsToCurrentUserById(@NonNull @Param("_id") Integer _id);
    @PostAuthorize("isAuthenticated() and returnObject.userId == T(me.yusuf.ecommerce_builder.demo.domain.user.User).cast(principal)?.id")
    Order findById(Integer id);
}
