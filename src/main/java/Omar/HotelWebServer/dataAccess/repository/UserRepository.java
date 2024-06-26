package Omar.HotelWebServer.dataAccess.repository;

import Omar.HotelWebServer.dataAccess.model.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);

    Optional<User> getById(int id);

}
