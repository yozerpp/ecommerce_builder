package me.yusuf.ecommerce.domain.session;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface SessionRepository extends org.springframework.data.repository.CrudRepository<Session, String>{
}
