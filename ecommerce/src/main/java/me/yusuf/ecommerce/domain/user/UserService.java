package me.yusuf.ecommerce.domain.user;

import jakarta.persistence.EntityManager;
import me.yusuf.ecommerce.security.UserAuthService;
import me.yusuf.ecommerce.utils.Utils;
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
    @PreAuthorize("!isAnonymous()")
    public User getCurrentUser(){
         return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public Map<String, Map.Entry<String,Boolean>> getLoginForm(Model model){
        return Utils.propertyMap(LoginForm.class);
    }
    @PreAuthorize("isAnonymous()")
    public void login(@RequestParam String username,@RequestParam String password) throws LoginException {
        User user;
        if(!userAuthService.userExists(username))
            throw new LoginException( "You have entered invalid username or password");
        user = userAuthService.loadUserByUsername(username);
        if (!user.isEnabled()) {
            throw new LoginException("Your account is disabled, please contact administration to re-enable it before logging in");
        }
        if (!user.getPassword().equals(password)) {
            throw new LoginException( "You have entered invalid username or password");
        }
        user.eraseCredentials();
    }
    public void updateUser(@RequestBody User user){
        userRepository.save(user);
    }
    @PreAuthorize("isAnonymous()")
    public Map<String, Map.Entry<String, Boolean>> getSignUpForm(){
        return Utils.propertyMap(RegistrationForm.class);
    }
    @PreAuthorize("isAnonymous()")
    public void signUp(User user){
        userRepository.save(user);
    }

}
