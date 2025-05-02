package me.yusuf.ecommerce_builder.demo.domain.network;

import me.yusuf.ecommerce_builder.shared.types.dto.RegistrationForm;
import me.yusuf.ecommerce_builder.shared.types.entity.User;
import me.yusuf.ecommerce_builder.demo.domain.repository.UserRepository;
import me.yusuf.ecommerce_builder.demo.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static me.yusuf.ecommerce_builder.demo.domain.network.ControllerBase.basePath;

@RestController
@RequestMapping(basePath+"/user")
public class UserController extends ControllerBase {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @PreAuthorize("!isAnonymous()")
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
    @PostMapping
    ResponseEntity<?> registerUser( @RequestBody RegistrationForm.Impl registrationForm){
        try {
            userService.signUp(registrationForm);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } return ResponseEntity.ok().build();
    }
    @PreAuthorize("!isAnonymous()")
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
    @PreAuthorize("!isAnonymous()")
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
