package entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderPojo implements Serializable {

    @Id
    @Column(name = "order_id")
    private Long order_id;

    @Column(name = "customer_id")
    private int customer_id;

    @Column(name = "is_order_completed")
    private boolean is_order_completed;

    @Column(name = "is_order_payed")
    private boolean is_order_payed;

    @Column(name = "date_of_order")
    private Date date_of_orders;

    @Column(name = "date_order_completed")
    private Date date_order_completed;

    @ManyToMany
    @ToString.Exclude
    private List<ProductPojo> products;

}
