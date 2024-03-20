package cb.task.backend.mapper;

import cb.task.backend.entity.AppUser;
import cb.task.backend.entity.DeliveryPoint;
import cb.task.backend.entity.ProductOrder;
import cb.task.backend.entity.ProductOrderStatus;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductOrderMapperTest {

    @Autowired
    ProductOrderMapper productOrderMapper;

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

        val dto = productOrderMapper.toDTO(productOrder);

        Assertions.assertEquals(dto.getStatus(), productOrder.getStatus().getOperation());
        Assertions.assertEquals(dto.getDeliveryPoint(), productOrder.getDeliveryPoint().getAddress());
        Assertions.assertEquals(dto.getFullName(), productOrder.getUser().getFullName());
        Assertions.assertNull(dto.getProducts());
        Assertions.assertNull(dto.getId());
        Assertions.assertNull(dto.getPrice());
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

        val dto = productOrderMapper.toDTO(productOrder);

        Assertions.assertNull(dto.getStatus());
        Assertions.assertNull(dto.getDeliveryPoint());
        Assertions.assertNull(dto.getFullName());
        Assertions.assertNull(dto.getProducts());
        Assertions.assertNull(dto.getId());
        Assertions.assertNull(dto.getPrice());

    }

    @Test
    void testToDTOWithNullObjects() {
        val productOrder = ProductOrder.builder()
                .build();

        val dto = productOrderMapper.toDTO(productOrder);

        Assertions.assertNull(dto.getStatus());
        Assertions.assertNull(dto.getDeliveryPoint());
        Assertions.assertNull(dto.getFullName());
        Assertions.assertNull(dto.getProducts());
        Assertions.assertNull(dto.getId());
        Assertions.assertNull(dto.getPrice());
    }

    @Test
    void testToDTONull() {

        val dto = productOrderMapper.toDTO(null);

        Assertions.assertNull(dto);
    }

}


