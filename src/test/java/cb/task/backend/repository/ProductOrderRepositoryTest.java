package cb.task.backend.repository;

import cb.task.backend.entity.AppUser;
import cb.task.backend.entity.DeliveryPoint;
import cb.task.backend.entity.ProductOrder;
import cb.task.backend.entity.ProductOrderStatus;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductOrderRepositoryTest {

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

        deliveryPointRepository.save(deliveryPoint);
        userRepository.save(user);

        val productOrder = ProductOrder.builder()
                .deliveryPoint(deliveryPoint)
                .status(ProductOrderStatus.CREATED)
                .user(user)
                .build();

        Assertions.assertNull(productOrder.getId());
        productOrderRepository.save(productOrder);
        Assertions.assertNotNull(productOrder.getId());
    }

    @Test
    void testFindAllRequest() {
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


        val productOrder2 = ProductOrder.builder()
                .deliveryPoint(deliveryPoint)
                .status(ProductOrderStatus.CREATED)
                .user(user)
                .build();

        val productOrder1 = ProductOrder.builder()
                .deliveryPoint(deliveryPoint)
                .status(ProductOrderStatus.DELIVERED)
                .user(user)
                .build();

        deliveryPointRepository.save(deliveryPoint);
        userRepository.save(user);

        productOrderRepository.save(productOrder1);
        productOrderRepository.save(productOrder2);

        val productOrders = productOrderRepository.findAll();
        Assertions.assertTrue(productOrders.stream().anyMatch(order -> order.getStatus().equals(ProductOrderStatus.CREATED)));
        Assertions.assertTrue(productOrders.stream().anyMatch(order -> order.getStatus().equals(ProductOrderStatus.DELIVERED)));
    }

    @Test
    void testFindAllByUserRequest() {
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


        val productOrder2 = ProductOrder.builder()
                .deliveryPoint(deliveryPoint)
                .status(ProductOrderStatus.CREATED)
                .user(user)
                .build();

        val productOrder1 = ProductOrder.builder()
                .deliveryPoint(deliveryPoint)
                .status(ProductOrderStatus.DELIVERED)
                .user(user)
                .build();

        deliveryPointRepository.save(deliveryPoint);
        userRepository.save(user);

        productOrderRepository.save(productOrder1);
        productOrderRepository.save(productOrder2);

        val productOrders = productOrderRepository.findAllByUser(user);
        Assertions.assertTrue(productOrders.stream().anyMatch(order -> order.getStatus().equals(ProductOrderStatus.CREATED)));
        Assertions.assertTrue(productOrders.stream().anyMatch(order -> order.getStatus().equals(ProductOrderStatus.DELIVERED)));

    }

    @Test
    void testFindByIdRequest() {
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

        val newOrder = productOrderRepository.findById(productOrder.getId());
        Assertions.assertTrue(newOrder.isPresent());
        Assertions.assertEquals(newOrder.get().getId(), productOrder.getId());
    }

}
