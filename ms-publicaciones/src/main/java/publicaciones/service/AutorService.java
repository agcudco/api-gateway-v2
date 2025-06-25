package publicaciones.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import publicaciones.dto.AutorDto;
import publicaciones.dto.ResponseDto;
import publicaciones.model.Autor;
import publicaciones.repository.AutorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private NotificacionProducer producer;

    public ResponseDto crearAutor(AutorDto dato) {
        Autor autor = new Autor();
        autor.setNombre(dato.getNombre());
        autor.setApellido(dato.getApellido());
        autor.setEmail(dato.getEmail());
        autor.setOrcid(dato.getOrcid());
        autor.setInstitucion(dato.getInstitucion());
        autor.setTelefono(dato.getTelefono());
        autor.setNacionalidad(dato.getNacionalidad());
        Autor saved = autorRepository.save(autor);

        //notificar el evento
        producer.enviarNotificacion(
                "Nuevo autor registrado: " + autor.getNombre() + autor.getApellido(),
                "nuevo-autor");

        return new ResponseDto(
                "Autor registrado correctamente",
                saved);
    }

    public ResponseDto actualizarAutor(Long id, AutorDto dto) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor con id: " + id + " no existe"));

        autor.setNombre(dto.getNombre());
        autor.setApellido(dto.getApellido());
        autor.setEmail(dto.getEmail());
        autor.setOrcid(dto.getOrcid());
        autor.setInstitucion(dto.getInstitucion());
        autor.setTelefono(dto.getTelefono());
        autor.setNacionalidad(dto.getNacionalidad());
        return new ResponseDto(
                "Autor actualizado correctamente",
                autorRepository.save(autor));
    }

    public List<ResponseDto> listarAutores() {
        return autorRepository.findAll().stream()
                .map(a -> new ResponseDto("Autor: " + a.getNombre(), a))
                .collect(Collectors.toList());
    }


    public ResponseDto buscarAutorPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autor con id: " + id + " no existe"));
        return new ResponseDto("Autor: " + autor.getNombre(), autor);
    }

}
