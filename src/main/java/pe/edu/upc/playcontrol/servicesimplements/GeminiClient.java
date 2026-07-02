package pe.edu.upc.playcontrol.servicesimplements;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Cliente REST mínimo para la API generateContent de Google Gemini.
 * Arma el body multi-turn (system_instruction + contents) y extrae el texto de la respuesta.
 */
@Component
public class GeminiClient {

    private static final String ENDPOINT =
            "https://generativelanguage.googleapis.com/v1beta/models/%s:generateContent";

    @Value("${gemini.api.key:}")
    private String apiKey;

    @Value("${gemini.model:gemini-3.1-flash-lite}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * @param systemPrompt instrucción de sistema (distinta según rol)
     * @param historial    interacciones previas en orden cronológico (turno usuario + turno modelo)
     * @param mensaje      mensaje actual del usuario
     */
    public String generar(String systemPrompt, List<Turno> historial, String mensaje) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException(
                    "La variable de entorno GEMINI_API_KEY no está configurada");
        }

        // contents: se reconstruye la conversación previa y al final el mensaje actual
        List<Map<String, Object>> contents = new ArrayList<>();
        for (Turno t : historial) {
            contents.add(Map.of(
                    "role", "user",
                    "parts", List.of(Map.of("text", t.pregunta()))));
            contents.add(Map.of(
                    "role", "model",
                    "parts", List.of(Map.of("text", t.respuesta()))));
        }
        contents.add(Map.of(
                "role", "user",
                "parts", List.of(Map.of("text", mensaje))));

        Map<String, Object> body = Map.of(
                "system_instruction", Map.of("parts", List.of(Map.of("text", systemPrompt))),
                "contents", contents,
                // thinkingBudget:0 desactiva el "razonamiento" del modelo -> respuestas mucho más rápidas.
                // maxOutputTokens limita el largo (respuestas breves) y ahorra cuota.
                "generationConfig", Map.of(
                        "thinkingConfig", Map.of("thinkingBudget", 0),
                        "temperature", 0.7,
                        "maxOutputTokens", 600));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-goog-api-key", apiKey);

        String url = String.format(ENDPOINT, model);

        try {
            Map<?, ?> response = restTemplate.postForObject(
                    url, new HttpEntity<>(body, headers), Map.class);
            return extraerTexto(response);
        } catch (HttpStatusCodeException e) {
            int status = e.getStatusCode().value();
            if (status == 429 || status == 503) {
                throw new GeminiRateLimitException(
                        "El asistente está muy solicitado en este momento");
            }
            throw new RuntimeException(
                    "Error de la API de Gemini (" + status + "): " + e.getResponseBodyAsString());
        }
    }

    @SuppressWarnings("unchecked")
    private String extraerTexto(Map<?, ?> response) {
        if (response == null) return "";
        List<?> candidates = (List<?>) response.get("candidates");
        if (candidates == null || candidates.isEmpty()) {
            return "No pude generar una respuesta en este momento.";
        }
        Map<String, Object> first = (Map<String, Object>) candidates.get(0);
        Map<String, Object> content = (Map<String, Object>) first.get("content");
        if (content == null) return "No pude generar una respuesta en este momento.";
        List<Map<String, Object>> parts = (List<Map<String, Object>>) content.get("parts");
        if (parts == null || parts.isEmpty()) return "No pude generar una respuesta en este momento.";
        Object text = parts.get(0).get("text");
        return text != null ? text.toString().trim() : "";
    }

    // Un turno previo de la conversación (pregunta del usuario + respuesta del modelo)
    public record Turno(String pregunta, String respuesta) {}
}
