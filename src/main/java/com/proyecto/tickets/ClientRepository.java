package com.proyecto.tickets;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends MongoRepository<Client, String> {
    Optional<Client> findByCorreo(String correo);
    boolean existsByCorreo(String correo);
    List<Client> findByNombreContainingIgnoreCaseOrEmpresaContainingIgnoreCase(String nombre, String empresa);
}
