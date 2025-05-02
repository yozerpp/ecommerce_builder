package me.yusuf.ecommerce_builder.shared.types.plugin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;

/**
 * This is used in persistence and network layers. It is converted to {@link Plugin} to be used for compilation.
 */
@Entity(name = "Plugin")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PluginDto implements IPlugin, Serializable {
    @EmbeddedId
    IPlugin.Id id;
    @Embedded
    PluginMetadata metadata;
    @Embedded
    PluginSource source;
    @Embeddable
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PluginSource implements Serializable {
//        @JdbcTypeCode(SqlTypes.CLOB)
        @Column(name = "pseudo_code", nullable = false, columnDefinition = "text")
        @JsonIgnore
         String pseudoCode;
//        @JdbcTypeCode(SqlTypes.CLOB)
        @JsonIgnore
        @Column(name = "char_encoded", nullable = false, columnDefinition = "text")
         String charEncoded;
//        @JdbcTypeCode(SqlTypes.BLOB)
        @Column(name = "byte_encoded", nullable = false, columnDefinition = "bytea")
         byte[] byteEncoded;
    }
}
