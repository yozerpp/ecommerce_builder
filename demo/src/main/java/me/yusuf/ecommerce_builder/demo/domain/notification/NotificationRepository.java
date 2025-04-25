package me.yusuf.ecommerce_builder.demo.domain.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface NotificationRepository extends org.springframework.data.repository.CrudRepository<Notification, Long>{
    @Query("SELECT n FROM Notification n where n.userId = :#{T(me.yusuf.ecommerce_builder.demo.domain.user.User).cast(principal).getId()} and n.read = :is_read")
    @NonNull Page<Notification> getNotificationsOfCurrentUser(@Param("is_read")  boolean read, Pageable pageable);
    @Query("INSERT INTO Notification (userId,message,type) values (:userid, :msg, :tp)")
    @Modifying
    @NonNull Notification send(@NonNull @Param("userid") Integer userId, @NonNull @Param("msg") Map<String,Object> message,@Param("tp") @NonNull Notification.NotificationType type);
}
