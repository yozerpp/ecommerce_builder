package me.yusuf.ecommerce.domain.payment;

import org.springframework.data.repository.Repository;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PaymentRepository extends Repository<Payment, Long> {
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')") // NEED TO EXECUTE THIS BY AN ADMIN
    <P extends Payment> @NonNull P save(@NonNull P payment);
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    <P extends Payment> void delete(@NonNull P payment);

}
