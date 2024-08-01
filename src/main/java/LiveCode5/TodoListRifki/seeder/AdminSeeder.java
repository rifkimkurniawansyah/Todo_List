package LiveCode5.TodoListRifki.seeder;

import LiveCode5.TodoListRifki.model.User;
import LiveCode5.TodoListRifki.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class AdminSeeder implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception{
        seedAdmins();
    }

    private void seedAdmins(){
        if(userRepository.findByEmail("admin123@gmail.com").isPresent()) return;

        userRepository.save(
                User.builder()
                        .email("admin123@gmail.com")
                        .username("admin123")
                        .password(passwordEncoder.encode("123456"))
                        .role(User.Role.SUPER_ADMIN)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }
}
