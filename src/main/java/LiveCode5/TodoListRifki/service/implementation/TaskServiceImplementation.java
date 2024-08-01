package LiveCode5.TodoListRifki.service.implementation;

import LiveCode5.TodoListRifki.model.Task;
import LiveCode5.TodoListRifki.model.User;
import LiveCode5.TodoListRifki.repository.TaskRepository;
import LiveCode5.TodoListRifki.service.TaskService;
import LiveCode5.TodoListRifki.service.UserService;
import LiveCode5.TodoListRifki.utils.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImplementation implements TaskService {
    private final TaskRepository taskRepository;
    private final UserService userService;

    @Override
    public TaskResponseDTO create(TaskRequestDTO taskDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setStatus("PENDING");
        task.setCreatedAt(LocalDateTime.now());
        task.setUser(user);
        Task savedTask = taskRepository.save(task);
        return toResponseDTO(savedTask);
    }

    @Override
    public TaskListResponseDTO getAll(String status, String sortBy, String order, Integer page, Integer size) {
        Sort.Direction sortDirection = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(order)) {
            sortDirection = Sort.Direction.DESC;
        }

        Sort sort = Sort.by(sortDirection, sortBy != null ? sortBy : "createdAt");
        Pageable pageable = PageRequest.of(page != null ? page : 0, size != null ? size : 10, sort);

        Page<Task> tasks;
        if (status != null) {
            tasks = taskRepository.findByStatus(status, pageable);
        } else {
            tasks = taskRepository.findAll(pageable);
        }

        TaskListResponseDTO responseDTO = new TaskListResponseDTO();
        responseDTO.setItems(tasks.stream().map(this::toResponseDTO).collect(Collectors.toList()));
        responseDTO.setTotalItems(tasks.getTotalElements());
        responseDTO.setCurrentPage(tasks.getNumber());
        responseDTO.setTotalPages(tasks.getTotalPages());

        return responseDTO;
    }

    @Override
    public TaskResponseDTO getOne(Integer id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with id " + id + " not found"));

        return toResponseDTO(task);
    }

    @Override
    public TaskResponseDTO update(Integer id, TaskUpdateDTO request) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with id " + id + " not found"));

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDueDate(request.getDueDate());
        task.setStatus(request.getStatus());

        Task updatedTask = taskRepository.save(task);
        return toResponseDTO(updatedTask);
    }

    @Override
    public TaskResponseDTO updateStatus(Integer id, TaskStatusUpdateDTO statusUpdateDTO) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task with id " + id + " not found"));

        task.setStatus(statusUpdateDTO.getStatus());
        Task updatedTask = taskRepository.save(task);
        return toResponseDTO(updatedTask);
    }


    @Override
    public void delete(Integer id) {
        taskRepository.deleteById(id);
    }

    private TaskResponseDTO toResponseDTO(Task task) {
        TaskResponseDTO dto = new TaskResponseDTO();
        dto.setId(task.getId());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setStatus(task.getStatus());
        dto.setCreatedAt(task.getCreatedAt());
        return dto;
    }
}
