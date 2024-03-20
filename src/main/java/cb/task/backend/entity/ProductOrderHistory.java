package cb.task.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_order_history")
public class ProductOrderHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Enumerated(EnumType.STRING)
    @Column
    ProductOrderStatus status;

    @Column(name = "datetime")
    LocalDateTime dateTime;

    @ManyToOne()
    @JoinColumn(foreignKey = @ForeignKey(name = "order_fk"), name = "product_order_id")
    ProductOrder order;
}
