package me.yusuf.ecommerce_builder.demo.domain.service;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce_builder.shared.types.entity.Notification;
import me.yusuf.ecommerce_builder.demo.domain.repository.NotificationRepository;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodInfo;
import me.yusuf.ecommerce_builder.shared.types.tuple.Tuple2;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@PreAuthorize("!isAnonymous()")
public class NotificationService extends ServiceBase {
    private NotificationRepository notificationRepository;

    public NotificationService( NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }
    
    @MethodInfo(userFriendlyName = "Bildirim Getir")
    public Page<Notification> notifications(@RequestParam(required = false, defaultValue = "false") boolean read,
                                                   @RequestParam(required = false, defaultValue = "0") int page,
                                                   @RequestParam(required = false, defaultValue = "20") int pageSize,
                                                   Model model, @RequestParam(required = false, defaultValue = "false") boolean fullPage){
        var pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        return notificationRepository.getNotificationsOfCurrentUser(read, pageable);
    }
}
