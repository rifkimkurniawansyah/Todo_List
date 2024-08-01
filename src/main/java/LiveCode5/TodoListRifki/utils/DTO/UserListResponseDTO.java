package LiveCode5.TodoListRifki.utils.DTO;

import lombok.Data;

import java.util.List;

@Data
public class UserListResponseDTO {
    private List<UserResponseDTO> users;
    private long totalItems;
    private int currentPage;
    private int totalPages;
}
