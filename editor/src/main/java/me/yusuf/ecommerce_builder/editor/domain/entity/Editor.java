package me.yusuf.ecommerce_builder.editor.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.yusuf.ecommerce_builder.shared.types.conversion.JpaTypeConverter;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.Versioned;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GeneratedColumn;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Editor extends Versioned {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Email
    @Column(unique = true, nullable = false)
    private String username;
    private String password;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "middle_name", nullable = true)
    private String middleName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @GeneratedColumn("first_name || ' ' || middle_name || ' ' || last_name")
    @Column(name = "full_name", updatable = false, insertable = false)
    private String fullName;
//    @ColumnDefault("ARRAY[]::text[]")
//    @Column(name="modified_classes", nullable=false)
//    @ElementCollection(targetClass = Class.class, fetch = FetchType.EAGER)
//    @Convert(converter = JpaTypeConverter.class)
//    private Set<Class<?>> modifiedClasses = new HashSet<>();
//    @OneToMany(mappedBy = "editor", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
//    private List<Metamodel> metamodels = new ArrayList<>();
}
