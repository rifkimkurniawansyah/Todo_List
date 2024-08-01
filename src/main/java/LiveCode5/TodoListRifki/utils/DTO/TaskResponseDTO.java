package LiveCode5.TodoListRifki.utils.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class TaskResponseDTO {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private String status;
    private LocalDateTime createdAt;
}
