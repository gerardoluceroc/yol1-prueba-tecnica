package prueba_tecnica.yol1.errors;

public class UserAlreadyExistsException extends RuntimeException {

    // Constructor que acepta el mensaje de error
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}