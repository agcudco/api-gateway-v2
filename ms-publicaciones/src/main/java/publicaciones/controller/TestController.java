package publicaciones.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/test")
public class TestController {
    private final WebClient.Builder webClient;

    public TestController(WebClient.Builder webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/test/autor")
    public Mono<String> testAutor() {
        return webClient.build()
                .get()
                .uri("lb://SERVICIO-PUBLICACIONES/autor")
                .retrieve()
                .bodyToMono(String.class);
    }
}
