package me.yusuf.ecommerce_builder.demo.service;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce_builder.demo.domain.user.LoginForm;
import me.yusuf.ecommerce_builder.demo.domain.user.RegistrationForm;
import me.yusuf.ecommerce_builder.demo.domain.user.User;
import me.yusuf.ecommerce_builder.demo.domain.user.UserRepository;
import me.yusuf.ecommerce_builder.demo.security.UserAuthService;
import me.yusuf.ecommerce_builder.demo.utils.Utils;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodMetadataAnn;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    
    @MethodMetadataAnn(methodFriendlyName = "Kullanıcı Getir")
    @PreAuthorize("!isAnonymous()")
    public User getCurrentUser(){
         return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    
    @MethodMetadataAnn(methodFriendlyName = "Giriş Form")
    public Map<String, Map.Entry<String,Boolean>> getLoginForm(Model model){
        return Utils.propertyMap(LoginForm.class);
    }
    
    @MethodMetadataAnn(methodFriendlyName = "Giriş Yap")
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
    
    @MethodMetadataAnn(methodFriendlyName = "Kullanıcı Güncelle")
    public void updateUser(@RequestBody User user){
        userRepository.save(user);
    }
    
    @MethodMetadataAnn(methodFriendlyName = "Kayıt Form")
    @PreAuthorize("isAnonymous()")
    public Map<String, Map.Entry<String, Boolean>> getSignUpForm(){
        return Utils.propertyMap(RegistrationForm.class);
    }
    
    @MethodMetadataAnn(methodFriendlyName = "Kayıt Ol")
    @PreAuthorize("isAnonymous()")
    public void signUp(User user){
        userRepository.save(user);
    }
}
