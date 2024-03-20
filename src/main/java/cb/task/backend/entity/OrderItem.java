package cb.task.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column
    Integer quantity;

    @ManyToOne()
    @JoinColumn(foreignKey = @ForeignKey(name = "order_fk"), name = "order_id")
    ProductOrder order;

    @ManyToOne()
    @JoinColumn(foreignKey = @ForeignKey(name = "product_fk"), name = "product_id")
    Product product;
}
