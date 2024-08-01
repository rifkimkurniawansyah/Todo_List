package LiveCode5.TodoListRifki.repository;

import LiveCode5.TodoListRifki.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    Page<Task> findByStatus(String status, Pageable pageable);
}
