package ec.edu.espe.seguridad.security;


import ec.edu.espe.seguridad.entidades.Usuario;
import ec.edu.espe.seguridad.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return org.springframework.security.core.userdetails.User
                .withUsername(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(usuario.getRoles().stream().map(r -> r.getNombre()).toArray(String[]::new))
                .build();
    }
}