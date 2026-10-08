package com.proyecto.tickets;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public void create(String tipo, String mensaje, String destinatarioId) {
        Notification n = new Notification();
        n.setTipo(tipo);
        n.setMensaje(mensaje);
        n.setDestinatarioId(destinatarioId);
        n.setLeida(false);
        n.setFecha(LocalDateTime.now());
        repository.save(n);
    }

    public List<NotificationResponseDTO> findByDestinatario(String destinatarioId) {
        return repository.findByDestinatarioIdOrderByFechaDesc(destinatarioId).stream()
                .map(n -> new NotificationResponseDTO(n.getId(), n.getTipo(), n.getMensaje(), n.isLeida(), n.getFecha()))
                .toList();
    }

    public void markAllAsRead(String destinatarioId) {
        List<Notification> pendientes = repository.findByDestinatarioIdAndLeidaFalse(destinatarioId);
        pendientes.forEach(n -> n.setLeida(true));
        repository.saveAll(pendientes);
    }
}
