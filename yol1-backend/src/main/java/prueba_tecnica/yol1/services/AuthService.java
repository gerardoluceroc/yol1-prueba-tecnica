package prueba_tecnica.yol1.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;
import prueba_tecnica.yol1.dtos.AuthResponseDTO;
import prueba_tecnica.yol1.dtos.LoginRequestDTO;
import prueba_tecnica.yol1.dtos.RegisterRequestDTO;
import prueba_tecnica.yol1.dtos.Role;
import prueba_tecnica.yol1.entities.UserEntity;
import prueba_tecnica.yol1.errors.UserAlreadyExistsException;
import prueba_tecnica.yol1.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO login(LoginRequestDTO request){
        try {
            // System.out.println("Intentando autenticar usuario: " + request.getEmail());
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            System.out.println("Autenticación exitosa");
        } catch (BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en la autenticación");
        }

        UserDetails user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        String token = jwtService.getToken(user);
        return AuthResponseDTO.builder().token(token).build();
    }

    public AuthResponseDTO register(RegisterRequestDTO request) {
        // Verificar si el usuario ya existe usando findByEmail
        Optional<UserEntity> existingUser = userRepository.findByEmail(request.getEmail());
        
        if (existingUser.isPresent()) {
            // Si el usuario ya existe, lanzar la excepción
            throw new UserAlreadyExistsException("El usuario ya existe");
        }
    
        // Si no existe, proceder con la creación del nuevo usuario
        UserEntity user = UserEntity.builder()
                .name(request.getName())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
    
        userRepository.save(user);  // Guardamos al nuevo usuario
    
        // Crear y devolver el token de autenticación
        return AuthResponseDTO.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
