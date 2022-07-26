package entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPojo implements Serializable {

    @Id
    @Column(name = "product_id")
    private Long product_id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price_without_vat")
    private double price_without_vat;

    @Column(name = "price_with_vat")
    private double price_with_vat;

    @Column(name = "product_type")
    private String product_type;

}
