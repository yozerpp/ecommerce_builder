package me.yusuf.ecommerce_builder.demo.domain.service;

import me.yusuf.ecommerce_builder.shared.types.dto.RegistrationForm;
import me.yusuf.ecommerce_builder.shared.types.entity.User;
import me.yusuf.ecommerce_builder.demo.domain.repository.UserRepository;
import me.yusuf.ecommerce_builder.demo.security.UserAuthService;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@Service
public class UserService extends ServiceBase {
    private final UserAuthService userAuthService;
    private final UserRepository userRepository;

    public UserService(
                       UserRepository userRepository,
                       UserAuthService userAuthService) {
        this.userRepository = userRepository;
        this.userAuthService = userAuthService;
    }

    @MethodInfo(userFriendlyName = "Giriş Yap")
    @PreAuthorize("isAnonymous()")
    public User login(@RequestParam String username,
                      @RequestParam String password) throws LoginException {
        User user;
        if (!userAuthService.userExists(username)) {
            throw new LoginException("You have entered invalid username or password");
        }
        user = userAuthService.loadUserByUsername(username).getUser();
        if (!user.isEnabled()) {
            throw new LoginException("Your account is disabled, please contact administration to re-enable it before logging in");
        }
        if (!user.getPassword().equals(password)) {
            throw new LoginException("You have entered invalid username or password");
        }
        return user;
    }

    @MethodInfo(userFriendlyName = "Kullanıcı Güncelle")
    public User updateUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @MethodInfo(userFriendlyName = "Kayıt Ol")
    @PreAuthorize("isAnonymous()")
    public User signUp(RegistrationForm.Impl form) {
        return userRepository.save(form.toUser());
    }
}
