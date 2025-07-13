package prueba_tecnica.yol1.jwt;

// La clase abstracta OncePerRequestsFilter se utiliza para crear filtros personalizados

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import prueba_tecnica.yol1.services.JwtService;

// la razon del por que se va a extender de esta clase, es para garantizar que el filtro se ejecute solo una vez por cada solicitud HTTP

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter  {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            final String token = getTokenFromRequest(request);
            final String  email;
        
                if(token == null){
                    filterChain.doFilter(request, response);
                    return;
                }

                email = jwtService.getEmailFromToken(token);

                if(email != null && SecurityContextHolder.getContext().getAuthentication()==null){
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    if(jwtService.isTokenValid(token, userDetails)){
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()); 

                            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                            SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }


        
                filterChain.doFilter(request, response);
         
            }
        
            private String getTokenFromRequest(HttpServletRequest request) {

                final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

                // Este encabezado o string al que vamos a acceder va a comenzar con la palabra "Bearer", 
                // ya que estamos trabajando con autenticacion basada en token.

                // Entonces lo primero que se debe hacer es verificar esto, para retornar el token, porque se debe extraer el token
                // de esa cadena de caracteres sin incorporar la palabra "Bearer".

                if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){

                    //Luego de la palabra "Bearer" viene el token, por lo que se extrae.
                    // Osea se extrae el String desde el caracter 7 hasta el final.
                    return authHeader.substring(7);
                }
                return null;  
            }
}
