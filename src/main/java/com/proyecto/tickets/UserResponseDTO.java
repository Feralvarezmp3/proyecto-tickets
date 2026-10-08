package com.proyecto.tickets;

public record UserResponseDTO(
        String id,
        String idUsuario,
        String nombre,
        String apellidos,
        String correo,
        String puesto,
        String dependencia) {
}
