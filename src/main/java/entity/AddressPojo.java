package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers_addresses")
public class AddressPojo implements Serializable {

    @Id
    @Column(name = "address_id")
    private Long address_id;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "state_us")
    private String state_us;

    @Column(name = "postal_code")
    private int postal_code;

    @Column(name = "country")
    private String country;
}
