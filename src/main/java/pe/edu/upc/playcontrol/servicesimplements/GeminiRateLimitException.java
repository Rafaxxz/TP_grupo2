package pe.edu.upc.playcontrol.servicesimplements;

// Se lanza cuando la API de Gemini responde 429 (rate limit / cuota diaria excedida)
// o 503 (servicio saturado). El controller la traduce a un 503 amigable.
public class GeminiRateLimitException extends RuntimeException {
    public GeminiRateLimitException(String message) {
        super(message);
    }
}
