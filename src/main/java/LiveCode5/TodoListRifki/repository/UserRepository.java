package LiveCode5.TodoListRifki.repository;

import LiveCode5.TodoListRifki.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
    List<User> findByUsernameContainingIgnoreCase(String username);
}
