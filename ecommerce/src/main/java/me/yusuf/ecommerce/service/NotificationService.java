package me.yusuf.ecommerce.service;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce.domain.ServiceBase;
import me.yusuf.ecommerce.domain.notification.Notification;
import me.yusuf.ecommerce.domain.notification.NotificationRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import me.yusuf.ecommerce_builder.shared.MethodMetadata;

import java.util.List;
import java.util.Map;
@Service
@PreAuthorize("!isAnonymous()")
public class NotificationService extends ServiceBase {
    private NotificationRepository notificationRepository;

    public NotificationService(ApplicationContext context, EntityManager entityManager, NotificationRepository notificationRepository) {
        super(entityManager);
        this.notificationRepository = notificationRepository;
    }
    
    @MethodMetadata(name = "Bildirim Getir")
    public Page<Notification> notifications(@RequestParam(required = false, defaultValue = "false") boolean read,
                                             @RequestParam(required = false, defaultValue = "0") int page,
                                             @RequestParam(required = false, defaultValue = "20") int pageSize,
                                             Model model, @RequestParam(required = false, defaultValue = "false") boolean fullPage){
        var pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
        return notificationRepository.getNotificationsOfCurrentUser(read, pageable);
    }
}
