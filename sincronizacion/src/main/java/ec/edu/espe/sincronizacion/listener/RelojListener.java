package ec.edu.espe.sincronizacion.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import ec.edu.espe.sincronizacion.dto.HoraClienteDto;
import ec.edu.espe.sincronizacion.services.SincronizacionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RelojListener {

    @Autowired
    private SincronizacionService sincronizacionService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = "reloj.solicitud")
    public void recibirSolicitud(String mensajeJson) {
        try {
            HoraClienteDto dto = objectMapper.readValue(mensajeJson, HoraClienteDto.class);
            System.out.println("Solicitud recibida: " + dto);
            sincronizacionService.registarTiempo(dto);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
