package cb.task.backend.controller;

import cb.task.backend.bean.ProductOrderResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductOrderControllerTest {

    @Autowired
    TestRestTemplate testRestTemplate;

    @WithMockUser("emp1")
    @Test
    public void testGetOrders200() {
        ResponseEntity<ProductOrderResponse> result = testRestTemplate.withBasicAuth("emp1", "pass1")
                .getForEntity("/order/list", ProductOrderResponse.class);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @WithMockUser("client1")
    @Test
    public void testGetOrders403() {
        ResponseEntity<ProductOrderResponse> result = testRestTemplate.withBasicAuth("client1", "pass1")
                .getForEntity("/order/list", ProductOrderResponse.class);

        Assertions.assertEquals(HttpStatus.FORBIDDEN, result.getStatusCode());
    }

    @WithMockUser()
    @Test
    public void testGetOrders401() {
        ResponseEntity<ProductOrderResponse> result = testRestTemplate
                .getForEntity("/order/list", ProductOrderResponse.class);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }
}
