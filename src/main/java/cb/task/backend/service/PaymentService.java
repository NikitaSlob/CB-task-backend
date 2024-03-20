package cb.task.backend.service;

import cb.task.backend.entity.ProductOrder;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public void payment(ProductOrder order) {
        //Производится оплата
    }

    public void payBack(ProductOrder order) {
        //Производится возврат денег
    }


}
