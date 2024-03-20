package cb.task.backend.mapper;

import cb.task.backend.dto.ProductDTO;
import cb.task.backend.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product entity);

}
