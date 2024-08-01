package LiveCode5.TodoListRifki.service.implementation;

import LiveCode5.TodoListRifki.model.User;
import LiveCode5.TodoListRifki.repository.UserRepository;
import LiveCode5.TodoListRifki.service.UserService;
import LiveCode5.TodoListRifki.utils.DTO.RegisterRequestDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImplementation implements UserService, UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public User create(User request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setUsername(request.getUsername());
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(User.Role.USER);
        return userRepository.save(user);
    }

    @Override
    public void registerUser(RegisterRequestDTO registerRequest){
        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .createdAt(LocalDateTime.now())
                .role(User.Role.valueOf(registerRequest.getRole()))
                .build();
        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getOne(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("user with id: " + id +
                " not found"));
    }

    @Override
    public User update(Integer id, User request) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new RuntimeException("User with id: " + id + " not found");
        }
        user.setUsername(request.getUsername());
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User with email: " + username + " not found"));
    }
}
