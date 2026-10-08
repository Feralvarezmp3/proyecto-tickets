package com.proyecto.tickets;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Document(collection = "tickets")
public class Ticket {
    @Id
    private String id;
    private String numeroSolicitud;
    private String numeroReporte;
    private String descripcionBreve;
    private String detalles;
    private String requerimientoServicio;
    private String personaUsuaria;
    private String generadoPor;
    private String urgencia;
    private String prioridad;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDate fechaEstimadaSolucion;
    private String grupoAsignacion;
    private String asignadoA;
}
