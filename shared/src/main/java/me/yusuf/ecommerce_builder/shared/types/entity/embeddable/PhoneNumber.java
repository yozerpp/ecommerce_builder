package me.yusuf.ecommerce_builder.shared.types.entity.embeddable;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverrides({@AttributeOverride(name = "countryCode",column = @Column(name = "phone_country_code")),
@AttributeOverride(name = "number", column = @Column(name = "phone_number"))})
public class PhoneNumber {

    @NotNull
    @Pattern(regexp = "\\+\\d{1,4}", message = "Invalid country code format")
    @Size(max = 5)
    private String countryCode;

    @NotNull
    @Pattern(regexp = "\\d{6,15}", message = "Invalid phone number format")
    @Size(max = 15)
    private String number;
}