package notificaciones.services;

import notificaciones.dto.NotificacionDto;
import notificaciones.entity.Notificacion;
import notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService {

    @Autowired
    private NotificacionRepository repository;

    public void guardarNotificacion(NotificacionDto dto) {
        Notificacion notificacion = new Notificacion();
        notificacion.setMensaje(dto.getMensaje());
        notificacion.setTipo(dto.getTipo());
        notificacion.setFecha(LocalDateTime.now());
        repository.save(notificacion);
    }

    public List<Notificacion> obtenerTodas() {
        return repository.findAll();
    }
}
