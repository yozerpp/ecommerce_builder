package me.yusuf.ecommerce_builder.shared.types.entity.embeddable;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
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
@AttributeOverrides({@AttributeOverride(name = "countryCode",column = @Column(name = "address_country_code"))})
public class Address {

    @NotNull
    @Size(max = 255)
    private String street;

    @NotNull
    @Size(max = 100)
    private String city;

    @NotNull
    @Size(max = 100)
    private String state;

    @NotNull
    @Size(max = 20)
    private String postalCode;

    @NotNull
    @Size(max = 2)
    private String countryCode;
}