package ec.edu.espe.seguridad.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-publicaciones", url = "http://localhost:8081")
public interface PublicacionClient {

    @GetMapping("/autores/{id}")
    Object obtenerAutor(@PathVariable Long id);
}
