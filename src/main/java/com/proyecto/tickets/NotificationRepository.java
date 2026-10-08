package com.proyecto.tickets;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByDestinatarioIdOrderByFechaDesc(String destinatarioId);
    List<Notification> findByDestinatarioIdAndLeidaFalse(String destinatarioId);
}
