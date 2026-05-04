package pe.edu.upc.playcontrol.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pe.edu.upc.playcontrol.entities.Mensaje;

import java.util.List;

// Aquí se definen las consultas a la tabla mensaje para filtros y queries de decisión
public interface IMensajeRepository extends JpaRepository<Mensaje, Integer> {

    // Filtro simple: trae mensajes enviados por un remitente
    List<Mensaje> findByRemitente_IdUsuario(Integer remitenteId);

    // Filtro simple: trae mensajes no leídos recibidos por un usuario
    List<Mensaje> findByDestinatario_IdUsuarioAndLeido(Integer destinatarioId, Boolean leido);

    // Query 1: Conversación entre dos usuarios (últimos N mensajes ordenados)
    @Query("SELECT m FROM Mensaje m WHERE " +
           "(m.remitente.idUsuario = :usuarioA AND m.destinatario.idUsuario = :usuarioB) OR " +
           "(m.remitente.idUsuario = :usuarioB AND m.destinatario.idUsuario = :usuarioA) " +
           "ORDER BY m.enviadoEn DESC")
    List<Mensaje> findConversacionBetweenUsers(@Param("usuarioA") Integer usuarioA,
                                                @Param("usuarioB") Integer usuarioB);

    // Query 2: Resumen de no leídos por remitente (cuántos msgs no leídos de cada persona)
    @Query("SELECT m.remitente.idUsuario, COUNT(m) as noLeidos FROM Mensaje m " +
           "WHERE m.destinatario.idUsuario = :usuarioId AND m.leido = false " +
           "GROUP BY m.remitente.idUsuario ORDER BY noLeidos DESC")
    List<Object[]> resumenNoLeidosPorRemitente(@Param("usuarioId") Integer usuarioId);
}
