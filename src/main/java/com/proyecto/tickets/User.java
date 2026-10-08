package com.proyecto.tickets;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "usuarios")
public class User {
    @Id
    private String id;
    private String idUsuario;
    private String nombre;
    private String apellidos;
    private String correo;
    private String puesto;
    private String dependencia;
    private String contrasena;
}
