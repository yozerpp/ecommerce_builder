package me.yusuf.ecommerce.domain.user;

import me.yusuf.ecommerce.domain.z_embeddable.Address;
import me.yusuf.ecommerce.domain.z_embeddable.PhoneNumber;

public interface RegistrationForm extends LoginForm{
    String getFirstName();
    String getLastName();
    PhoneNumber getPhoneNumber();
    Address getAddress();
}
