package cb.task.backend.mapper;

import cb.task.backend.dto.ProductOrderItemDTO;
import cb.task.backend.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {

    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "name", source = "product.name")
    ProductOrderItemDTO toDTO(OrderItem orderItem);

}
