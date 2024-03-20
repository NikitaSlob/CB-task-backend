package cb.task.backend.service;

import cb.task.backend.entity.DeliveryPoint;
import cb.task.backend.repository.DeliveryPointRepository;
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

import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DeliveryPointServiceTest {

    @MockBean
    DeliveryPointRepository repository;

    @Autowired
    DeliveryPointService service;

    @Test
    void testGetById() {
        val deliveryPoint = DeliveryPoint.builder()
                .id(1L)
                .name("name")
                .address("address")
                .build();

        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(deliveryPoint));

        val newDeliveryPoint = service.getById(deliveryPoint.getId());

        Assertions.assertEquals(newDeliveryPoint.getName(), deliveryPoint.getName());
        Assertions.assertEquals(newDeliveryPoint.getId(), deliveryPoint.getId());
        Assertions.assertEquals(newDeliveryPoint.getAddress(), deliveryPoint.getAddress());

    }

    @Test
    void testGetByIdErrors() {
        Mockito.when(repository.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class);
        Assertions.assertThrowsExactly(IllegalArgumentException.class,() -> service.getById(-1L));
        Assertions.assertThrowsExactly(InvalidDataAccessApiUsageException.class,() -> service.getById(null));
    }


}
