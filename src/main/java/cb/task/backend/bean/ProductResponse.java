package cb.task.backend.bean;

import cb.task.backend.dto.ProductDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

	@Schema(description = "Товары")
	List<ProductDTO> products;
	@Schema(description = "Общее количество товаров")
	Long totalElements;
	
}
