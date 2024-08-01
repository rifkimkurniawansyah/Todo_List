package LiveCode5.TodoListRifki.utils.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
    private String role;
    private LocalDateTime createdAt;
}
