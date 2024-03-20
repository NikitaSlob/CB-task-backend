package cb.task.backend.repository;

import cb.task.backend.entity.*;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;

@DataJpaTest
public class ProductOrderHistoryRepositoryTest {

    @Autowired
    ProductOrderHistoryRepository productOrderHistoryRepository;

    @Autowired
    ProductOrderRepository productOrderRepository;

    @Autowired
    DeliveryPointRepository deliveryPointRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void verifyRepository() {
        val deliveryPoint = DeliveryPoint.builder()
                .name("name")
                .address("address")
                .build();

        val user = AppUser.builder()
                .fullName("full name")
                .roles(null)
                .login("login")
                .password("password")
                .build();


        val productOrder = ProductOrder.builder()
                .deliveryPoint(deliveryPoint)
                .status(ProductOrderStatus.CREATED)
                .user(user)
                .build();

        deliveryPointRepository.save(deliveryPoint);
        userRepository.save(user);
        productOrderRepository.save(productOrder);

        val productOrderHistory = ProductOrderHistory.builder()
                .order(productOrder)
                .status(ProductOrderStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();

        Assertions.assertNull(productOrderHistory.getId());
        productOrderHistoryRepository.save(productOrderHistory);
        Assertions.assertNotNull(productOrderHistory.getId());
    }

    @Test
    void testProductOrderHistoryFindByOrderIdRequest() {
        val deliveryPoint = DeliveryPoint.builder()
                .name("name")
                .address("address")
                .build();

        val user = AppUser.builder()
                .fullName("full name")
                .roles(null)
                .login("login")
                .password("password")
                .build();


        val productOrder = ProductOrder.builder()
                .deliveryPoint(deliveryPoint)
                .status(ProductOrderStatus.CREATED)
                .user(user)
                .build();


        deliveryPointRepository.save(deliveryPoint);
        userRepository.save(user);
        productOrderRepository.save(productOrder);

        val orderItem1 = ProductOrderHistory.builder()
                .order(productOrder)
                .status(ProductOrderStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();

        val orderItem2 = ProductOrderHistory.builder()
                .order(productOrder)
                .status(ProductOrderStatus.CANCELED)
                .dateTime(LocalDateTime.now())
                .build();


        productOrderHistoryRepository.save(orderItem1);
        productOrderHistoryRepository.save(orderItem2);

        val productOrderHistories = productOrderHistoryRepository.findAllByOrder(productOrder);
        Assertions.assertTrue(productOrderHistories.stream().anyMatch(history -> history.getStatus()
                .equals(ProductOrderStatus.CREATED)));
        Assertions.assertTrue(productOrderHistories.stream().anyMatch(history -> history.getStatus()
                .equals(ProductOrderStatus.CANCELED)));

    }
}
