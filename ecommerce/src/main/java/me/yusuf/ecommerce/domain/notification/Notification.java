package me.yusuf.ecommerce.domain.notification;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import me.yusuf.ecommerce.domain.user.User;
import me.yusuf.ecommerce.domain.z_embeddable.Versioned;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.hibernate.validator.constraints.URL;
import org.springframework.lang.NonNull;

import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Notification extends Versioned {
    public Notification(Integer id){
        this.id =id;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Transient
    private String imageUrl;
    @URL
    @Column(name = "link",nullable = true)
    private String link;
    @NotNull
    @Column(name= "user_id",updatable = false,insertable = false)
    private Integer userId;
    @Column(name = "is_read")
    private boolean read;
    @NonNull
    @Column(name= "title")
    private String title;
    @NonNull
    @Column(name = "message", columnDefinition = "jsonb")
    private String message;
    @NotNull
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;
    public static enum NotificationType{
        DEFAULT
    }
}
