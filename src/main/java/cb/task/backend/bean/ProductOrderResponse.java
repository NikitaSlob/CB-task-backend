package cb.task.backend.bean;

import cb.task.backend.dto.ProductOrderDTO;
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
public class ProductOrderResponse {

    @Schema(description = "Общее количество товаров")
    List<ProductOrderDTO> productOrderDTOS;
    @Schema(description = "Общее количество товаров")
    Long totalElements;

}
