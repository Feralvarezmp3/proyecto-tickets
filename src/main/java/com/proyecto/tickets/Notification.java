package com.proyecto.tickets;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "notificaciones")
public class Notification {
    @Id
    private String id;
    private String tipo;
    private String mensaje;
    private String destinatarioId;
    private boolean leida;
    private LocalDateTime fecha;
}
