package cb.task.backend.service;

import cb.task.backend.dto.ProductOrderHistoryDTO;
import cb.task.backend.entity.ProductOrder;
import cb.task.backend.entity.ProductOrderHistory;
import cb.task.backend.entity.ProductOrderStatus;
import cb.task.backend.mapper.ProductOrderHistoryMapper;
import cb.task.backend.repository.ProductOrderHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProductOrderHistoryService {

    final ProductOrderHistoryRepository productOrderHistoryRepository;

    final ProductOrderHistoryMapper productOrderHistoryMapper;


    public ProductOrderHistoryService(ProductOrderHistoryRepository productOrderHistoryRepository,
                                      ProductOrderHistoryMapper productOrderHistoryMapper) {
        this.productOrderHistoryRepository = productOrderHistoryRepository;
        this.productOrderHistoryMapper = productOrderHistoryMapper;
    }

    public void createProductOrderHistory(ProductOrder productOrder, ProductOrderStatus status) {
        productOrderHistoryRepository.save(ProductOrderHistory.builder()
                .order(productOrder)
                .dateTime(LocalDateTime.now())
                .status(status)
                .build());
    }

    public List<ProductOrderHistoryDTO> getOrderHistory(ProductOrder productOrder) {
        return productOrderHistoryRepository.findAllByOrder(productOrder)
                .stream().map(productOrderHistoryMapper::toDTO).toList();
    }
}
