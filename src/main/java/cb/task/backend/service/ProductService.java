package cb.task.backend.service;

import cb.task.backend.bean.ProductResponse;
import cb.task.backend.entity.Product;
import cb.task.backend.mapper.ProductMapper;
import cb.task.backend.repository.ProductRepository;
import lombok.val;
import org.springframework.stereotype.Service;


@Service
public class ProductService {

    final ProductRepository productRepository;

    final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ProductResponse getAll() {

        val products = productRepository.findAll();

        return ProductResponse.builder()
                .products(products
                        .stream()
                        .map(productMapper::toDTO)
                        .toList())
                .totalElements((long) products.size())
                .build();

    }

    public Product getProductEntityById(Long id) {
        return productRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Товара с указанным именем не существует"));
    }
}
