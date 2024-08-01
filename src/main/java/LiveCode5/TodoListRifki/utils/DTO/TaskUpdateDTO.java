package LiveCode5.TodoListRifki.utils.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskUpdateDTO {
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private String status;
}
