package cb.task.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderDTO {

    @Schema(description = "Товары")
    List<ProductOrderItemDTO> products;

    @Schema(description = "Id")
    Long id;

    @Schema(description = "Полное имя клиента")
    String fullName;

    @Schema(description = "Цена за все товары")
    Integer price;

    @Schema(description = "Текущий статус заказа")
    String status;

    @Schema(description = "Адрес пункта доставки")
    String deliveryPoint;

}
