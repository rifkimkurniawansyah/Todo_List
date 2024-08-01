package LiveCode5.TodoListRifki.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class Res {
    public static <T> ResponseEntity<?> renderJson(T data, String message, HttpStatus httpStatus){
        WebResponse<?> response = WebResponse.builder()
                .status(httpStatus.getReasonPhrase())
                .message(message)
                .data(data)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }
}
