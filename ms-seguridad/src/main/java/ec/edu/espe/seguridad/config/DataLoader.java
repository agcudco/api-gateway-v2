// src/main/java/ec/edu/espe/seguridad/config/DataLoader.java

package ec.edu.espe.seguridad.config;

import ec.edu.espe.seguridad.entidades.Rol;
import ec.edu.espe.seguridad.entidades.Usuario;
import ec.edu.espe.seguridad.repository.RolRepository;
import ec.edu.espe.seguridad.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Crear roles si no existen
        if (rolRepository.findByNombre("ROLE_USER").isEmpty()) {
            rolRepository.save(new Rol(null, "ROLE_USER"));
        }

        if (rolRepository.findByNombre("ROLE_ADMIN").isEmpty()) {
            rolRepository.save(new Rol(null, "ROLE_ADMIN"));
        }

        // Crear usuario admin si no existe
        if (usuarioRepository.findByUsername("admin").isEmpty()) {
            Rol rolAdmin = rolRepository.findByNombre("ROLE_ADMIN")
                    .orElseThrow(() -> new RuntimeException("Error: Rol ROLE_ADMIN no encontrado."));

            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña encriptada
            admin.setRoles(Set.of(rolAdmin));
            usuarioRepository.save(admin);
        }

        // Crear usuario user si no existe
        if (usuarioRepository.findByUsername("user").isEmpty()) {
            Rol rolUser = rolRepository.findByNombre("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("Error: Rol ROLE_USER no encontrado."));

            Usuario user = new Usuario();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRoles(Set.of(rolUser));
            usuarioRepository.save(user);
        }
    }
}