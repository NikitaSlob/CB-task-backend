package cb.task.backend.mapper;

import cb.task.backend.dto.ProductOrderDTO;
import cb.task.backend.entity.ProductOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductOrderMapper {

    @Mapping(target = "fullName", source = "entity.user.fullName")
    @Mapping(target = "status", source = "entity.status.operation")
    @Mapping(target = "deliveryPoint", source = "entity.deliveryPoint.address")
    ProductOrderDTO toDTO(ProductOrder entity);
}
