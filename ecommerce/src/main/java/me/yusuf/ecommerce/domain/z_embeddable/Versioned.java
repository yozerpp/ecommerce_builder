package me.yusuf.ecommerce.domain.z_embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GeneratedColumn;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class Versioned{
    public static final String CURRENT_TIME_MILIS_SQL = "CAST(EXTRACT(EPOCH FROM CURRENT_TIMESTAMP) * 1000 AS BIGINT)";
    @Version
    protected Long version;
    @ColumnDefault(CURRENT_TIME_MILIS_SQL)
    @Generated
    @Column(name = "inserted_at", nullable = false, updatable = false, insertable = false)
    protected long createdAt;
    @Column(name = "updated_at",insertable = false)
    @LastModifiedDate
    protected Long updatedAt;
    @ColumnDefault(value = "false")
    @Column(name = "is_deleted",insertable = false, nullable = false)
    protected boolean deleted;
    @Column(name = "deleted_at",insertable = false, nullable = true)
    protected Long deletedAt;
}
