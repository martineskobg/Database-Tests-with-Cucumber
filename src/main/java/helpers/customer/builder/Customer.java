package helpers.customer.builder;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.sql.Date;


@Builder
@Getter
@ToString
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"address_id"})})
public class Customer {
    @NonNull
    private final int customerId;
    @NonNull
    private final String name;
    @NonNull
    private final String email;
    private String phone;
    @Builder.Default
    private int age = 99;
    @NonNull
    @OneToOne
    @Column(unique=true)
    private  Long addressId;
    private final boolean gdpr;
    private final boolean isProfileActive;
    private Date profileCreated;
    private Date profileDeactivated;
    private String deactivationReason;
    private String note;

    // Custom builder for Required Parameters
    public static CustomerBuilder builder(final String name,
                                          final String email,
                                          final Long addressId,
                                          final boolean gdpr,
                                          final boolean isProfileActive) {

        return new CustomerBuilder()
                .name(name)
                .email(email)
                .addressId(addressId)
                .gdpr(gdpr).
                isProfileActive(isProfileActive);
    }
}
