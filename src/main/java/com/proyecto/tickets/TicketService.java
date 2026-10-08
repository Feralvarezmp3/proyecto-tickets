package com.proyecto.tickets;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TicketService {

    private final TicketRepository repository;
    private final NotificationService notifications;

    public TicketService(TicketRepository repository, NotificationService notifications) {
        this.repository = repository;
        this.notifications = notifications;
    }

    public TicketResponseDTO create(TicketRequestDTO dto) {
        String numero = String.format("%03d", repository.count() + 1);
        Ticket t = new Ticket();
        t.setNumeroSolicitud(numero);
        t.setNumeroReporte("REP-" + numero);
        t.setDescripcionBreve(dto.descripcionBreve());
        t.setDetalles(dto.detalles());
        t.setRequerimientoServicio(dto.requerimientoServicio());
        t.setPersonaUsuaria(dto.personaUsuaria());
        t.setGeneradoPor(dto.generadoPor());
        t.setUrgencia(dto.urgencia());
        t.setPrioridad(dto.urgencia());
        t.setEstado("ABIERTO");
        t.setFechaCreacion(LocalDateTime.now());
        t.setFechaEstimadaSolucion(dto.fechaEstimadaSolucion());
        t.setGrupoAsignacion(dto.grupoAsignacion());
        t.setAsignadoA(dto.asignadoA());
        Ticket guardado = repository.save(t);

        String destinatario = dto.asignadoA() != null ? dto.asignadoA() : "GENERAL";
        notifications.create("NUEVO_TICKET",
                "Se ha registrado un nuevo ticket con número de solicitud #" + numero + ".", destinatario);
        return toDto(guardado);
    }

    public List<TicketResponseDTO> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public TicketResponseDTO findById(String id) {
        return toDto(getOrThrow(id));
    }

    public TicketResponseDTO findByNumero(String numeroSolicitud) {
        return toDto(repository.findByNumeroSolicitud(numeroSolicitud).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitud no encontrada")));
    }

    public TicketResponseDTO update(String id, TicketUpdateDTO dto) {
        Ticket t = getOrThrow(id);
        String estadoAnterior = t.getEstado();
        String asignadoAnterior = t.getAsignadoA();

        if (dto.estado() != null) t.setEstado(dto.estado());
        if (dto.urgencia() != null) t.setUrgencia(dto.urgencia());
        if (dto.prioridad() != null) t.setPrioridad(dto.prioridad());
        if (dto.fechaEstimadaSolucion() != null) t.setFechaEstimadaSolucion(dto.fechaEstimadaSolucion());
        if (dto.grupoAsignacion() != null) t.setGrupoAsignacion(dto.grupoAsignacion());
        if (dto.asignadoA() != null) t.setAsignadoA(dto.asignadoA());
        Ticket guardado = repository.save(t);

        if (!Objects.equals(estadoAnterior, guardado.getEstado())) {
            notifications.create("TICKET_ACTUALIZADO",
                    "El ticket #" + guardado.getNumeroSolicitud() + " cambió de estado.",
                    guardado.getPersonaUsuaria());
        }
        if (guardado.getAsignadoA() != null && !Objects.equals(asignadoAnterior, guardado.getAsignadoA())) {
            notifications.create("TICKET_ASIGNADO",
                    "Se te ha asignado el ticket #" + guardado.getNumeroSolicitud(),
                    guardado.getAsignadoA());
        }
        return toDto(guardado);
    }

    public void delete(String id) {
        repository.delete(getOrThrow(id));
    }

    public TicketResponseDTO toDto(Ticket t) {
        return new TicketResponseDTO(t.getId(), t.getNumeroSolicitud(), t.getNumeroReporte(),
                t.getPersonaUsuaria(), t.getGeneradoPor(), t.getUrgencia(), t.getPrioridad(),
                t.getEstado(), t.getDescripcionBreve(), t.getDetalles(), t.getRequerimientoServicio(),
                t.getFechaCreacion(), t.getFechaEstimadaSolucion(), t.getGrupoAsignacion(), t.getAsignadoA());
    }

    private Ticket getOrThrow(String id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticket no encontrado"));
    }
}
