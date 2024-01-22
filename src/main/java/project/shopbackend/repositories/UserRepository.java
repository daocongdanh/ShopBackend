package project.shopbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import project.shopbackend.models.User;

public interface UserRepository extends JpaRepository<User,Long> {
    boolean existsById(Long id);
}
