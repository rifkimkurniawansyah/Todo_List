package LiveCode5.TodoListRifki.utils.DTO;

import lombok.Data;

import java.util.List;

@Data
public class TaskListResponseDTO {
    private List<TaskResponseDTO> items;
    private long totalItems;
    private int currentPage;
    private int totalPages;
}
