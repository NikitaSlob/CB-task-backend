package cb.task.backend.repository;

import cb.task.backend.entity.Product;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    ProductRepository productRepository;

    @Test
    void verifyRepository() {
        val product = Product.builder()
                .name("name")
                .price(1)
                .build();

        Assertions.assertNull(product.getId());
        productRepository.save(product);
        Assertions.assertNotNull(product.getId());
    }

    @Test
    void testFindAllRequest() {
        val product1 = Product.builder()
                .name("test name1")
                .price(1)
                .build();

        val product2 = Product.builder()
                .name("test name2")
                .price(1)
                .build();

        productRepository.save(product1);
        productRepository.save(product2);

        val products = productRepository.findAll();
        Assertions.assertTrue(products.stream().anyMatch(product -> product.getName().equals("test name1")));
        Assertions.assertTrue(products.stream().anyMatch(product -> product.getName().equals("test name2")));

    }

}
