package me.yusuf.ecommerce_builder.demo.security;

import me.yusuf.ecommerce_builder.shared.types.entity.User;
import me.yusuf.ecommerce_builder.demo.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAuthService implements UserDetailsManager {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    @jakarta.transaction.Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findUserByUsername(username);
        if (u == null) {
            throw new UsernameNotFoundException("User not found for username:" + username);
        }
        return new UserPrincipal(u);
    }

    @Override
    public void createUser(UserDetails user) {
        if (user instanceof UserPrincipal up) {
            userRepository.save(up.getUser());
        } else if (user instanceof User) {
            userRepository.save((User) user);
        } else {
            throw new RuntimeException("Unexpected type: " + user.getClass());
        }
    }

    @Override
    public void updateUser(UserDetails user) {
        if (user instanceof UserPrincipal up) {
            userRepository.save(up.getUser());
        } else if (user instanceof User) {
            userRepository.save((User) user);
        } else {
            throw new RuntimeException("Unexpected type: " + user.getClass());
        }
    }

    @Override
    public void deleteUser(String username) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedIn = auth.getName();
        if (!loggedIn.equals(username)) {
            throw new RuntimeException("Cannot delete other user");
        }
        UserPrincipal up = (UserPrincipal) auth.getPrincipal();
        userRepository.delete(up.getUser());
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal up = (UserPrincipal) auth.getPrincipal();
        if (!up.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Bad old password");
        }
        userRepository.updatePasswordOfCurrentUser(newPassword);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.existsUserByUsername(username);
    }
}
