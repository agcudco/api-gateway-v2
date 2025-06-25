package ec.edu.espe.sincronizacion.services;

import ec.edu.espe.sincronizacion.dto.HoraClienteDto;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SincronizacionService {
    private final Map<String, Long> tiemposClientes = new ConcurrentHashMap<>();

    private static int INTERVALO_SEGUNDOS = 10;

    public void registarTiempo(HoraClienteDto dto) {
        tiemposClientes.put(dto.getNombreNodo(), dto.getHoraEnviada());
    }

    public void sincronizarRelojes() {
        if (tiemposClientes.size() >= 2) {
            long ahora = Instant.now().toEpochMilli();
            long promedio = (ahora + tiemposClientes.values().stream().mapToLong(Long::longValue).sum())
                    / (tiemposClientes.size() + 1);

            tiemposClientes.clear();
            enviarAjusteRelojes(promedio);
        }
    }

    public void enviarAjusteRelojes(long horaServidor) {
        System.out.println("Ajustando relojes a la hora: " + horaServidor);
    }
}
