package cb.task.backend.service;

import cb.task.backend.dto.ProductOrderItemDTO;
import cb.task.backend.entity.OrderItem;
import cb.task.backend.mapper.OrderItemMapper;
import cb.task.backend.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    final OrderItemMapper orderItemMapper;

    final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
        this.orderItemRepository = orderItemRepository;
    }

    public List<ProductOrderItemDTO> getOrderItems(Long orderId) {
        return orderItemRepository.findByOrderId(orderId).stream().map(orderItemMapper::toDTO).toList();
    }

    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }


}
