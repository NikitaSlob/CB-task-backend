package cb.task.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductOrderHistoryDTO {

    @Schema(description = "Операция с заказом")
    String status;

    @Schema(description = "Дата совершения операции")
    LocalDateTime dateTime;
}
