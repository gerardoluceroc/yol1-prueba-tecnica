package prueba_tecnica.yol1.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import prueba_tecnica.yol1.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Encontrar un usuario por su correo electrónico
    Optional<UserEntity> findByEmail(String email);

    // Verificar si existe un usuario con el correo electrónico especificado
    boolean existsByEmail(String email);
    
} 