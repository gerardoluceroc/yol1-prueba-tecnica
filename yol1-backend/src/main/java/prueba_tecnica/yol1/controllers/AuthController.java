package prueba_tecnica.yol1.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import prueba_tecnica.yol1.dtos.AuthResponseDTO;
import prueba_tecnica.yol1.dtos.LoginRequestDTO;
import prueba_tecnica.yol1.dtos.RegisterRequestDTO;
import prueba_tecnica.yol1.services.AuthService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

// Yo puse esto
@CrossOrigin(origins = "*") // Permite peticiones desde cualquier dominio
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO request) {
        return ResponseEntity.ok(authService.register(request));
    }
}