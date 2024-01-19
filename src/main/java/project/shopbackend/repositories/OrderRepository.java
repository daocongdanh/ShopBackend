package project.shopbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shopbackend.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
