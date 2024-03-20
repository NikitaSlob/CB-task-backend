package cb.task.backend.mapper;

import cb.task.backend.entity.*;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderItemMapperTest {

    @Autowired
    OrderItemMapper mapper;

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

        val product = Product.builder()
                .name("name")
                .price(1)
                .build();

        val orderItem = OrderItem.builder()
                .order(productOrder)
                .quantity(1)
                .product(product)
                .build();

        val dto = mapper.toDTO(orderItem);

        Assertions.assertEquals(dto.getName(), orderItem.getProduct().getName());
        Assertions.assertEquals(dto.getQuantity(), orderItem.getQuantity());
        Assertions.assertEquals(dto.getPrice(), orderItem.getProduct().getPrice());

    }

    @Test
    void testToDTOWithNullVariables() {
        val deliveryPoint = DeliveryPoint.builder()
                .build();

        val user = AppUser.builder()
                .build();


        val productOrder = ProductOrder.builder()
                .deliveryPoint(deliveryPoint)
                .user(user)
                .build();

        val product = Product.builder()
                .build();

        val orderItem = OrderItem.builder()
                .order(productOrder)
                .product(product)
                .build();

        val dto = mapper.toDTO(orderItem);

        Assertions.assertNull(dto.getQuantity());
        Assertions.assertNull(dto.getPrice());
        Assertions.assertNull(dto.getName());

    }

    @Test
    void testToDTOWithNullObjectsButProductAndOrder() {
        val productOrder = ProductOrder.builder()
                .build();

        val product = Product.builder()
                .build();

        val orderItem = OrderItem.builder()
                .order(productOrder)
                .product(product)
                .build();

        val dto = mapper.toDTO(orderItem);

        Assertions.assertNull(dto.getName());
        Assertions.assertNull(dto.getPrice());
        Assertions.assertNull(dto.getQuantity());

    }

    @Test
    void testToDTOWithNullObjects() {
        val orderItem = OrderItem.builder()
                .build();

        val dto = mapper.toDTO(orderItem);

        Assertions.assertNull(dto.getName());
        Assertions.assertNull(dto.getPrice());
        Assertions.assertNull(dto.getQuantity());

    }

    @Test
    void testToDTONull() {

        val dto = mapper.toDTO(null);

        Assertions.assertNull(dto);
    }


}
