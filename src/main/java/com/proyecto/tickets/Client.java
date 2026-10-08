package com.proyecto.tickets;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "clientes")
public class Client {
    @Id
    private String id;
    private String nombre;
    private String empresa;
    private String correo;
    private String telefono;
    private String direccion;
    private String contrasena;
}
