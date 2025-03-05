package me.yusuf.ecommerce.controller;

import me.yusuf.ecommerce.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/notification")
public class NotificationController extends ControllerBase {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public Map<String, Object> notifications(@RequestParam(required = false, defaultValue = "false") boolean read,
                                               @RequestParam(required = false, defaultValue = "0") int page,
                                               @RequestParam(required = false, defaultValue = "20") int pageSize,
                                               @RequestParam(required = false, defaultValue = "false") boolean fullPage){
        var notifications = this.notificationService.notifications(read, page, pageSize, null, fullPage);
        Map<String, Object> response = new HashMap<>();
        response.put("notifications", notifications.getContent());
        response.put("num_notifications", notifications.getTotalElements());
        response.put("page", notifications.getNumber());
        return response;
    }
}
