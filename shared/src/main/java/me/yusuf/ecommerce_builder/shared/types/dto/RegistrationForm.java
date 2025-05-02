package me.yusuf.ecommerce_builder.shared.types.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.yusuf.ecommerce_builder.shared.types.entity.User;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.Address;
import me.yusuf.ecommerce_builder.shared.types.entity.embeddable.PhoneNumber;

public interface RegistrationForm extends LoginForm {
    String getFirstName();
    String getLastName();
    String getUsername();
    String getPassword();
    PhoneNumber getPhoneNumber();
    Address getAddress();
    default User toUser(){
        var ret = new User();
        ret.setId(null);
        ret.setFirstName(getFirstName());
        ret.setLastName(getLastName());
        ret.setUsername(getUsername());
        ret.setPassword(getPassword());
        ret.setPhoneNumber(getPhoneNumber());
        ret.setAddress(getAddress());
        return ret;
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public class Impl implements RegistrationForm{
        public String firstName,lastName,username,password;
        public Address address;
        public PhoneNumber phoneNumber;
    }
}
