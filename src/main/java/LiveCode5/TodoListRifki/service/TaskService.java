package LiveCode5.TodoListRifki.service;

import LiveCode5.TodoListRifki.model.Task;
import LiveCode5.TodoListRifki.utils.DTO.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface TaskService {
    TaskResponseDTO create(TaskRequestDTO request);
    TaskListResponseDTO getAll(String status, String sortBy, String order, Integer page, Integer size);
    TaskResponseDTO getOne(Integer id);
    TaskResponseDTO update(Integer id, TaskUpdateDTO request);
    TaskResponseDTO updateStatus(Integer id, TaskStatusUpdateDTO statusUpdateDTO);
    void delete(Integer id);
}
