package com.proyecto.tickets;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
        @NotBlank String nombre,
        @NotBlank String apellidos,
        @NotBlank @Email String correo,
        @NotBlank String idUsuario,
        @NotBlank @Size(min = 6, message = "Mínimo 6 caracteres") String contrasena,
        @NotBlank String confirmarContrasena,
        String puesto,
        String dependencia) {
}
