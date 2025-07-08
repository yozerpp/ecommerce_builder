package me.yusuf.ecommerce_builder.demo.domain.service;

import jakarta.servlet.http.HttpServletRequest;
import me.yusuf.ecommerce_builder.demo.domain.network.filter.SessionHolder;
import me.yusuf.ecommerce_builder.demo.security.AuthenticationManagerImpl;
import me.yusuf.ecommerce_builder.demo.security.UserPrincipal;
import me.yusuf.ecommerce_builder.shared.types.dto.RegistrationForm;
import me.yusuf.ecommerce_builder.shared.types.entity.User;
import me.yusuf.ecommerce_builder.demo.domain.repository.UserRepository;
import me.yusuf.ecommerce_builder.shared.types.annotation.MethodInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.LoginException;

@Service
public class UserService extends ServiceBase {
    private final UserRepository userRepository;
    private final AuthenticationManagerImpl authenticationManager;
    public UserService(
            UserRepository userRepository, AuthenticationManagerImpl authenticationManager) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    @MethodInfo(userFriendlyName = "Giriş Yap")
    @PreAuthorize("isAnonymous()")
    public User login(@RequestParam String username,
                      @RequestParam String password, HttpServletRequest request) throws LoginException {
        var auth =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        SecurityContextHolder.getContext().setAuthentication(auth);
        request.getSession(true).setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        return  auth.getDetails();
    }

    @MethodInfo(userFriendlyName = "Kullanıcı Güncelle")
    public User updateUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @MethodInfo(userFriendlyName = "Kayıt Ol")
    @PreAuthorize("isAnonymous()")
    public User signUp(RegistrationForm.Impl form) {
        var u = form.toUser();
        var s = SessionHolder.getSession();
        u.setActiveSession(s.getId());
        u.setActiveSessionRef(s);
        return userRepository.save(u);
    }
}
