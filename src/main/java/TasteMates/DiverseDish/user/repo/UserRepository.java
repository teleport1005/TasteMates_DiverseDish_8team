package TasteMates.DiverseDish.user.repo;

import TasteMates.DiverseDish.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    void deleteByUsername(String username);
}
