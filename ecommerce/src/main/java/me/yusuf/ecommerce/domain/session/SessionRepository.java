package me.yusuf.ecommerce.domain.session;

import me.yusuf.ecommerce.domain.cart.Cart;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RepositoryRestResource(exported = false)
@Repository("sessionRepository")
public interface SessionRepository extends org.springframework.data.repository.CrudRepository<Session, String>{
public Optional<Session> findWithCartById(String token);
}
