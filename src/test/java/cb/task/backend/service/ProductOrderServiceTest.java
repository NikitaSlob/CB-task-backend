package cb.task.backend.service;

import cb.task.backend.bean.ProductOrderItemRequestBean;
import cb.task.backend.bean.ProductOrderResponse;
import cb.task.backend.bean.ProductOrderSaveRequest;
import cb.task.backend.dto.ProductOrderDTO;
import cb.task.backend.entity.*;
import cb.task.backend.mapper.ProductOrderMapper;
import cb.task.backend.repository.ProductOrderRepository;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductOrderServiceTest {

    @MockBean
    ProductOrderRepository productOrderRepository;

    @MockBean
    OrderItemService orderItemService;


    @MockBean
    UserService userService;

    @MockBean
    ProductService productService;

    @MockBean
    DeliveryPointService deliveryPointService;

    @MockBean
    ProductOrderHistoryService productOrderHistoryService;

    @MockBean
    PaymentService paymentService;

    @MockBean
    ProductOrderMapper mapper;

    @Autowired
    ProductOrderService service;

    @Test
    void testGetAll() {
        Mockito.when(productOrderRepository.findAll()).thenReturn(List.of());
        Mockito.when(orderItemService.getOrderItems(Mockito.anyLong())).thenReturn(List.of());
        Assertions.assertAll(() -> service.getAll());
    }

    @Test
    void testGetProductOrdersByUser() {

        val user = AppUser.builder()
                .login("login")
                .fullName("fullname")
                .build();

        val productOrder1 = ProductOrder.builder()
                .build();

        val productOrder2 = ProductOrder.builder()
                .build();

        val response = ProductOrderResponse.builder()
                .productOrderDTOS(List.of(new ProductOrderDTO(), new ProductOrderDTO()))
                .totalElements(2L)
                .build();

        Mockito.when(userService.getUserByLogin("login")).thenReturn(user);
        Mockito.when(orderItemService.getOrderItems(Mockito.anyLong())).thenReturn(List.of());
        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of(productOrder1, productOrder2));

        Mockito.when(mapper.toDTO(Mockito.any(ProductOrder.class))).thenReturn(new ProductOrderDTO());

        val result = service.getProductOrdersByUser("login");

        Assertions.assertEquals(result.getTotalElements(), response.getTotalElements());
    }

    @Test
    void testGetProductOrdersByUserEmpty() {

        val user = AppUser.builder()
                .login("login")
                .fullName("fullname")
                .build();

        val response = ProductOrderResponse.builder()
                .productOrderDTOS(List.of())
                .totalElements(0L)
                .build();

        Mockito.when(userService.getUserByLogin("login")).thenReturn(user);
        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of());

        val result = service.getProductOrdersByUser("login");

        Assertions.assertEquals(result.getTotalElements(), response.getTotalElements());
    }

    @Test
    void testCreate() {
        val requestProduct1 = new ProductOrderItemRequestBean(1L, 1);
        val requestProduct2 = new ProductOrderItemRequestBean(2L, 1);

        val request = new ProductOrderSaveRequest(List.of(requestProduct1, requestProduct2), 1L);

        Assertions.assertAll(() -> service.create(request, "login"));
    }

    @Test
    void testGetOrderHistory() {
        val user = AppUser.builder()
                .login("login")
                .fullName("fullname")
                .roles(List.of(UserRole.builder()
                        .role(Role.builder()
                                .name(RoleEnum.ROLE_USER)
                                .build())
                        .build()))
                .build();

        val userEmp = AppUser.builder()
                .login("login1")
                .fullName("fullname")
                .roles(List.of(UserRole.builder()
                        .role(Role.builder()
                                .name(RoleEnum.ROLE_EMPLOYEE)
                                .build())
                        .build()))
                .build();

        val productOrder = ProductOrder.builder()
                .id(1L)
                .build();

        Mockito.when(userService.getUserByLogin("login")).thenReturn(user);
        Mockito.when(userService.getUserByLogin("login1")).thenReturn(userEmp);

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of(productOrder));
        Mockito.when(productOrderRepository.findById(1L))
                .thenReturn(Optional.of(productOrder));

        Assertions.assertAll(() -> service.getProductOrderHistory(1L, "login"));
        Assertions.assertAll(() -> service.getProductOrderHistory(1L, "login1"));
    }

    @Test
    void testGetOrderHistoryErrors() {
        val user = AppUser.builder()
                .login("login")
                .fullName("fullname")
                .roles(List.of(UserRole.builder()
                        .role(Role.builder()
                                .name(RoleEnum.ROLE_USER)
                                .build())
                        .build()))
                .build();

        val userEmp = AppUser.builder()
                .login("login1")
                .fullName("fullname")
                .roles(List.of(UserRole.builder()
                        .role(Role.builder()
                                .name(RoleEnum.ROLE_EMPLOYEE)
                                .build())
                        .build()))
                .build();

        val productOrder = ProductOrder.builder()
                .id(1L)
                .build();

        Mockito.when(userService.getUserByLogin("login")).thenReturn(user);
        Mockito.when(userService.getUserByLogin("login1")).thenReturn(userEmp);

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of());
        Mockito.when(productOrderRepository.findById(1L))
                .thenReturn(Optional.of(productOrder));

        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> service.getProductOrderHistory(1L, "login"));
        Assertions.assertAll(() -> service.getProductOrderHistory(1L, "login1"));


        Mockito.when(productOrderRepository.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(IllegalArgumentException.class,
                () -> service.getProductOrderHistory(1L, "login1"));
    }

    @Test
    void testPay() {
        val user = AppUser.builder()
                .login("login")
                .fullName("fullname")
                .roles(List.of(UserRole.builder()
                        .role(Role.builder()
                                .build())
                        .build()))
                .build();

        val productOrder = ProductOrder.builder()
                .id(1L)
                .status(ProductOrderStatus.CREATED)
                .build();

        Mockito.when(userService.getUserByLogin("login")).thenReturn(user);

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of(productOrder));
        Assertions.assertAll(() -> service.pay(1L, "login"));

    }

    @Test
    void testPayErrors() {
        val user = AppUser.builder()
                .login("login")
                .fullName("fullname")
                .roles(List.of(UserRole.builder()
                        .role(Role.builder()
                                .build())
                        .build()))
                .build();

        val productOrder = ProductOrder.builder()
                .id(1L)
                .status(ProductOrderStatus.PAID)
                .build();

        Mockito.when(userService.getUserByLogin("login")).thenReturn(user);

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of(productOrder));
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.pay(1L, "login"));

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of());
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.pay(1L, "login"));

    }

    @Test
    void testPayBack() {
        val user = AppUser.builder()
                .login("login")
                .fullName("fullname")
                .roles(List.of(UserRole.builder()
                        .role(Role.builder()
                                .build())
                        .build()))
                .build();

        val productOrder = ProductOrder.builder()
                .id(1L)
                .status(ProductOrderStatus.AWAITS_PAID_BACK)
                .build();

        Mockito.when(userService.getUserByLogin("login")).thenReturn(user);

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of(productOrder));
        Assertions.assertAll(() -> service.payBack(1L, "login"));

    }

    @Test
    void testPayBackErrors() {
        val user = AppUser.builder()
                .login("login")
                .fullName("fullname")
                .roles(List.of(UserRole.builder()
                        .role(Role.builder()
                                .build())
                        .build()))
                .build();

        val productOrder = ProductOrder.builder()
                .id(1L)
                .status(ProductOrderStatus.PAID)
                .build();

        Mockito.when(userService.getUserByLogin("login")).thenReturn(user);

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of(productOrder));
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.payBack(1L, "login"));

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of());
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.payBack(1L, "login"));

    }

    @Test
    void testCancel() {
        val user = AppUser.builder()
                .login("login")
                .fullName("fullname")
                .roles(List.of(UserRole.builder()
                        .role(Role.builder()
                                .build())
                        .build()))
                .build();

        val productOrder = ProductOrder.builder()
                .id(1L)
                .status(ProductOrderStatus.CREATED)
                .build();

        Mockito.when(userService.getUserByLogin("login")).thenReturn(user);

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of(productOrder));
        Assertions.assertAll(() -> service.cancel(1L, "login"));

        productOrder.setStatus(ProductOrderStatus.PAID);
        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of(productOrder));
        Assertions.assertAll(() -> service.cancel(1L, "login"));

    }

    @Test
    void testCancelErrors() {
        val user = AppUser.builder()
                .login("login")
                .fullName("fullname")
                .roles(List.of(UserRole.builder()
                        .role(Role.builder()
                                .build())
                        .build()))
                .build();

        val productOrder = ProductOrder.builder()
                .id(1L)
                .status(ProductOrderStatus.RECEIVED)
                .build();

        Mockito.when(userService.getUserByLogin("login")).thenReturn(user);

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of(productOrder));
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.cancel(1L, "login"));

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of());
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.cancel(1L, "login"));

    }

    @Test
    void testDelivery() {
        val productOrder = ProductOrder.builder()
                .id(1L)
                .status(ProductOrderStatus.PAID)
                .build();

        Mockito.when(productOrderRepository.findById(1L))
                .thenReturn(Optional.of(productOrder));
        Assertions.assertAll(() -> service.delivery(1L));

    }

    @Test
    void testDeliveryErrors() {
        val productOrder = ProductOrder.builder()
                .id(1L)
                .status(ProductOrderStatus.RECEIVED)
                .build();

        Mockito.when(productOrderRepository.findById(1L))
                .thenReturn(Optional.of(productOrder));
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.delivery(1L));

        Mockito.when(productOrderRepository.findById(1L))
                .thenReturn(Optional.empty());
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.delivery(1L));

    }

    @Test
    void testReceive() {
        val user = AppUser.builder()
                .login("login")
                .fullName("fullname")
                .roles(List.of(UserRole.builder()
                        .role(Role.builder()
                                .build())
                        .build()))
                .build();

        val productOrder = ProductOrder.builder()
                .id(1L)
                .status(ProductOrderStatus.DELIVERED)
                .build();

        Mockito.when(userService.getUserByLogin("login")).thenReturn(user);

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of(productOrder));
        Assertions.assertAll(() -> service.receive(1L, "login"));

    }

    @Test
    void testReceiveErrors() {
        val user = AppUser.builder()
                .login("login")
                .fullName("fullname")
                .roles(List.of(UserRole.builder()
                        .role(Role.builder()
                                .build())
                        .build()))
                .build();

        val productOrder = ProductOrder.builder()
                .id(1L)
                .status(ProductOrderStatus.RECEIVED)
                .build();

        Mockito.when(userService.getUserByLogin("login")).thenReturn(user);

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of(productOrder));
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.receive(1L, "login"));

        Mockito.when(productOrderRepository.findAllByUser(user))
                .thenReturn(List.of());
        Assertions.assertThrows(IllegalArgumentException.class, () -> service.receive(1L, "login"));

    }
}
