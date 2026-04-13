package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.dtos.RolDTO;
import pe.edu.upc.playcontrol.entities.Rol;
import pe.edu.upc.playcontrol.repositories.IRolRepository;
import pe.edu.upc.playcontrol.servicesinterfaces.IRolService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RolServiceImplement implements IRolService {

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public List<RolDTO> getAll() {
        return rolRepository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<RolDTO> getById(Integer id) {
        return rolRepository.findById(id).map(this::toDTO);
    }

    @Override
    public RolDTO save(RolDTO dto) {
        return toDTO(rolRepository.save(toEntity(dto)));
    }

    @Override
    public void delete(Integer id) {
        rolRepository.deleteById(id);
    }

    private RolDTO toDTO(Rol e) {
        RolDTO dto = new RolDTO();
        dto.setIdRol(e.getIdRol());
        dto.setNombre(e.getNombre());
        return dto;
    }

    private Rol toEntity(RolDTO dto) {
        Rol e = new Rol();
        e.setIdRol(dto.getIdRol());
        e.setNombre(dto.getNombre());
        return e;
    }
}
