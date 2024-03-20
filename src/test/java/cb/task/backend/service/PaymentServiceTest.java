package cb.task.backend.service;

import cb.task.backend.entity.AppUser;
import cb.task.backend.entity.DeliveryPoint;
import cb.task.backend.entity.ProductOrder;
import cb.task.backend.entity.ProductOrderStatus;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PaymentServiceTest {

    @Autowired
    PaymentService service;

    @Test
    void test() {
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


        Assertions.assertAll(() -> {
            service.payment(productOrder);
            service.payBack(productOrder);
        });
    }

}
