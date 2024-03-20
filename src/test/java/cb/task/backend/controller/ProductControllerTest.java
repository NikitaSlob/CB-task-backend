package cb.task.backend.controller;

import cb.task.backend.bean.ProductResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {


    @Autowired
    TestRestTemplate testRestTemplate;

    @WithMockUser("emp1")
    @Test
    public void testGetProducts200() throws Exception {
        ResponseEntity<ProductResponse> result = testRestTemplate.withBasicAuth("emp1", "pass1")
                .getForEntity("/product/list", ProductResponse.class);

        Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @WithMockUser()
    @Test
    public void testGetProducts401() throws Exception {
        ResponseEntity<ProductResponse> result = testRestTemplate
                .getForEntity("/product/list", ProductResponse.class);

        Assertions.assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
    }

}
