package ec.edu.espe.seguridad.service;


import ec.edu.espe.seguridad.entidades.Rol;
import ec.edu.espe.seguridad.entidades.Usuario;
import ec.edu.espe.seguridad.repository.RolRepository;
import ec.edu.espe.seguridad.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario crearUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        if (usuario.getRoles() == null || usuario.getRoles().isEmpty()) {
            Rol rolUsuario = rolRepository.findByNombre("ROLE_USER")
                    .orElseGet(() -> {
                        Rol nuevoRol = new Rol(); // Usa el constructor sin argumentos
                        nuevoRol.setNombre("ROLE_USER");
                        return rolRepository.save(nuevoRol);
                    });
            usuario.setRoles(Set.of(rolUsuario));
        }

        return usuarioRepository.save(usuario);
    }
}
