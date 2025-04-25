package me.yusuf.ecommerce_builder.demo.domain.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.yusuf.ecommerce_builder.demo.domain.order.Order;
import me.yusuf.ecommerce_builder.demo.domain.role.Role;
import me.yusuf.ecommerce_builder.demo.domain.seller.Seller;
import me.yusuf.ecommerce_builder.demo.domain.session.Session;
import me.yusuf.ecommerce_builder.demo.domain.z_embeddable.Versioned;
import me.yusuf.ecommerce_builder.demo.domain.z_embeddable.Address;
import me.yusuf.ecommerce_builder.demo.domain.z_embeddable.PhoneNumber;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GeneratedColumn;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends Versioned implements RegistrationForm,UserDetails, CredentialsContainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    
    @NotNull
    @Column(name = "first_name", nullable = false, length = 255)
    private String firstName;
    
    @NotNull
    @Column(name = "last_name", nullable = false, length = 255)
    private String lastName;
    
    @GeneratedColumn("first_name || ' ' || last_name")
    @Column(name = "full_name",nullable = false, updatable = false,insertable = false, length = 255)
    private String fullName;
    
    @Email
    @Column(name = "username", nullable = false, unique = true, length = 255)
    @Getter(AccessLevel.NONE)
    private String username;
    
    @Getter(AccessLevel.NONE)
    private PhoneNumber phoneNumber;
    
    @OneToOne(optional = true,fetch = FetchType.EAGER, mappedBy = "user")
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private Seller seller;
    @Embedded
    public PhoneNumber getPhoneNumber() throws AccessDeniedException{
        try {
            if(!phoneNumberAccessAllowed && !((UserDetails)SecurityContextHolder.getContext().getAuthentication()).getUsername().equals( this.username))
                throw new AccessDeniedException("");
        } catch (NullPointerException e) {
            throw new AccessDeniedException("");
        }
        return this.phoneNumber;
    }
    public String getUsername() throws AccessDeniedException{
        try {
            if(!usernameAccessAllowed && !((UserDetails)SecurityContextHolder.getContext().getAuthentication()).getUsername().equals( this.username))
                throw new AccessDeniedException("");
        } catch (NullPointerException e) {
            throw new AccessDeniedException("");
        }
        return this.username;
    }
    @Embedded
    public Address getAddress() throws AccessDeniedException{
        if (Boolean.getBoolean("test.env")) return this.address;
        try {
            if(!addressAccessAllowed && !((UserDetails)SecurityContextHolder.getContext().getAuthentication()).getUsername().equals( this.username))
                throw new AccessDeniedException("");
        } catch (NullPointerException e) {
            throw new AccessDeniedException("");
        }
        return this.address;
    }
    
    @Getter(AccessLevel.NONE)
    private Address address;
    
    @ColumnDefault("false")
    @Column(name = "phone_number_public", nullable = false, insertable = false)
    private boolean phoneNumberAccessAllowed;
    
    @ColumnDefault("false")
    @Column(name = "username_public", nullable = false,insertable = false)
    private boolean usernameAccessAllowed;
    
    @ColumnDefault("false")
    @Column(name = "address_public", nullable = false,insertable = false)
    private boolean addressAccessAllowed;
    @NotNull
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    @NotNull
    @ColumnDefault(Versioned.CURRENT_TIME_MILIS_SQL)
    @Column(name = "last_password_update",insertable = false, nullable = false)
    private long lastPasswordUpdate;
    @NotNull
    @Override
    @Transient
    public boolean isCredentialsNonExpired() {
        return  lastPasswordUpdate - System.currentTimeMillis() < 60L * 60L * 1000L*24L * 30L;
    }
    @Override
    @Transient
    public boolean isAccountNonExpired() {
        return lastLogin!=null?
                lastLogin.isAfter(Instant.now().minusSeconds(60 * 60* 24 * 365))
                :this.getCreatedAt() - System.currentTimeMillis() < 60L * 60L * 1000L*24L * 3L;
    }
    @Override
    @Transient
    public boolean isAccountNonLocked() {
        return enabled;
    }
    @Lob
    @Column(name = "profile_image")
    private byte[] profileImage;
    @ColumnDefault("true")
    @Column(name = "enabled", nullable = false,insertable = false, columnDefinition = "boolean")
    private boolean enabled;
    @ColumnDefault("false")
    @Column(name = "email_verified", nullable = false, columnDefinition = "boolean")
    private boolean emailVerified;
    @ColumnDefault("false")
    @Column(name = "phone_verified", nullable = false, columnDefinition = "boolean")
    private boolean phoneVerified;
    @Column(name = "active_session", length = 255)
    private String activeSession;
    @Column(name = "last_login",columnDefinition = "timestamptz")
    private Instant lastLogin;
    @Getter(AccessLevel.NONE)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Order> orders;
    @OneToOne(fetch = FetchType.LAZY)
    @Getter(AccessLevel.NONE)
    @JoinColumn(name = "active_session", referencedColumnName = "id", insertable = false, updatable = false)
    private Session activeSessionRef;

    public Session getActiveSessionRef() {
        if(Boolean.getBoolean("test.env")) return this.activeSessionRef;
        try {
            if(!((UserDetails)SecurityContextHolder.getContext().getAuthentication()).getUsername().equals( this.username))
                throw new AccessDeniedException("");
        } catch (NullPointerException e) {
            throw new AccessDeniedException("");
        }
        return this.activeSessionRef;
    }

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities = new HashSet<>();

    public Set<Order> getOrders() {
        try {
            if(!((UserDetails)SecurityContextHolder.getContext().getAuthentication()).getUsername().equals( this.username))
                throw new AccessDeniedException("");
        } catch (NullPointerException e) {
            throw new AccessDeniedException("");
        }
        return this.orders;
    }

    @Override
    public void eraseCredentials() {

    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User user)) return false;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }
}