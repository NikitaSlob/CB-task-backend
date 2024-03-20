package cb.task.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    @Schema(description = "Id")
    Long id;

    @Schema(description = "Название товара")
    String name;

    @Schema(description = "Цена за еденицу товара")
    Integer price;
}
