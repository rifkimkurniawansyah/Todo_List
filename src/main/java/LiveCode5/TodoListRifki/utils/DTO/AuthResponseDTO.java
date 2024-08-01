package LiveCode5.TodoListRifki.utils.DTO;

import LiveCode5.TodoListRifki.model.Task;
import LiveCode5.TodoListRifki.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO {
    private String error;
    private String accessToken;
    private String refreshToken;
    private Integer id;
    private String username;
    private String email;
    private String password;
    private List<Task> tasks;
    private User users;
}
