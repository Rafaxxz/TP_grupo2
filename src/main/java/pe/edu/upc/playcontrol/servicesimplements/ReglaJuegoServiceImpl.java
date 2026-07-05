package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.ReglaJuegoDTO;
import pe.edu.upc.playcontrol.dtos.ReglaJuegoRequest;
import pe.edu.upc.playcontrol.entities.Juego;
import pe.edu.upc.playcontrol.entities.ReglaJuego;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IJuegoRepository;
import pe.edu.upc.playcontrol.repositories.IReglaJuegoRepository;
import pe.edu.upc.playcontrol.repositories.SesionJuegoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.ReglaJuegoService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReglaJuegoServiceImpl implements ReglaJuegoService {

    @Autowired
    private IReglaJuegoRepository reglaRepo;

    @Autowired
    private IJuegoRepository juegoRepo;

    @Autowired
    private SesionJuegoRepository sesionRepo;

    @Override
    public ReglaJuegoDTO crear(ReglaJuegoRequest req) {
        ReglaJuego regla = new ReglaJuego();
        Usuario hijo = new Usuario();
        hijo.setIdUsuario(req.getHijoId());
        regla.setHijo(hijo);
        regla.setJuego(obtenerOCrearJuego(req.getNombre()));
        aplicarCampos(regla, req);
        return toDTO(reglaRepo.save(regla));
    }

    @Override
    public ReglaJuegoDTO actualizar(Integer id, ReglaJuegoRequest req) {
        ReglaJuego regla = reglaRepo.findById(id).orElseThrow(() -> new RuntimeException("Regla no encontrada"));
        if (req.getNombre() != null && !req.getNombre().equalsIgnoreCase(regla.getJuego().getNombre())) {
            regla.setJuego(obtenerOCrearJuego(req.getNombre()));
        }
        aplicarCampos(regla, req);
        return toDTO(reglaRepo.save(regla));
    }

    @Override
    public void eliminar(Integer id) {
        reglaRepo.deleteById(id);
    }

    @Override
    public List<ReglaJuegoDTO> listarPorHijo(Integer hijoId) {
        return reglaRepo.findByHijo_IdUsuario(hijoId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    private void aplicarCampos(ReglaJuego regla, ReglaJuegoRequest req) {
        if (req.getUrl() != null) regla.setUrl(req.getUrl());
        regla.setHoraInicio(req.getHoraInicio());
        regla.setHoraFin(req.getHoraFin());
        if (req.getMinutosMaximos() != null) regla.setMinutosMaximos(req.getMinutosMaximos());
        if (req.getBloqueado() != null) regla.setBloqueado(req.getBloqueado());
    }

    // Reutiliza el juego del catálogo si ya existe uno con ese nombre, si no lo crea.
    private Juego obtenerOCrearJuego(String nombre) {
        return juegoRepo.findByNombreIgnoreCase(nombre).orElseGet(() -> {
            Juego j = new Juego();
            j.setNombre(nombre);
            return juegoRepo.save(j);
        });
    }

    private ReglaJuegoDTO toDTO(ReglaJuego r) {
        ReglaJuegoDTO dto = new ReglaJuegoDTO();
        dto.setIdRegla(r.getIdRegla());
        dto.setHijoId(r.getHijo().getIdUsuario());
        dto.setHijoNombre(r.getHijo().getNombre());
        dto.setJuegoId(r.getJuego().getIdJuego());
        dto.setNombre(r.getJuego().getNombre());
        dto.setUrl(r.getUrl());
        dto.setHoraInicio(r.getHoraInicio());
        dto.setHoraFin(r.getHoraFin());
        dto.setMinutosMaximos(r.getMinutosMaximos());
        dto.setBloqueado(r.getBloqueado());
        dto.setMinutosUsadosHoy(sesionRepo.sumMinutosByUsuarioAndJuegoAndFecha(
                r.getHijo().getIdUsuario(), r.getJuego().getIdJuego(), LocalDate.now()));
        return dto;
    }
}
