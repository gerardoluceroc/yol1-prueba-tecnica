package prueba_tecnica.yol1.errors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejamos la excepción personalizada UserAlreadyExistsException
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        // Creamos la respuesta con el mensaje de error
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());

        // Retornamos el código 409 (Conflict) con el mensaje de error
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    // Otras excepciones genéricas pueden ser manejadas aquí
}