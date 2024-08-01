package LiveCode5.TodoListRifki.controller;

import LiveCode5.TodoListRifki.model.User;
import LiveCode5.TodoListRifki.service.UserService;
import LiveCode5.TodoListRifki.utils.DTO.RegisterRequestDTO;
import LiveCode5.TodoListRifki.utils.DTO.UserListResponseDTO;
import LiveCode5.TodoListRifki.utils.DTO.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminUserController {
    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserListResponseDTO> getAllUsers() {
        List<User> users = userService.getAll();

        List<UserResponseDTO> userDTOs = users.stream()
                .map(user -> {
                    UserResponseDTO dto = new UserResponseDTO();
                    dto.setId(user.getId().toString());
                    dto.setUsername(user.getActualUsername());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole().toString());
                    dto.setCreatedAt(user.getCreatedAt().toString());
                    return dto;
                }).collect(Collectors.toList());

        UserListResponseDTO responseDTO = new UserListResponseDTO();
        responseDTO.setUsers(userDTOs);
        responseDTO.setTotalItems(userDTOs.size());
        responseDTO.setCurrentPage(0);
        responseDTO.setTotalPages(1);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer id) {
        User user = userService.getOne(id);

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId().toString());
        dto.setUsername(user.getActualUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());
        dto.setCreatedAt(user.getCreatedAt().toString());

        return ResponseEntity.ok(dto);
    }

}