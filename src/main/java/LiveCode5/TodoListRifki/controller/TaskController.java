package LiveCode5.TodoListRifki.controller;

import LiveCode5.TodoListRifki.model.Task;
import LiveCode5.TodoListRifki.service.TaskService;
import LiveCode5.TodoListRifki.utils.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponseDTO> createTask(@RequestBody TaskRequestDTO taskRequest) {
        TaskResponseDTO taskResponse = taskService.create(taskRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable Integer id) {
        TaskResponseDTO taskResponse = taskService.getOne(id);
        return ResponseEntity.ok(taskResponse);
    }

    @GetMapping
    public ResponseEntity<TaskListResponseDTO> getAllTasks(
            @RequestParam(required = false) String status,
            @RequestParam(required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        TaskListResponseDTO taskListResponse = taskService.getAll(status, sortBy, order, page, size);
        return ResponseEntity.ok(taskListResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Integer id, @RequestBody TaskUpdateDTO taskUpdateRequest) {
        TaskResponseDTO taskResponse = taskService.update(id, taskUpdateRequest);
        return ResponseEntity.ok(taskResponse);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TaskResponseDTO> updateTaskStatus(@PathVariable Integer id,
                                                            @RequestBody TaskStatusUpdateDTO statusUpdate) {
        TaskResponseDTO taskResponse = taskService.updateStatus(id, statusUpdate);
        return ResponseEntity.ok(taskResponse);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Integer id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
