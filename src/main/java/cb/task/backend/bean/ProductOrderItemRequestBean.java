package cb.task.backend.bean;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOrderItemRequestBean {

    @NotNull(message = "Id товара не должно быть пустым")
    Long productId;

    @NotNull(message = "Количество товара не должно быть пустым")
    @Min(value = 1, message = "Количество товаров должно быть больше 0")
    Integer quantity;

}
