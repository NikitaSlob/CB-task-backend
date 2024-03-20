package cb.task.backend.bean;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderSaveRequest {

    @Schema(description = "Id товаров заказа и их количество")
    List<ProductOrderItemRequestBean> products;

    @Schema(description = "Описание товара")
    @NotNull(message = "Id аункта доставки не должно быть пустым")
    Long deliveryPoint;
}
