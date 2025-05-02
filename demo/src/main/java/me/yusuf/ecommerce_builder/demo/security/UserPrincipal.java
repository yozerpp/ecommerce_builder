package me.yusuf.ecommerce_builder.demo.security;

import me.yusuf.ecommerce_builder.shared.types.entity.User;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;

public class UserPrincipal implements UserDetails, CredentialsContainer {
  private final User user;
  public UserPrincipal(User user) {
    this.user = user;
  }
  /** Expose the underlying JPA entity */
  public User getUser() {
    return user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getAuthorities();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    Instant lastLogin = user.getLastLogin();
    return lastLogin != null
        ? lastLogin.isAfter(Instant.now().minusSeconds(60 * 60 * 24 * 365))
        : (user.getCreatedAt() - System.currentTimeMillis()) < 60L * 60 * 24 * 3 * 1000;
  }

  @Override
  public boolean isAccountNonLocked() {
    return user.isEnabled();
  }

  @Override
  public boolean isCredentialsNonExpired() {
    long age = System.currentTimeMillis() - user.getLastPasswordUpdate();
    return age < 60L * 60 * 24 * 30 * 1000;
  }

  @Override
  public boolean isEnabled() {
    return user.isEnabled();
  }

  @Override
  public void eraseCredentials() {
    // Optionally null out user.password here
  }
}
