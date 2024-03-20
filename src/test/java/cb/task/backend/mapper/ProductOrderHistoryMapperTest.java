package cb.task.backend.mapper;

import cb.task.backend.entity.*;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
public class ProductOrderHistoryMapperTest {

    @Autowired
    ProductOrderHistoryMapper productOrderHistoryMapper;

    @Test
    void testToDTO() {
        val deliveryPoint = DeliveryPoint.builder()
                .name("name")
                .address("address")
                .build();

        val user = AppUser.builder()
                .fullName("full name")
                .login("login")
                .password("password")
                .build();


        val productOrder = ProductOrder.builder()
                .deliveryPoint(deliveryPoint)
                .status(ProductOrderStatus.CREATED)
                .user(user)
                .build();

        val productOrderHistory = ProductOrderHistory.builder()
                .order(productOrder)
                .status(ProductOrderStatus.CREATED)
                .dateTime(LocalDateTime.now())
                .build();

        val dto = productOrderHistoryMapper.toDTO(productOrderHistory);

        Assertions.assertEquals(dto.getDateTime(), productOrderHistory.getDateTime());
        Assertions.assertEquals(dto.getStatus(), productOrderHistory.getStatus().getOperation());
    }

    @Test
    void testToDTONullVariables() {
        val deliveryPoint = DeliveryPoint.builder()
                .build();

        val user = AppUser.builder()
                .build();


        val productOrder = ProductOrder.builder()
                .deliveryPoint(deliveryPoint)
                .user(user)
                .build();

        val productOrderHistory = ProductOrderHistory.builder()
                .order(productOrder)
                .build();

        val dto = productOrderHistoryMapper.toDTO(productOrderHistory);

        Assertions.assertNull(dto.getDateTime());
        Assertions.assertNull(dto.getStatus());
    }

    @Test
    void testToDTONullObjectsButOrder() {

        val productOrder = ProductOrder.builder()
                .build();

        val productOrderHistory = ProductOrderHistory.builder()
                .order(productOrder)
                .build();

        val dto = productOrderHistoryMapper.toDTO(productOrderHistory);

        Assertions.assertNull(dto.getDateTime());
        Assertions.assertNull(dto.getStatus());
    }

    @Test
    void testToDTONullObjects() {

        val productOrderHistory = ProductOrderHistory.builder()
                .build();

        val dto = productOrderHistoryMapper.toDTO(productOrderHistory);

        Assertions.assertNull(dto.getDateTime());
        Assertions.assertNull(dto.getStatus());
    }

    @Test
    void testToDTONull() {

        val dto = productOrderHistoryMapper.toDTO(null);

        Assertions.assertNull(dto);
        Assertions.assertNull(dto);
    }
}
