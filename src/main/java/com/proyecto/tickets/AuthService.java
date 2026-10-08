package com.proyecto.tickets;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder encoder;

    public AuthService(UserRepository userRepository, ClientRepository clientRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.encoder = encoder;
    }

    public LoginResponseDTO login(LoginRequestDTO dto) {
        var usuario = userRepository.findByCorreo(dto.correo());
        if (usuario.isPresent() && encoder.matches(dto.contrasena(), usuario.get().getContrasena())) {
            User u = usuario.get();
            return new LoginResponseDTO(u.getId(), u.getNombre() + " " + u.getApellidos(), u.getCorreo(), "USUARIO");
        }
        var cliente = clientRepository.findByCorreo(dto.correo());
        if (cliente.isPresent() && encoder.matches(dto.contrasena(), cliente.get().getContrasena())) {
            Client c = cliente.get();
            return new LoginResponseDTO(c.getId(), c.getNombre(), c.getCorreo(), "CLIENTE");
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Correo o contraseña incorrectos");
    }
}
