package me.yusuf.ecommerce_builder.demo.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class AuthenticationManagerImpl  implements AuthenticationManager {
    private final UserAuthService userAuthService;
    public AuthenticationManagerImpl(UserAuthService userAuthService) {
        this.userAuthService = userAuthService;
    }
    @Override
    public UserPrincipal authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication instanceof UsernamePasswordAuthenticationToken unp) {
            if (userAuthService.userExists((String) unp.getPrincipal())) {
                var user = userAuthService.loadUserByUsername((String) unp.getPrincipal());
                if (user.getPassword().equals(unp.getCredentials())){
                    if (!user.isEnabled()) throw new DisabledException("Account is disabled.");
                    return user;
                }
            }
            throw new BadCredentialsException("invalid username or password");
        } else throw new IllegalArgumentException("Only username-password authentication is supported. Received: " + authentication.getClass().getName());
    }
}
