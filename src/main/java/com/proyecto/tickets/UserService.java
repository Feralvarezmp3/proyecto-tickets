package com.proyecto.tickets;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public UserResponseDTO register(UserRequestDTO dto) {
        if (!dto.contrasena().equals(dto.confirmarContrasena())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las contraseñas no coinciden");
        }
        if (repository.existsByCorreo(dto.correo()) || repository.existsByIdUsuario(dto.idUsuario())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario ya existe");
        }
        User u = new User();
        u.setIdUsuario(dto.idUsuario());
        u.setNombre(dto.nombre());
        u.setApellidos(dto.apellidos());
        u.setCorreo(dto.correo());
        u.setPuesto(dto.puesto());
        u.setDependencia(dto.dependencia());
        u.setContrasena(encoder.encode(dto.contrasena()));
        return toDto(repository.save(u));
    }

    public List<UserResponseDTO> findAll(String buscar) {
        List<User> usuarios = (buscar == null || buscar.isBlank())
                ? repository.findAll()
                : repository.findByNombreContainingIgnoreCaseOrApellidosContainingIgnoreCaseOrIdUsuarioContainingIgnoreCase(
                        buscar, buscar, buscar);
        return usuarios.stream().map(this::toDto).toList();
    }

    public UserResponseDTO findById(String id) {
        return toDto(getOrThrow(id));
    }

    public void delete(String id) {
        repository.delete(getOrThrow(id));
    }

    private User getOrThrow(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

    private UserResponseDTO toDto(User u) {
        return new UserResponseDTO(u.getId(), u.getIdUsuario(), u.getNombre(), u.getApellidos(),
                u.getCorreo(), u.getPuesto(), u.getDependencia());
    }
}
