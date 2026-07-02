package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IRolRepository;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no existe: " + username));

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (usuario.getIdRol() != null) {
            rolRepository.findById(usuario.getIdRol())
                    .ifPresent(rol -> authorities.add(new SimpleGrantedAuthority(rol.getNombre())));
        }

        return new org.springframework.security.core.userdetails.User(
                usuario.getUsername(),
                usuario.getPasswordHash(),
                Boolean.TRUE.equals(usuario.getEstado()),
                true, true, true,
                authorities);
    }
}
