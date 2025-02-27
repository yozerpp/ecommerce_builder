package me.yusuf.ecommerce.controller;

import me.yusuf.ecommerce.domain.order.Order;
import me.yusuf.ecommerce.domain.order.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.Nullable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("orderController")
@RequestMapping("/order")
public class OrderController extends ControllerBase{
    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @GetMapping
    public String getOrders(@RequestParam Pageable pageable, Model model){
        var orders= orderService.getOrders(pageable);
        model.addAttribute("orders", orders);
        return "fragments/user/orders";
    }
    @GetMapping("/{id}")
    public @Nullable String getOrder(int id, Model model){
        var order = orderService.getOrder(id);
        if(order == null){
            model.addAttribute("message", "Order not found");
            return null;
        }
        model.addAttribute("orders", List.of(order));
        return "fragments/user/orders";
    }
    @PutMapping("/{id}")
    public void updateOrder(@RequestParam int id, @RequestParam Order newOrder, Model model){
        this.orderService.updateOrder(id, newOrder);
        model.addAttribute("message", "Order updated");
    }
    @PostMapping
    public void createOrder(Model model){
       var id= this.orderService.createOrder();
       model.addAttribute("message", createdMessage(id,"/order"));
    }
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable int id, Model model){
        try {
            this.orderService.cancelOrder(id);
            model.addAttribute("message", "Order cancelled");
        } catch (AccessDeniedException e) {
            model.addAttribute("message", e.getMessage());
        }
    }
}
