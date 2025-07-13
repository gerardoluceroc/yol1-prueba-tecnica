package prueba_tecnica.yol1.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import prueba_tecnica.yol1.entities.UserEntity;
import prueba_tecnica.yol1.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    //Obtener todos los usuarios
    public List<UserEntity>  getAllUsers(){
        return (List<UserEntity>) userRepository.findAll();
    }
}
