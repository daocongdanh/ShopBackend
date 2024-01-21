package project.shopbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shopbackend.models.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}
