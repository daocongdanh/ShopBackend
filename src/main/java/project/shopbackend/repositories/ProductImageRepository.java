package project.shopbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shopbackend.models.ProductImage;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long id);
}
