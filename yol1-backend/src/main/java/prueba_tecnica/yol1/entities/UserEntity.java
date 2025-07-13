package prueba_tecnica.yol1.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import prueba_tecnica.yol1.dtos.Role;


@NoArgsConstructor
@AllArgsConstructor
@Entity
//no se puede repetir el email en la base de datos 
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
@Data
@Builder

public class UserEntity implements UserDetails {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private Role role;

    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }


    @Override
    public String getUsername() {
        return this.email; // Devuelve el email como el nombre de usuario
    }
}
