package me.yusuf.ecommerce_builder.demo.domain.session;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RepositoryRestResource(exported = false)
@Repository("sessionRepository")
public interface SessionRepository extends org.springframework.data.repository.CrudRepository<Session, String>{
public Optional<Session> findWithCartById(String token);
}
