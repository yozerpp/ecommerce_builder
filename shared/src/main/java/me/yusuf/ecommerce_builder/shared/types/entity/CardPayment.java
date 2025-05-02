package me.yusuf.ecommerce_builder.shared.types.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "card_payment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardPayment extends Payment {

    @Column(name = "code")
    @Size(max = 255)
    private String code;

    @Column(name = "transdate", columnDefinition = "timestamptz")
    private Instant transDate;

    @NotNull
    @Size(max = 255)
    @Column(name = "processor", nullable = false)
    private String processor;

    @NotNull
    @Size(max = 255)
    @Column(name = "processor_trans_id", nullable = false)
    private String processorTransId;

    @NotNull
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "cc_num")
    @Size(max = 255)
    private String creditCardNumber;

    @Column(name = "cc_type")
    @Size(max = 255)
    private String creditCardType;

    @Column(name = "response", columnDefinition = "text")
    private String response;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CardPayment that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getCode(), that.getCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCode());
    }
}