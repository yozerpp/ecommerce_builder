package me.yusuf.ecommerce_builder.shared.types.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.Address;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.PhoneNumber;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.Versioned;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GeneratedColumn;

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
public class User extends Versioned {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Integer id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @GeneratedColumn("first_name || ' ' || last_name")
    @Column(name = "full_name", nullable = false, updatable = false, insertable = false)
    private String fullName;

    @Email
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    private PhoneNumber phoneNumber;

    @OneToOne(fetch = FetchType.EAGER, mappedBy = "user")
    @JoinColumn(name = "id", referencedColumnName = "user_id")
    private Seller seller;

    @Embedded
    private Address address;

    @ColumnDefault("false")
    @Column(name = "phone_number_public", nullable = false, insertable = false)
    private boolean phoneNumberAccessAllowed;

    @ColumnDefault("false")
    @Column(name = "username_public", nullable = false, insertable = false)
    private boolean usernameAccessAllowed;

    @ColumnDefault("false")
    @Column(name = "address_public", nullable = false, insertable = false)
    private boolean addressAccessAllowed;

    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @ColumnDefault(Versioned.CURRENT_TIME_MILIS_SQL)
    @Column(name = "last_password_update", insertable = false, nullable = false)
    private long lastPasswordUpdate;

    @Lob
    @Column(name = "profile_image")
    private byte[] profileImage;

    @ColumnDefault("true")
    @Column(name = "enabled", nullable = false, insertable = false, columnDefinition = "boolean")
    private boolean enabled;

    @ColumnDefault("false")
    @Column(name = "email_verified", nullable = false, columnDefinition = "boolean")
    private boolean emailVerified;

    @ColumnDefault("false")
    @Column(name = "phone_verified", nullable = false, columnDefinition = "boolean")
    private boolean phoneVerified;

    @Column(name = "active_session")
    private String activeSession;

    @Column(name = "last_login", columnDefinition = "timestamptz")
    private Instant lastLogin;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Order> orders = new HashSet<>();

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "active_session", referencedColumnName = "id", insertable = false, updatable = false)
    private Session activeSessionRef;

    @ManyToMany
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities = new HashSet<>();

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
