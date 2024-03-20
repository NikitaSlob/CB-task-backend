package cb.task.backend.mapper;

import cb.task.backend.dto.ProductOrderHistoryDTO;
import cb.task.backend.entity.ProductOrderHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductOrderHistoryMapper {

    @Mapping(target = "status", source = "entity.status.operation")
    ProductOrderHistoryDTO toDTO(ProductOrderHistory entity);

}
