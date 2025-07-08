package me.yusuf.ecommerce_builder.demo.domain.repository;

import me.yusuf.ecommerce_builder.shared.types.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository("notificationRepository")
public interface NotificationRepository extends org.springframework.data.repository.CrudRepository<Notification, Long>{
    @Query("SELECT n FROM Notification n where n.userId = :#{T(me.yusuf.ecommerce_builder.shared.types.entity.User).cast(principal).getId()} and n.read = :is_read")
    @NonNull Page<Notification> getNotificationsOfCurrentUser(@Param("is_read")  boolean read, Pageable pageable);
}
