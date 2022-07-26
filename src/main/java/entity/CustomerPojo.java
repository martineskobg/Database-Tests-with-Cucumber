package entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "customers")
public class CustomerPojo implements Serializable {

    @Id
    @Column(name = "customer_id")
    private int customer_id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "age")
    private int age;

    @Column(name = "address_id")
    private Long address_id;

    @Column(name = "gdpr")
    private boolean gdpr;

    @Column(name = "is_profile_active")
    private boolean is_profile_active;

    @Column(name = "profile_created")
    private Date profile_created;

    @Column(name = "profile_deactivated")
    private Date profile_deactivated;

    @Column(name = "deactivation_reason")
    private String deactivation_reason;

    @Column(name = "note")
    private String note;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false)
    private AddressPojo addressPojo;

    @OneToMany
    private List<OrderPojo> orders;
}
