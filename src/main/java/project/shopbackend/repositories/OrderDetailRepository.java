package project.shopbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shopbackend.models.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
}
