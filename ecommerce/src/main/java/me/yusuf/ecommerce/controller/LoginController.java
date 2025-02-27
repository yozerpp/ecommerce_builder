package me.yusuf.ecommerce.controller;

import me.yusuf.ecommerce.domain.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.security.auth.login.LoginException;

@Controller("loginController")
@RequestMapping
public class LoginController extends ControllerBase{

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @PostMapping("/login")
    public String login(Model model, @RequestParam String username, @RequestParam String password, @RequestParam(name = "continue", defaultValue = "/user") String cont){
        try {
            userService.login(username,password);
            return "redirect:" + cont;
        } catch (LoginException e) {
            model.addAttribute("error", e.getMessage());
            return null;
        }
    }
    @PostMapping("/logout")
    public String logout(){
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "redirect:/home";
    }
}
