package prueba_tecnica.yol1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import prueba_tecnica.yol1.jwt.JwtAuthenticationFilter;

/**
 * Configuración de seguridad de Spring Security para manejar la autenticación y autorización en la aplicación.
 * Se define un punto de entrada de autenticación personalizado para manejar los errores de autenticación.
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

     /**
     * Se crea un `AuthenticationEntryPoint` personalizado que maneja los errores de autenticación.
     * Este bean intercepta los intentos fallidos de autenticación y responde con un código 401 (Unauthorized),
     * en lugar del código predeterminado 403 (Forbidden).
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setContentType("application/json"); // Se especifica que la respuesta será en formato JSON.
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // Se establece el código de estado HTTP 401 (Unauthorized).
            response.getWriter().write("{\"error\": \"Credenciales inválidas\"}"); // Se envía un mensaje JSON con el error.
        };
    }

    /**
     * Configuración principal de la cadena de filtros de seguridad (`SecurityFilterChain`).
     * Se encarga de definir cómo se manejarán las solicitudes HTTP dentro del sistema de seguridad.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            // 1. Se desactiva la protección CSRF (Cross-Site Request Forgery) porque la aplicación es stateless (sin sesiones).
            .csrf(csrf -> csrf.disable())

            // 2. Se configuran las reglas de autorización para las solicitudes HTTP.
            .authorizeHttpRequests(authRequest ->
                authRequest
                    .requestMatchers("/auth/**").permitAll() // Se permite el acceso sin autenticación a las rutas que empiezan con `/auth/`.
                    .anyRequest().authenticated() // Cualquier otra solicitud requiere autenticación.
            )

            // 3. Se configura la gestión de sesiones.
            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Se establece una política de sesión sin estado (stateless).
            )

            // 4. Se define el `AuthenticationProvider` que manejará la autenticación de los usuarios.
            .authenticationProvider(authProvider)

            // 5. Se configura el manejo de excepciones para que, en caso de error de autenticación, 
            //    Spring Security utilice el `AuthenticationEntryPoint` definido anteriormente.
            .exceptionHandling(exceptionHandling ->
                exceptionHandling.authenticationEntryPoint(authenticationEntryPoint())
            )

            // 6. Se agrega el filtro de autenticación JWT antes de `UsernamePasswordAuthenticationFilter`,
            //    asegurando que las solicitudes sean validadas con tokens JWT antes de autenticarse con nombre de usuario y contraseña.
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

            // 7. Se construye y devuelve la configuración de la cadena de seguridad.
            .build();
    }
        
}
