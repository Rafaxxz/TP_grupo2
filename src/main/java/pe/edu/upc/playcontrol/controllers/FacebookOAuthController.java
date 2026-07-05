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
public class FacebookOAuthController {

    @Autowired private IUsuarioRepository usuarioRepository;
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private JwtUserDetailsService userDetailsService;

    @Value("${facebook.app.id}")
    private String fbAppId;

    @Value("${facebook.app.secret}")
    private String fbAppSecret;

    @PostMapping("/login/facebook")
    public ResponseEntity<?> loginFacebook(@RequestBody Map<String, String> body) {
        String accessToken = body.get("accessToken");
        if (accessToken == null) return ResponseEntity.badRequest().body(Map.of("message", "accessToken requerido"));

        try {
            RestTemplate rt = new RestTemplate();

            // Verificar token con Facebook
            String verifyUrl = "https://graph.facebook.com/debug_token?input_token=" + accessToken
                    + "&access_token=" + fbAppId + "|" + fbAppSecret;
            Map<?, ?> debug = rt.getForObject(verifyUrl, Map.class);
            Map<?, ?> data = (Map<?, ?>) debug.get("data");
            if (data == null || !Boolean.TRUE.equals(data.get("is_valid")))
                return ResponseEntity.status(401).body(Map.of("message", "Token de Facebook inválido"));

            // Obtener datos del usuario
            String profileUrl = "https://graph.facebook.com/me?fields=id,name,email&access_token=" + accessToken;
            Map<?, ?> profile = rt.getForObject(profileUrl, Map.class);
            if (profile == null) return ResponseEntity.status(401).body(Map.of("message", "No se pudo obtener perfil"));

            String email = (String) profile.get("email");
            String name  = (String) profile.get("name");
            String fbId  = (String) profile.get("id");

            if (email == null) email = fbId + "@facebook.com";
            final String finalEmail = email;
            final String finalName  = name;

            Usuario user = usuarioRepository.findByEmail(finalEmail).orElseGet(() -> {
                Usuario u = new Usuario();
                u.setUsername(fbId);
                u.setEmail(finalEmail);
                u.setNombre(finalName != null ? finalName : fbId);
                u.setPasswordHash("FACEBOOK_OAUTH");
                u.setIdRol(2); // HIJO por defecto
                u.setEstado(true);
                u.setCreatedAt(OffsetDateTime.now());
                return usuarioRepository.save(u);
            });

            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
            return ResponseEntity.ok(Map.of("jwttoken", jwtTokenUtil.generateToken(userDetails)));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("message", "Error al verificar token de Facebook"));
        }
    }
}
