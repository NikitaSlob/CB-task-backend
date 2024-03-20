package cb.task.backend.service;

import cb.task.backend.entity.*;
import cb.task.backend.repository.OrderItemRepository;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class OrderItemServiceTest {

    @MockBean
    OrderItemRepository repository;

    @Autowired
    OrderItemService service;

    @Test
    void testSave() {
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

        val orderItem = OrderItem.builder()
                .id(1L)
                .order(productOrder)
                .quantity(1)
                .product(product)
                .build();

        Mockito.when(repository.save(orderItem)).thenReturn(orderItem);

        val savedItem = service.save(orderItem);

        Assertions.assertEquals(savedItem.getId(), orderItem.getId());
        Assertions.assertEquals(savedItem.getQuantity(), orderItem.getQuantity());
        Assertions.assertEquals(savedItem.getOrder().getId(), orderItem.getOrder().getId());

    }

    @Test
    void testSaveErrors() {
        Mockito.when(repository.save(null)).thenThrow(InvalidDataAccessApiUsageException.class);

        Assertions.assertThrowsExactly(InvalidDataAccessApiUsageException.class,() -> service.save(null));
    }


    @Test
    void testGetOrderItems() {
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
                .id(1L)
                .deliveryPoint(deliveryPoint)
                .status(ProductOrderStatus.CREATED)
                .user(user)
                .build();

        val product = Product.builder()
                .name("name")
                .price(1)
                .build();

        val product2 = Product.builder()
                .name("name2")
                .price(1)
                .build();

        val orderItem = OrderItem.builder()
                .id(1L)
                .order(productOrder)
                .quantity(1)
                .product(product)
                .build();

        val orderItem2 = OrderItem.builder()
                .id(2L)
                .order(productOrder)
                .quantity(1)
                .product(product2)
                .build();

        Mockito.when(repository.findByOrderId(orderItem.getOrder().getId())).thenReturn(List.of(orderItem, orderItem2));

        val items = service.getOrderItems(orderItem.getOrder().getId());

        Assertions.assertEquals(items.size(), 2);
        Assertions.assertTrue(items.stream().anyMatch(item -> item.getName().equals(orderItem.getProduct().getName())));
        Assertions.assertTrue(items.stream().anyMatch(item -> item.getName().equals(orderItem2.getProduct().getName())));
    }

    @Test
    void testGetOrderItemsErrors() {

        Mockito.when(repository.findByOrderId(0L)).thenReturn(List.of());
        Mockito.when(repository.findByOrderId(null)).thenThrow(InvalidDataAccessApiUsageException.class);

        Assertions.assertEquals(service.getOrderItems(0L).size(), 0);
        Assertions.assertThrowsExactly(InvalidDataAccessApiUsageException.class,() -> service.getOrderItems(null));
    }
}
