package pe.edu.upc.playcontrol.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import pe.edu.upc.playcontrol.entities.Usuario;
import pe.edu.upc.playcontrol.repositories.IUsuarioRepository;
import pe.edu.upc.playcontrol.securities.JwtTokenUtil;
import pe.edu.upc.playcontrol.servicesimplements.JwtUserDetailsService;

import java.time.OffsetDateTime;
import java.util.Map;

@RestController
public class GoogleOAuthController {

    @Autowired private IUsuarioRepository usuarioRepository;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private JwtUserDetailsService userDetailsService;

    @Value("${google.client.id}")
    private String googleClientId;

    @PostMapping("/login/google")
    public ResponseEntity<?> loginGoogle(@RequestBody Map<String, String> body) {
        String idToken = body.get("idToken");
        if (idToken == null) return ResponseEntity.badRequest().body(Map.of("message", "idToken requerido"));

        try {
            RestTemplate rt = new RestTemplate();
            Map<?, ?> info = rt.getForObject(
                    "https://oauth2.googleapis.com/tokeninfo?id_token=" + idToken, Map.class);

            if (info == null || !googleClientId.equals(info.get("aud")))
                return ResponseEntity.status(401).body(Map.of("message", "Token de Google inválido"));

            String email = (String) info.get("email");
            String name  = (String) info.get("name");

            Usuario user = usuarioRepository.findByEmail(email).orElseGet(() -> {
                Usuario u = new Usuario();
                u.setUsername(email.split("@")[0]);
                u.setEmail(email);
                u.setNombre(name != null ? name : email.split("@")[0]);
                u.setPasswordHash("GOOGLE_OAUTH");
                u.setIdRol(2); // HIJO por defecto
                u.setEstado(true);
                u.setCreatedAt(OffsetDateTime.now());
                return usuarioRepository.save(u);
            });

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            return ResponseEntity.ok(Map.of("jwttoken", jwtTokenUtil.generateToken(userDetails)));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", "Error al verificar token de Google"));
        }
    }
}
