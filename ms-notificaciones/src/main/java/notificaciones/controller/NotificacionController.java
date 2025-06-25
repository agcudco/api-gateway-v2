package notificaciones.controller;

import jakarta.annotation.Resource;
import notificaciones.entity.Notificacion;
import notificaciones.services.NotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
public class NotificacionController {
    @Autowired
    private NotificacionService service;

    @GetMapping
    public List<Notificacion> obtenerTodas() {
        return service.obtenerTodas();
    }
}
