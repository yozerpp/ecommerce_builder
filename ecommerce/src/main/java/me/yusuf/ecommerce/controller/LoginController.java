package me.yusuf.ecommerce.controller;

import me.yusuf.ecommerce.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class LoginController extends ControllerBase {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public Map<String, Object> loginPage(){
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Please use POST /login with username and password to login.");
        return response;
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestParam String username,
                                     @RequestParam String password,
                                     @RequestParam(name = "continue", defaultValue = "/user") String cont){
        Map<String, Object> response = new HashMap<>();
        try {
            userService.login(username, password);
            response.put("redirect", cont);
        } catch (LoginException e) {
            response.put("error", e.getMessage());
        }
        return response;
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(){
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Logged out successfully");
        return response;
    }
}
