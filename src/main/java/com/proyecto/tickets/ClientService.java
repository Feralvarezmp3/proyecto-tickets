package com.proyecto.tickets;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public ClientService(ClientRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    public ClientResponseDTO register(ClientRequestDTO dto) {
        if (!dto.contrasena().equals(dto.confirmarContrasena())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Las contraseñas no coinciden");
        }
        if (repository.existsByCorreo(dto.correo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El cliente ya existe");
        }
        Client c = new Client();
        c.setNombre(dto.nombre());
        c.setEmpresa(dto.empresa());
        c.setCorreo(dto.correo());
        c.setTelefono(dto.telefono());
        c.setDireccion(dto.direccion());
        c.setContrasena(encoder.encode(dto.contrasena()));
        return toDto(repository.save(c));
    }

    public List<ClientResponseDTO> findAll(String buscar) {
        List<Client> clientes = (buscar == null || buscar.isBlank())
                ? repository.findAll()
                : repository.findByNombreContainingIgnoreCaseOrEmpresaContainingIgnoreCase(buscar, buscar);
        return clientes.stream().map(this::toDto).toList();
    }

    public ClientResponseDTO findById(String id) {
        return toDto(getOrThrow(id));
    }

    public void delete(String id) {
        repository.delete(getOrThrow(id));
    }

    private Client getOrThrow(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));
    }

    private ClientResponseDTO toDto(Client c) {
        return new ClientResponseDTO(c.getId(), c.getNombre(), c.getEmpresa(),
                c.getCorreo(), c.getTelefono(), c.getDireccion());
    }
}
