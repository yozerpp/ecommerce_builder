package me.yusuf.ecommerce_builder.demo.controller;

import me.yusuf.ecommerce_builder.demo.domain.user.User;
import me.yusuf.ecommerce_builder.demo.domain.user.UserRepository;
import me.yusuf.ecommerce_builder.demo.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@PreAuthorize("!isAnonymous()")
public class UserController extends ControllerBase {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public Map<String, Object> userPage(){
        var user = userService.getCurrentUser();
        Map<String, Object> response = new HashMap<>();
        response.put("user", user);
        response.put("orders", user.getOrders());
        if(user.getSeller() != null){
            response.put("products", user.getSeller().getProductOffers());
            response.put("shipments", user.getSeller().getShipments());
        }
        return response;
    }

    @PutMapping
    public Map<String, Object> putUser(@RequestBody User user){
        Map<String, Object> response = new HashMap<>();
        try {
            userService.updateUser(user);
            response.put("message", "User updated successfully");
        } catch (Exception e){
            response.put("message", "User update failed: " + e.getLocalizedMessage());
        }
        return response;
    }

    @DeleteMapping
    public Map<String, Object> deleteUser(){
        Map<String, Object> response = new HashMap<>();
        try {
            userRepository.deleteCurrentUser();
            response.put("message", "User deleted successfully");
        } catch (Exception e){
            response.put("message", "User delete failed: " + e.getLocalizedMessage());
        }
        return response;
    }
}
