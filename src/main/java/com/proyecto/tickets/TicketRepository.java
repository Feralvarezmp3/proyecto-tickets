package com.proyecto.tickets;

import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    Optional<Ticket> findByNumeroSolicitud(String numeroSolicitud);
    List<Ticket> findByPersonaUsuaria(String personaUsuaria);
    List<Ticket> findByAsignadoA(String asignadoA);
}
