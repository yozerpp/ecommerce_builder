package me.yusuf.ecommerce.controller;

import me.yusuf.ecommerce.domain.notification.Notification;
import me.yusuf.ecommerce.domain.notification.NotificationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
@RequestMapping("/notification")
@Controller("notificationController")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }
    @GetMapping
    public String notifications(@RequestParam(required = false, defaultValue = "false") boolean read,
                                @RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = "20") int pageSize,
                                Model model, @RequestParam(required = false, defaultValue = "false") boolean fullPage){
        var notifications =this.notificationService.notifications(read, page, pageSize, model, fullPage);
        model.addAttribute("notifications", notifications.getContent());
        model.addAttribute("num_notifications", notifications.getTotalElements());
        model.addAttribute("page", notifications.getNumber());
        return "fragments/sides/notifications";
    }
}
