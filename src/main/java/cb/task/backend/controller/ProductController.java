package cb.task.backend.controller;


import cb.task.backend.bean.ProductResponse;
import cb.task.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/product")
public class ProductController {

    final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    @Operation(summary = "Получение товаров", description = "Получение товаров с данным размером страницы и её номером")
    @ApiResponse(description = "Список товаров", responseCode = "200")
    @ApiResponse(description = "Пользователь не авторизован", responseCode = "401")
    public ResponseEntity<ProductResponse> getProducts() {

        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }
}
