package cb.task.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_order")
public class ProductOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column
    ProductOrderStatus status;

    @ManyToOne()
    @JoinColumn(foreignKey = @ForeignKey(name = "user_fk"), name = "user_id")
    AppUser user;

    @ManyToOne()
    @JoinColumn(foreignKey = @ForeignKey(name = "delivery_point_fk"), name = "delivery_point_id")
    DeliveryPoint deliveryPoint;
}
