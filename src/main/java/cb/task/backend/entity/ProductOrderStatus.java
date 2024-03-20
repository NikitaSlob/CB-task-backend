package cb.task.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ProductOrderStatus {
    CREATED("Заказ создан"),
    PAID("Заказ оплачен"),
    DELIVERED("Заказ доставлен в пункт выдачи"),
    RECEIVED("Заказ получен клиентом"),
    CANCELED("Заказ отменен"),
    AWAITS_PAID_BACK("Заказ отменен, ожитается возвращение денег");

    final String operation;
}
