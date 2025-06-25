package ec.edu.espe.sincronizacion.config;

import ec.edu.espe.sincronizacion.services.SincronizacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Autowired
    private SincronizacionService sincronizacionService;

    @Scheduled(fixedRateString = "#{T(java.util.concurrent.TimeUnit).SECONDS.toMillis(${reloj.intervalo:10})}")
    public void sincronizarRelojes() {
        System.out.println("Ejecutando sincronización.....");
        sincronizacionService.sincronizarRelojes();
    }
}
