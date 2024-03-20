package cb.task.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderItemDTO {

    @Schema(description = "Название товара")
    String name;

    @Schema(description = "Цена за еденицу товара")
    Integer price;

    @Schema(description = "Количество")
    Integer quantity;

}
