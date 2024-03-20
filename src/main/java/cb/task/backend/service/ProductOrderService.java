package cb.task.backend.service;

import cb.task.backend.bean.ProductOrderResponse;
import cb.task.backend.bean.ProductOrderSaveRequest;
import cb.task.backend.dto.ProductOrderHistoryDTO;
import cb.task.backend.entity.OrderItem;
import cb.task.backend.entity.ProductOrder;
import cb.task.backend.entity.ProductOrderStatus;
import cb.task.backend.entity.RoleEnum;
import cb.task.backend.mapper.ProductOrderMapper;
import cb.task.backend.repository.ProductOrderRepository;
import jakarta.transaction.Transactional;
import lombok.val;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductOrderService {

    final ProductOrderMapper productOrderMapper;

    final ProductOrderRepository productOrderRepository;

    final OrderItemService orderItemService;

    final ProductService productService;

    final UserService userService;

    final DeliveryPointService deliveryPointService;

    final ProductOrderHistoryService productOrderHistoryService;

    final PaymentService paymentService;


    public ProductOrderService(ProductOrderRepository productOrderRepository, ProductOrderMapper productOrderMapper,
                               OrderItemService orderItemService, ProductService productService,
                               UserService userService, DeliveryPointService deliveryPointService,
                               ProductOrderHistoryService productOrderHistoryService, PaymentService paymentService) {
        this.productOrderMapper = productOrderMapper;
        this.productOrderRepository = productOrderRepository;
        this.orderItemService = orderItemService;
        this.productService = productService;
        this.userService = userService;
        this.deliveryPointService = deliveryPointService;
        this.productOrderHistoryService = productOrderHistoryService;
        this.paymentService = paymentService;
    }

    public ProductOrderResponse getAll() {
        val productOrders = productOrderRepository.findAll();

        return getProductOrderResponse(productOrders);
    }


    public ProductOrderResponse getProductOrdersByUser(final String login) {
        val appUser = userService.getUserByLogin(login);
        val productOrders = productOrderRepository.findAllByUser(appUser);

        return getProductOrderResponse(productOrders);
    }

    private ProductOrderResponse getProductOrderResponse(final List<ProductOrder> productOrders) {
        return ProductOrderResponse.builder()
                .productOrderDTOS(productOrders
                        .stream()
                        .map(order -> {
                            val orderDTO = productOrderMapper.toDTO(order);
                            val products = orderItemService.getOrderItems(order.getId());
                            orderDTO.setProducts(products);
                            orderDTO.setPrice(products.stream().map(product ->
                                    product.getQuantity() * product.getPrice()).reduce(0, Integer::sum));
                            return orderDTO;
                        })
                        .toList())
                .totalElements((long) productOrders.size())
                .build();
    }

    @Transactional
    public void create(final ProductOrderSaveRequest request, final String login) {
        val productOrder = productOrderRepository.save(ProductOrder
                .builder()
                .user(userService.getUserByLogin(login))
                .status(ProductOrderStatus.CREATED)
                .deliveryPoint(deliveryPointService.getById(request.getDeliveryPoint()))
                .build());

        request.getProducts().forEach(item -> {
            orderItemService.save(OrderItem.builder()
                    .order(productOrder)
                    .quantity(item.getQuantity())
                    .product(productService.getProductEntityById(item.getProductId()))
                    .build());
        });

        productOrderHistoryService.createProductOrderHistory(productOrder, ProductOrderStatus.CREATED);
    }

    public List<ProductOrderHistoryDTO> getProductOrderHistory(final Long orderId, final String login) {
        val appUser = userService.getUserByLogin(login);
        ProductOrder productOrder;
        if (appUser.getRoles().stream().anyMatch(role -> role.getRole().getName().equals(RoleEnum.ROLE_EMPLOYEE))) {
            productOrder = productOrderRepository.findById(orderId)
                    .orElseThrow(() -> new IllegalArgumentException("Заказа с указанным id не существует"));

        } else {
            productOrder = productOrderRepository.findAllByUser(appUser)
                    .stream().filter(order -> Objects.equals(order.getId(), orderId)).findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("У пользователя нет заказа с таким Id"));
        }

        return productOrderHistoryService.getOrderHistory(productOrder);

    }

    @Transactional
    public void pay(final Long orderId, final String login) {
        val productOrder = productOrderRepository.findAllByUser(userService.getUserByLogin(login))
                .stream().filter(order -> Objects.equals(order.getId(), orderId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("У пользователя нет заказа с таким Id"));

        if (productOrder.getStatus().equals(ProductOrderStatus.CREATED)) {
            paymentService.payment(productOrder);

            productOrder.setStatus(ProductOrderStatus.PAID);
            productOrderRepository.save(productOrder);

            productOrderHistoryService.createProductOrderHistory(productOrder, ProductOrderStatus.PAID);
        } else {
            throw new IllegalArgumentException("Заказ уже оплачен или отменен");
        }
    }

    @Transactional
    public void payBack(final Long orderId, final String login) {
        val productOrder = productOrderRepository.findAllByUser(userService.getUserByLogin(login))
                .stream().filter(order -> Objects.equals(order.getId(), orderId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("У пользователя нет заказа с таким Id"));

        if (productOrder.getStatus().equals(ProductOrderStatus.AWAITS_PAID_BACK)) {
            paymentService.payBack(productOrder);

            productOrder.setStatus(ProductOrderStatus.CANCELED);
            productOrderRepository.save(productOrder);

            productOrderHistoryService.createProductOrderHistory(productOrder, ProductOrderStatus.CANCELED);
        } else {
            throw new IllegalArgumentException("Для данного заказа возврат денег не ожидается");
        }
    }

    @Transactional
    public void cancel(final Long orderId, final String login) {
        val productOrder = productOrderRepository.findAllByUser(userService.getUserByLogin(login))
                .stream().filter(order -> Objects.equals(order.getId(), orderId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("У пользователя нет заказа с таким Id"));

        if (productOrder.getStatus().equals(ProductOrderStatus.CREATED)) {

            productOrder.setStatus(ProductOrderStatus.CANCELED);
            productOrderRepository.save(productOrder);

            productOrderHistoryService.createProductOrderHistory(productOrder, ProductOrderStatus.CANCELED);

        } else if (productOrder.getStatus().equals(ProductOrderStatus.PAID) ||
                productOrder.getStatus().equals(ProductOrderStatus.DELIVERED)) {

            productOrder.setStatus(ProductOrderStatus.AWAITS_PAID_BACK);
            productOrderRepository.save(productOrder);

            productOrderHistoryService.createProductOrderHistory(productOrder, ProductOrderStatus.AWAITS_PAID_BACK);

        } else {
            throw new IllegalArgumentException("Заказ уже был принят клиентом или отменен");
        }
    }

    @Transactional
    public void delivery(final Long orderId) {
        val productOrder = productOrderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Заказа с указанным id не существует"));

        if (productOrder.getStatus().equals(ProductOrderStatus.PAID)) {

            productOrder.setStatus(ProductOrderStatus.DELIVERED);
            productOrderRepository.save(productOrder);

            productOrderHistoryService.createProductOrderHistory(productOrder, ProductOrderStatus.DELIVERED);
        } else {
            throw new IllegalArgumentException("Заказ не оплачен, отменен или уже доставлен");
        }
    }

    @Transactional
    public void receive(final Long orderId, final String login) {
        val productOrder = productOrderRepository.findAllByUser(userService.getUserByLogin(login))
                .stream().filter(order -> Objects.equals(order.getId(), orderId)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("У пользователя нет заказа с таким Id"));

        if (productOrder.getStatus().equals(ProductOrderStatus.DELIVERED)) {

            productOrder.setStatus(ProductOrderStatus.RECEIVED);
            productOrderRepository.save(productOrder);

            productOrderHistoryService.createProductOrderHistory(productOrder, ProductOrderStatus.RECEIVED);
        } else {
            throw new IllegalArgumentException("Заказ ещё не доставлен или уже отменен");
        }
    }
}
