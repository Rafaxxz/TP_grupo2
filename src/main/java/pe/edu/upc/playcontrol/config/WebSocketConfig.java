package pe.edu.upc.playcontrol.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import pe.edu.upc.playcontrol.websocket.JwtHandshakeInterceptor;

/**
 * Configuración STOMP sobre SockJS para WebSocket.
 * Permite comunicación bidireccional en tiempo real entre cliente y servidor.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private JwtHandshakeInterceptor jwtHandshakeInterceptor;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Endpoint de conexión WebSocket con SockJS fallback
        registry.addEndpoint("/ws")
                .addInterceptors(jwtHandshakeInterceptor) // Valida JWT en handshake
                .setAllowedOriginPatterns("*")             // CORS
                .withSockJS();                             // Fallback para navegadores sin WebSocket
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // Prefijo para tópicos que pueden suscribirse los clientes
        registry.enableSimpleBroker("/topic");
        
        // Prefijo para rutas de destino (@MessageMapping)
        registry.setApplicationDestinationPrefixes("/app");
    }
}

