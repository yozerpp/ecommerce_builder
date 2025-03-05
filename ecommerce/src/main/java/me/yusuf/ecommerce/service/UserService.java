package me.yusuf.ecommerce.service;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce.domain.user.LoginForm;
import me.yusuf.ecommerce.domain.user.RegistrationForm;
import me.yusuf.ecommerce.domain.user.User;
import me.yusuf.ecommerce.domain.user.UserRepository;
import me.yusuf.ecommerce.security.UserAuthService;
import me.yusuf.ecommerce.utils.Utils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import me.yusuf.ecommerce_builder.shared.MethodMetadata;

import javax.security.auth.login.LoginException;
import java.util.Map;

@Service
public class UserService {
    private final UserAuthService userAuthService;
    UserRepository userRepository;
    public UserService(EntityManager entityManager, UserRepository userRepository, UserAuthService userAuthService) {
        this.userRepository = userRepository;
        this.userAuthService = userAuthService;
    }
    
    @MethodMetadata(name = "Mevcut")
    @PreAuthorize("!isAnonymous()")
    public User getCurrentUser(){
         return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    @MethodMetadata(name = "Giriş")
    public Map<String, Map.Entry<String,Boolean>> getLoginForm(Model model){
        return Utils.propertyMap(LoginForm.class);
    }
    
    @MethodMetadata(name = "Giriş")
    @PreAuthorize("isAnonymous()")
    public void login(@RequestParam String username, @RequestParam String password) throws LoginException {
        User user;
        if(!userAuthService.userExists(username))
            throw new LoginException("You have entered invalid username or password");
        user = userAuthService.loadUserByUsername(username);
        if (!user.isEnabled()) {
            throw new LoginException("Your account is disabled, please contact administration to re-enable it before logging in");
        }
        if (!user.getPassword().equals(password)) {
            throw new LoginException("You have entered invalid username or password");
        }
        user.eraseCredentials();
    }
    
    @MethodMetadata(name = "Güncelle")
    public void updateUser(@RequestBody User user){
        userRepository.save(user);
    }
    
    @MethodMetadata(name = "KayıtForm")
    @PreAuthorize("isAnonymous()")
    public Map<String, Map.Entry<String, Boolean>> getSignUpForm(){
        return Utils.propertyMap(RegistrationForm.class);
    }
    
    @MethodMetadata(name = "Kayıt")
    @PreAuthorize("isAnonymous()")
    public void signUp(User user){
        userRepository.save(user);
    }
}
