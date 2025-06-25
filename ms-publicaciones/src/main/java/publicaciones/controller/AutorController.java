package publicaciones.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import publicaciones.dto.AutorDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Autor;
import publicaciones.service.AutorService;

import java.util.List;

@RestController
@RequestMapping("/autor")
public class AutorController {

    @Autowired
    private AutorService service;

    @GetMapping
    public List<ResponseDto> listarAutores() {
        return service.listarAutores();
    }

    @PostMapping
    public ResponseDto crearAutor(@RequestBody AutorDto autor) {
        return service.crearAutor(autor);
    }

    @PutMapping("/{id}")
    public ResponseDto actualizarAutor(@PathVariable Long id, @RequestBody AutorDto autor) {
        return service.actualizarAutor(id, autor);
    }

    @GetMapping("/{id}")
    public ResponseDto buscarPorId(@PathVariable Long id) {
        return service.buscarAutorPorId(id);
    }
}
