package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.ContenidoEducativoDTO;
import pe.edu.upc.playcontrol.entities.ContenidoEducativo;
import pe.edu.upc.playcontrol.repositories.IContenidoEducativoRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IContenidoEducativoService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

// Aquí se implementa la lógica de negocio para la tabla contenido_educativo
@Service
public class ContenidoEducativoServiceImplement implements IContenidoEducativoService {

    @Autowired
    private IContenidoEducativoRepository contenidoEducativoRepository;

    @Override
    public List<ContenidoEducativoDTO> list() {
        return contenidoEducativoRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public ContenidoEducativoDTO insert(ContenidoEducativoDTO dto) {
        return toDTO(contenidoEducativoRepository.save(toEntity(dto)));
    }

    @Override
    public ContenidoEducativoDTO update(ContenidoEducativoDTO dto) {
        return toDTO(contenidoEducativoRepository.save(toEntity(dto)));
    }

    @Override
    public Optional<ContenidoEducativoDTO> listId(UUID id) {
        return contenidoEducativoRepository.findById(id).map(this::toDTO);
    }

    @Override
    public void delete(UUID id) {
        contenidoEducativoRepository.deleteById(id);
    }

    @Override
    public List<ContenidoEducativoDTO> listByTipo(String tipo) {
        return contenidoEducativoRepository.findByTipo(tipo).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ContenidoEducativoDTO> findByTipoOrdenadoPorFecha(String tipo) {
        return contenidoEducativoRepository.findByTipoOrdenadoPorFecha(tipo).stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ContenidoEducativoDTO> findUltimosContenidosPorTipo(String tipo) {
        return contenidoEducativoRepository.findUltimosContenidosPorTipo(tipo).stream().map(this::toDTO).collect(Collectors.toList());
    }

    private ContenidoEducativoDTO toDTO(ContenidoEducativo e) {
        ContenidoEducativoDTO dto = new ContenidoEducativoDTO();
        dto.setIdContenido(e.getIdContenido());
        dto.setTitulo(e.getTitulo());
        dto.setAutor(e.getAutor());
        dto.setFuente(e.getFuente());
        dto.setTipo(e.getTipo());
        dto.setUrl(e.getUrl());
        dto.setContenido(e.getContenido());
        dto.setPublicadoEn(e.getPublicadoEn());
        return dto;
    }

    private ContenidoEducativo toEntity(ContenidoEducativoDTO dto) {
        ContenidoEducativo e = new ContenidoEducativo();
        e.setIdContenido(dto.getIdContenido());
        e.setTitulo(dto.getTitulo());
        e.setAutor(dto.getAutor());
        e.setFuente(dto.getFuente());
        e.setTipo(dto.getTipo());
        e.setUrl(dto.getUrl());
        e.setContenido(dto.getContenido());
        e.setPublicadoEn(dto.getPublicadoEn());
        return e;
    }
}
