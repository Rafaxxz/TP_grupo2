package pe.edu.upc.playcontrol.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import pe.edu.upc.playcontrol.securities.JwtTokenUtil;

import java.util.Map;

/**
 * Interceptor para validar JWT en el handshake WebSocket.
 * El token se espera como parámetro query: ?token=<JWT_TOKEN>
 */
@Component
public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        if (request instanceof ServletServerHttpRequest servletRequest) {
            String token = servletRequest.getServletRequest().getParameter("token");

            if (token == null || token.isBlank()) {
                return false; // Rechazar si no hay token
            }

            try {
                // Validar token
                String username = jwtTokenUtil.getUsernameFromToken(token);
                if (username != null) {
                    // Extraer información del token y guardar en atributos
                    attributes.put("username", username);
                    // Puedes extraer más claims si lo necesitas
                    return true;
                }
            } catch (Exception e) {
                return false; // Token inválido
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                              WebSocketHandler wsHandler, Exception exception) {
        // No-op
    }
}

