package cb.task.backend.repository;

import cb.task.backend.entity.ProductOrder;
import cb.task.backend.entity.ProductOrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderHistoryRepository extends JpaRepository<ProductOrderHistory, Long> {

    List<ProductOrderHistory> findAllByOrder(ProductOrder productOrder);

}
