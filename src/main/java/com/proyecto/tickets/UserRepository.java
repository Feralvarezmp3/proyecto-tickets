package com.proyecto.tickets;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    boolean existsByIdUsuario(String idUsuario);
    List<User> findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCaseOrIdUsuarioContainingIgnoreCase(
            String nombre, String apellidos, String idUsuario);
}
