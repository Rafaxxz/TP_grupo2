package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.playcontrol.entities.Mensaje;

import java.util.List;
import java.util.UUID;

// Aquí se definen las consultas a la tabla mensaje para filtros y decisión
public interface IMensajeRepository extends JpaRepository<Mensaje, UUID> {

    // Filtro simple: trae mensajes enviados por un remitente
    List<Mensaje> findByRemitente_IdUsuario(UUID remitenteId);

    // Query de decisión: trae mensajes no leídos recibidos por un usuario
    List<Mensaje> findByDestinatario_IdUsuarioAndLeido(UUID destinatarioId, Boolean leido);
}
