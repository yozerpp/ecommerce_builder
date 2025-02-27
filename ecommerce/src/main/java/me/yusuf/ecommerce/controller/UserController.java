package me.yusuf.ecommerce.controller;

import me.yusuf.ecommerce.domain.user.User;
import me.yusuf.ecommerce.domain.user.UserRepository;
import me.yusuf.ecommerce.domain.user.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@Controller("uiController")
@PreAuthorize("!isAnonymous()")
@org.springframework.web.bind.annotation.RequestMapping({"/user"})
public class UserController extends ControllerBase{

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }
    @GetMapping
    public String userPage(Model model){
        var user = userService.getCurrentUser();
        model.addAttribute("user", user);
        model.addAttribute("orders", user.getOrders());
        if(user.getSeller()!=null){
            model.addAttribute("products", user.getSeller().getProductOffers());
            model.addAttribute("shipments", user.getSeller().getShipments());
        }
        return "user";
    }
    @PutMapping
    public void putUSer(@RequestBody User user, Model model){
        try {
            userService.updateUser(user);
            model.addAttribute("message",Map.of("text", "User updated successfully", "success", true));
        } catch (Exception e){
            model.addAttribute("message",Map.of("text", "User update failed: " + e.getLocalizedMessage(), "success", false));
        }
    }
    @DeleteMapping
    public void deleteUser(Model model){
        try {
            userRepository.deleteCurrentUser();
            model.addAttribute("message",Map.of("text", "User deleted successfully", "success", true));
        } catch (Exception e){
            model.addAttribute("message",Map.of("text", "User delete failed: " + e.getLocalizedMessage(), "success", false));
        }
    }

}
