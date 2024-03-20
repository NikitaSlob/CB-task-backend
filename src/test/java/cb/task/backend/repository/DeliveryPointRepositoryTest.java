package cb.task.backend.repository;

import cb.task.backend.entity.DeliveryPoint;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class DeliveryPointRepositoryTest {

    @Autowired
    DeliveryPointRepository deliveryPointRepository;

    @Test
    void verifyRepository() {
        val deliveryPoint = DeliveryPoint.builder()
                .name("name")
                .address("address")
                .build();

        Assertions.assertNull(deliveryPoint.getId());
        deliveryPointRepository.save(deliveryPoint);
        Assertions.assertNotNull(deliveryPoint.getId());
    }

    @Test
    void testFindAllRequest() {
        val deliveryPoint1 = DeliveryPoint.builder()
                .name("name1")
                .address("address1")
                .build();

        val deliveryPoint2 = DeliveryPoint.builder()
                .name("name2")
                .address("address2")
                .build();

        deliveryPointRepository.save(deliveryPoint1);
        deliveryPointRepository.save(deliveryPoint2);

        val deliveryPoints = deliveryPointRepository.findAll();
        Assertions.assertTrue(deliveryPoints.stream().anyMatch(point -> point.getName().equals("name1")));
        Assertions.assertTrue(deliveryPoints.stream().anyMatch(point -> point.getName().equals("name2")));

    }

    @Test
    void testFindByIdRequest() {
        val deliveryPoint = DeliveryPoint.builder()
                .name("name")
                .address("address")
                .build();

        deliveryPointRepository.save(deliveryPoint);

        val newDeliveryPoint = deliveryPointRepository.findById(deliveryPoint.getId());
        Assertions.assertTrue(newDeliveryPoint.isPresent());
        Assertions.assertEquals(newDeliveryPoint.get().getId(), deliveryPoint.getId());
    }

}
