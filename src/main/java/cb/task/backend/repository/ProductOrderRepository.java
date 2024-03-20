package cb.task.backend.repository;

import cb.task.backend.entity.AppUser;
import cb.task.backend.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    List<ProductOrder> findAllByUser(AppUser user);

}
