package LiveCode5.TodoListRifki.controller;

import LiveCode5.TodoListRifki.model.User;
import LiveCode5.TodoListRifki.service.UserService;
import LiveCode5.TodoListRifki.utils.DTO.UserListResponseDTO;
import LiveCode5.TodoListRifki.utils.DTO.UserResponseDTO;
import LiveCode5.TodoListRifki.utils.Res;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping
    public User create(@Valid @RequestBody User request){
        return userService.create(request);
    }

    @GetMapping
    public ResponseEntity<UserListResponseDTO> getAllUsers(
            @RequestParam(value = "username", required = false) String username) {

        List<User> users = userService.getAll();
        List<UserResponseDTO> userDTOs = users.stream()
                .map(user -> {
                    UserResponseDTO dto = new UserResponseDTO();
                    dto.setId(user.getId().toString());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());
                    return dto;
                }).collect(Collectors.toList());

        UserListResponseDTO responseDTO = new UserListResponseDTO();
        responseDTO.setUsers(userDTOs);
        responseDTO.setTotalItems(userDTOs.size());
        responseDTO.setCurrentPage(0);
        responseDTO.setTotalPages(1);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Integer id){
        return Res.renderJson(userService.getOne(id), "Found", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public User update (@PathVariable Integer id, @RequestBody User request){
        return userService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        userService.delete(id);
    }
}
