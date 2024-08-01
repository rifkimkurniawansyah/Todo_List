package LiveCode5.TodoListRifki.service.implementation;

import LiveCode5.TodoListRifki.model.User;
import LiveCode5.TodoListRifki.repository.UserRepository;
import LiveCode5.TodoListRifki.utils.DTO.AuthResponseDTO;
import LiveCode5.TodoListRifki.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponseDTO signUp(AuthResponseDTO registrationRequest){
        AuthResponseDTO response = new AuthResponseDTO();
        try{
            User user = new User();
            user.setUsername(registrationRequest.getUsername());
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(User.Role.USER);
            User userResult = userRepository.save(user);

            if(userResult != null && userResult.getId() > 0){
                response.setId(userResult.getId());
                response.setUsername(userResult.getUsername());
                response.setEmail(userResult.getEmail());
            }
        }catch (Exception e){
            response.setError(e.getMessage());
        }
        return response;
    }

    public AuthResponseDTO signIn(AuthResponseDTO signInRequest){
        AuthResponseDTO response = new AuthResponseDTO();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
            var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow();
            var accessToken = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(user);
            response.setAccessToken(accessToken);
            response.setRefreshToken(refreshToken);
        }catch(Exception e){
            response.setError(e.getMessage());
        }
        return response;
    }

    public AuthResponseDTO refreshToken(String refreshTokenRequest) {
        AuthResponseDTO response = new AuthResponseDTO();
        try {
            User user = jwtUtils.validateAndExtractUserFromRefreshToken(refreshTokenRequest);
            if (user != null) {
                String newAccessToken = jwtUtils.generateToken(user);
                response.setAccessToken(newAccessToken);
            } else {
                response.setError("Invalid refresh token");
            }
        } catch (Exception e) {
            response.setError(e.getMessage());
        }
        return response;
    }
}
