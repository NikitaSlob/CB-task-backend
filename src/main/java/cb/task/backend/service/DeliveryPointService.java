package cb.task.backend.service;

import cb.task.backend.entity.DeliveryPoint;
import cb.task.backend.repository.DeliveryPointRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPointService {

    final DeliveryPointRepository deliveryPointRepository;

    public DeliveryPointService(DeliveryPointRepository deliveryPointRepository) {
        this.deliveryPointRepository = deliveryPointRepository;
    }

    public DeliveryPoint getById(Long id) {
        return deliveryPointRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Нет пункта доставки с таким id"));
    }

}
