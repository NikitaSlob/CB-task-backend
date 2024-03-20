package cb.task.backend.controller;

import cb.task.backend.bean.ProductOrderResponse;
import cb.task.backend.bean.ProductOrderSaveRequest;
import cb.task.backend.dto.ProductOrderHistoryDTO;
import cb.task.backend.service.ProductOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class ProductOrderController {
    final ProductOrderService productOrderService;

    public ProductOrderController(ProductOrderService productOrderService) {
        this.productOrderService = productOrderService;
    }

    @GetMapping("/client_list")
    @Operation(summary = "Получение заказов клиента", description = "Получение всех заказов клиента")
    @ApiResponse(description = "Список заказов клиента", responseCode = "200")
    @ApiResponse(description = "Пользователь не авторизован", responseCode = "401")
    @ApiResponse(description = "У пользователя нет доступа", responseCode = "403")
    public ResponseEntity<ProductOrderResponse> getClientOrders(Authentication authentication) {
        return new ResponseEntity<>(productOrderService.getProductOrdersByUser(authentication.getName()), HttpStatus.OK);
    }

    @GetMapping("/list")
    @Operation(summary = "Получение заказов", description = "Получение всех заказов")
    @ApiResponse(description = "Список заказов", responseCode = "200")
    @ApiResponse(description = "Пользователь не авторизован", responseCode = "401")
    @ApiResponse(description = "У пользователя нет доступа", responseCode = "403")
    public ResponseEntity<ProductOrderResponse> getOrders() {
        return new ResponseEntity<>(productOrderService.getAll(), HttpStatus.OK);
    }

    @PutMapping("/create")
    @Operation(summary = "Создание заказа", description = "Выполняет создание заказа")
    @ApiResponse(description = "Ошибка создание заказа", responseCode = "500")
    @ApiResponse(description = "Заказа создан", responseCode = "200")
    @ApiResponse(description = "Параметры указаны некорректно", responseCode = "400")
    @ApiResponse(description = "Пользователь не авторизован", responseCode = "401")
    @ApiResponse(description = "У пользователя нет доступа", responseCode = "403")
    public ResponseEntity<Void> create(Authentication authentication, @RequestBody @Valid ProductOrderSaveRequest request) {
        productOrderService.create(request, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}/get_history")
    @Operation(summary = "Получение истории заказа")
    @ApiResponse(description = "Ошибка получения истории", responseCode = "500")
    @ApiResponse(description = "История заказа", responseCode = "200")
    @ApiResponse(description = "Параметры указаны некорректно", responseCode = "400")
    @ApiResponse(description = "Пользователь не авторизован", responseCode = "401")
    @ApiResponse(description = "У пользователя нет доступа", responseCode = "403")
    public ResponseEntity<List<ProductOrderHistoryDTO>> getOrderHistory(
            Authentication authentication, @PathVariable(value = "id") @Valid Long orderId) {
        return new ResponseEntity<>(productOrderService.getProductOrderHistory(orderId, authentication.getName()),
                HttpStatus.OK);
    }

    @PutMapping("/{id}/pay")
    @Operation(summary = "Оплата заказа", description = "Выполняет операцию оплаты")
    @ApiResponse(description = "Ошибка операции", responseCode = "500")
    @ApiResponse(description = "Операция выполнена", responseCode = "200")
    @ApiResponse(description = "Параметры указаны некорректно", responseCode = "400")
    @ApiResponse(description = "Пользователь не авторизован", responseCode = "401")
    @ApiResponse(description = "У пользователя нет доступа", responseCode = "403")
    public ResponseEntity<Void> pay(Authentication authentication, @PathVariable(value = "id") @Valid Long orderId) {
        productOrderService.pay(orderId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/payBack")
    @Operation(summary = "Возвращение денег за заказ", description = "Выполняет операцию возвращения денег")
    @ApiResponse(description = "Ошибка операции", responseCode = "500")
    @ApiResponse(description = "Операция выполнена", responseCode = "200")
    @ApiResponse(description = "Параметры указаны некорректно", responseCode = "400")
    @ApiResponse(description = "Пользователь не авторизован", responseCode = "401")
    @ApiResponse(description = "У пользователя нет доступа", responseCode = "403")
    public ResponseEntity<Void> payBack(Authentication authentication, @PathVariable(value = "id") @Valid Long orderId) {
        productOrderService.payBack(orderId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/delivery")
    @Operation(summary = "Доставка заказа", description = "Отмечает, что заказ доставлен в пункт выдачи")
    @ApiResponse(description = "Ошибка операции", responseCode = "500")
    @ApiResponse(description = "Операция выполнена", responseCode = "200")
    @ApiResponse(description = "Параметры указаны некорректно", responseCode = "400")
    @ApiResponse(description = "Пользователь не авторизован", responseCode = "401")
    @ApiResponse(description = "У пользователя нет доступа", responseCode = "403")
    public ResponseEntity<Void> delivery(@PathVariable(value = "id") @Valid Long orderId) {
        productOrderService.delivery(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/receive")
    @Operation(summary = "Получение заказа", description = "Отмечает, что клиент получил заказ")
    @ApiResponse(description = "Ошибка операции", responseCode = "500")
    @ApiResponse(description = "Операция выполнена", responseCode = "200")
    @ApiResponse(description = "Параметры указаны некорректно", responseCode = "400")
    @ApiResponse(description = "Пользователь не авторизован", responseCode = "401")
    @ApiResponse(description = "У пользователя нет доступа", responseCode = "403")
    public ResponseEntity<Void> receive(Authentication authentication, @PathVariable(value = "id") @Valid Long orderId) {
        productOrderService.receive(orderId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}/cancel")
    @Operation(summary = "Отмена заказ", description = "Выполняет операции отмены заказа")
    @ApiResponse(description = "Ошибка операции", responseCode = "500")
    @ApiResponse(description = "Операция выполнена", responseCode = "200")
    @ApiResponse(description = "Параметры указаны некорректно", responseCode = "400")
    @ApiResponse(description = "Пользователь не авторизован", responseCode = "401")
    @ApiResponse(description = "У пользователя нет доступа", responseCode = "403")
    public ResponseEntity<Void> cancel(Authentication authentication, @PathVariable(value = "id") @Valid Long orderId) {
        productOrderService.cancel(orderId, authentication.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
