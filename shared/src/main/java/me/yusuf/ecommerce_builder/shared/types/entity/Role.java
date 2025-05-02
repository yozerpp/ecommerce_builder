package me.yusuf.ecommerce_builder.shared.types.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {
    public static final Role ADMIN = new Role("ROLE_ADMIN",null);
    public static final Role USER = new Role("ROLE_USER",null);
    public static final Role SELLER = new Role("ROLE_SELLER",null);
    public static final Role STAFF = new Role("ROLE_STAFF",null);
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(label = "id", nullable = false, updatable = false)
//    private Integer id;
    @NotNull
    @Column(name = "label", nullable = false, unique = true, length = 50)
    private String authority;

    @Column(name = "description", length = 255)
    private String description;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Role role)) return false;
        return Objects.equals(getAuthority(), role.getAuthority());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getAuthority());
    }
}