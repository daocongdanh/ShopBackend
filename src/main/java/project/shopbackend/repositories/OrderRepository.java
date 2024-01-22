package project.shopbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shopbackend.models.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long id);
}
