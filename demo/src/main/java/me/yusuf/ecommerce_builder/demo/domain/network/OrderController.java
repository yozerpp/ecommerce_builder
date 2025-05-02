package me.yusuf.ecommerce_builder.demo.domain.network;

import me.yusuf.ecommerce_builder.shared.types.entity.Order;
import me.yusuf.ecommerce_builder.demo.domain.service.OrderService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static me.yusuf.ecommerce_builder.demo.domain.network.ControllerBase.basePath;

@RestController
@RequestMapping(basePath+"/order")
public class OrderController extends ControllerBase {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<?> getOrders(Pageable pageable){
        var orders = orderService.getOrders(pageable);
        return ResponseEntity.ok(Map.of("orders", orders));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable int id){
        var order = orderService.getOrder(id);
        if(order == null){
            return ResponseEntity.status(404).body(Map.of("message", "Order not found"));
        }
        return ResponseEntity.ok(Map.of("orders", List.of(order)));
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateOrder(@RequestParam int id, @RequestParam Order newOrder){
        orderService.updateOrder(id, newOrder);
        return Map.of("message", "Order updated");
    }

    @PostMapping
    public Map<String, Object> createOrder(){
       var id = orderService.createOrder()._1().getId();
       return Map.of("message", createdMessage(id, "/order"));
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteOrder(@PathVariable int id){
        Map<String, Object> response = new java.util.HashMap<>();
        try {
            orderService.cancelOrder(id);
            response.put("message", "Order cancelled");
        } catch (AccessDeniedException e) {
            response.put("message", e.getMessage());
        }
        return response;
    }
}
