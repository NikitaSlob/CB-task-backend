package cb.task.backend.repository;

import cb.task.backend.entity.*;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class OrderItemRepositoryTest {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    ProductOrderRepository productOrderRepository;

    @Autowired
    DeliveryPointRepository deliveryPointRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

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

        val product = Product.builder()
                .name("name")
                .price(1)
                .build();


        productRepository.save(product);
        deliveryPointRepository.save(deliveryPoint);
        userRepository.save(user);
        productOrderRepository.save(productOrder);

        val orderItem = OrderItem.builder()
                .order(productOrder)
                .quantity(1)
                .product(product)
                .build();


        Assertions.assertNull(orderItem.getId());
        orderItemRepository.save(orderItem);
        Assertions.assertNotNull(orderItem.getId());
    }

    @Test
    void testProductOrderItemFindByOrderIdRequest() {
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

        val product1 = Product.builder()
                .name("name1")
                .price(1)
                .build();

        val product2 = Product.builder()
                .name("name2")
                .price(2)
                .build();


        productRepository.save(product1);
        productRepository.save(product2);
        deliveryPointRepository.save(deliveryPoint);
        userRepository.save(user);
        productOrderRepository.save(productOrder);

        val orderItem1 = OrderItem.builder()
                .order(productOrder)
                .quantity(1)
                .product(product1)
                .build();

        val orderItem2 = OrderItem.builder()
                .order(productOrder)
                .quantity(1)
                .product(product2)
                .build();


        orderItemRepository.save(orderItem1);
        orderItemRepository.save(orderItem2);

        val orderItems = orderItemRepository.findByOrderId(productOrder.getId());
        Assertions.assertTrue(orderItems.stream().anyMatch(orderItem -> orderItem.getProduct().getName().equals("name1")));
        Assertions.assertTrue(orderItems.stream().anyMatch(orderItem -> orderItem.getProduct().getName().equals("name2")));

    }

}
