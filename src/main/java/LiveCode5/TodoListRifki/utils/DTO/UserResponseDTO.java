package LiveCode5.TodoListRifki.utils.DTO;
import lombok.Data;

@Data
public class UserResponseDTO {
    private String id;
    private String username;
    private String email;
    private String role;
    private String createdAt;
}
