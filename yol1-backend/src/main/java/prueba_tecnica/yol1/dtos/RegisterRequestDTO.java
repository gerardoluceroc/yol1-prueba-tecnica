package prueba_tecnica.yol1.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String password;
}