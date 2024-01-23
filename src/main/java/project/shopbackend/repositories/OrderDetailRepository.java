package project.shopbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shopbackend.models.OrderDetail;
import project.shopbackend.responses.OrderResponse;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findAllByOrderId(Long oid);
}
