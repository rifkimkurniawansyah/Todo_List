package LiveCode5.TodoListRifki.service;

import LiveCode5.TodoListRifki.model.User;
import LiveCode5.TodoListRifki.utils.DTO.RegisterRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User create(User request);
    void registerUser(RegisterRequestDTO registerRequest);
    List<User> getAll();
    User getOne(Integer id);
    User update(Integer id, User request);
    void delete(Integer id);
}
