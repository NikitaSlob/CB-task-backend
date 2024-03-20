package cb.task.backend.mapper;

import cb.task.backend.entity.Product;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductMapperTest {

    @Autowired
    ProductMapper productMapper;

    @Test
    void testToDTO() {
        val product = Product.builder()
                .id(1L)
                .name("name")
                .price(1)
                .build();

        val dto = productMapper.toDTO(product);

        Assertions.assertEquals(dto.getPrice(), product.getPrice());
        Assertions.assertEquals(dto.getName(), product.getName());
        Assertions.assertEquals(dto.getId(), product.getId());
    }

    @Test
    void testToDTOWithNulls() {
        val product = Product.builder()
                .build();

        val dto = productMapper.toDTO(product);

        Assertions.assertNull(dto.getPrice());
        Assertions.assertNull(dto.getName());
        Assertions.assertNull(dto.getId());
    }

    @Test
    void testToDTONull() {

        val dto = productMapper.toDTO(null);

        Assertions.assertNull(dto);
    }
}
