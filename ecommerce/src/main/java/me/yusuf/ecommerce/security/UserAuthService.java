package me.yusuf.ecommerce.security;

import me.yusuf.ecommerce.domain.role.Role;
import me.yusuf.ecommerce.domain.seller.SellerRepository;
import me.yusuf.ecommerce.domain.user.User;
import me.yusuf.ecommerce.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAuthService implements UserDetailsManager {
    UserRepository userRepository;
    User user;
    @Autowired
    public UserAuthService(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    @Transactional
    @jakarta.transaction.Transactional
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        this.user= userRepository.findUserByUsername(username);
        if(this.user==null)
            throw new UsernameNotFoundException("User not found for username:" + username);
        return this.user;
    }

    @Override
    public void createUser(UserDetails user) {
        if(User.class.isAssignableFrom(user.getClass()))
            userRepository.save((User) user);
        else throw new RuntimeException("Unexpected type:" + user.getClass().getName());
    }

    @Override
    public void updateUser(UserDetails user) {
        if(User.class.isAssignableFrom(user.getClass()))
            userRepository.save((User) user);
        else throw new RuntimeException("Unexpected type:" + user.getClass().getName());
    }

    @Override
    public void deleteUser(String username) {
        if(this.user.getUsername().equals(username))
            this.userRepository.deleteCurrentUser();
        else throw new RuntimeException("Unexpected type:" + user.getClass().getName());
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        if(this.user.getPassword().equals(oldPassword))
            this.userRepository.updatePasswordOfCurrentUser(newPassword);
        else throw new RuntimeException("Unexpected type:" + user.getClass().getName());
    }

    @Override
    public boolean userExists(String username) {
        return this.userRepository.existsUserByUsername(username);
    }
}
