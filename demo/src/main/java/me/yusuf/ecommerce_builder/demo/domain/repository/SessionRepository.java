package me.yusuf.ecommerce_builder.demo.domain.repository;

import me.yusuf.ecommerce_builder.shared.types.entity.Session;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("sessionRepository")
public interface SessionRepository extends org.springframework.data.repository.CrudRepository<Session, String>{
Optional<Session> findWithCartById(String token);
}
