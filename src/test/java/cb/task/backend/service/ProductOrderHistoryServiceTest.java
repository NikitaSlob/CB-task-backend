package cb.task.backend.service;

import cb.task.backend.entity.*;
import cb.task.backend.repository.ProductOrderHistoryRepository;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class ProductOrderHistoryServiceTest {

    @MockBean
    ProductOrderHistoryRepository repository;

    @Autowired
    ProductOrderHistoryService service;

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

        val productOrderHistory = ProductOrderHistory.builder()
                .id(1L)
                .order(productOrder)
                .dateTime(LocalDateTime.now())
                .status(ProductOrderStatus.CREATED)
                .build();

        Mockito.when(repository.save(Mockito.any(ProductOrderHistory.class))).thenReturn(productOrderHistory);

        Assertions.assertAll(() -> service.createProductOrderHistory(productOrder, ProductOrderStatus.CREATED));
    }

    @Test
    void testGetOrderHistory() {
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
                .status(ProductOrderStatus.DELIVERED)
                .user(user)
                .build();

        val productOrderHistory = ProductOrderHistory.builder()
                .id(1L)
                .order(productOrder)
                .dateTime(LocalDateTime.now())
                .status(ProductOrderStatus.CREATED)
                .build();

        val productOrderHistory1 = ProductOrderHistory.builder()
                .id(1L)
                .order(productOrder)
                .dateTime(LocalDateTime.now())
                .status(ProductOrderStatus.PAID)
                .build();

        val productOrderHistory2 = ProductOrderHistory.builder()
                .id(1L)
                .order(productOrder)
                .dateTime(LocalDateTime.now())
                .status(ProductOrderStatus.DELIVERED)
                .build();

        Mockito.when(repository.findAllByOrder(Mockito.any(ProductOrder.class)))
                .thenReturn(List.of(productOrderHistory, productOrderHistory1, productOrderHistory2));

        Assertions.assertEquals(service.getOrderHistory(productOrder).size(), 3);
    }

    @Test
    void testGetOrderItemsErrors() {

        Mockito.when(repository.findAllByOrder(Mockito.any(ProductOrder.class))).thenReturn(List.of());
        Mockito.when(repository.findAllByOrder(null)).thenThrow(InvalidDataAccessApiUsageException.class);

        val productOrder = ProductOrder.builder()
                .build();

        Assertions.assertEquals(service.getOrderHistory(productOrder).size(), 0);
        Assertions.assertThrowsExactly(InvalidDataAccessApiUsageException.class,
                () -> service.getOrderHistory(null));
    }
}
