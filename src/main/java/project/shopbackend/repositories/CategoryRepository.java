package project.shopbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shopbackend.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
