package LiveCode5.TodoListRifki.controller;

import LiveCode5.TodoListRifki.service.implementation.AuthService;
import LiveCode5.TodoListRifki.utils.DTO.AuthResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> signUp(@RequestBody AuthResponseDTO signUpRequest) {
        AuthResponseDTO response = authService.signUp(signUpRequest);
        if(response.getError() == null){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> signIn(@RequestBody AuthResponseDTO signInRequest) {
        AuthResponseDTO response = authService.signIn(signInRequest);
        if(response.getError() == null){
            return ResponseEntity.ok(response);
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDTO> refreshToken(@RequestBody Map<String, String> payload) {
        String refreshToken = payload.get("refreshToken");
        AuthResponseDTO response = authService.refreshToken(refreshToken);
        if (response.getAccessToken() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
