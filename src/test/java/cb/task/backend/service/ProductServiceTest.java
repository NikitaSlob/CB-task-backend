package cb.task.backend.service;

import cb.task.backend.entity.Product;
import cb.task.backend.repository.ProductRepository;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {

    @MockBean
    ProductRepository repository;

    @Autowired
    ProductService service;

    @Test
    void testGetAll() {
        val product1 = Product.builder()
                .name("name")
                .price(1)
                .build();

        val product2 = Product.builder()
                .name("name2")
                .price(1)
                .build();

        Mockito.when(repository.findAll()).thenReturn(List.of(product1, product2));
       val products = service.getAll();

       Assertions.assertEquals(products.getTotalElements(), 2);
       Assertions.assertTrue(products.getProducts().stream()
               .anyMatch(item -> item.getName().equals(product1.getName())));

    }

    @Test
    void testProductById() {
        val product = Product.builder()
                .id(1L)
                .name("name")
                .price(1)
                .build();
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(product));
        Assertions.assertEquals(service.getProductEntityById(1L).getName(), product.getName());
    }

    @Test
    void testProductByIdErrors() {
        Mockito.when(repository.findById(0L)).thenReturn(Optional.empty());
        Mockito.when(repository.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class);

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> service.getProductEntityById(1L));
        Assertions.assertThrowsExactly(InvalidDataAccessApiUsageException.class,
                () -> service.getProductEntityById(null));

    }

}
