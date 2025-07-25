package ec.edu.espe.seguridad.controller;


import ec.edu.espe.seguridad.dto.LoginRequest;
import ec.edu.espe.seguridad.dto.TokenResponse;
import ec.edu.espe.seguridad.entidades.Usuario;
import ec.edu.espe.seguridad.security.JwtUtil;
import ec.edu.espe.seguridad.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String username = authentication.getName();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);

        String token = jwtUtil.generarToken(username, claims);

        return ResponseEntity.ok(new TokenResponse(token,"Bearer"));
    }

    @PostMapping("/usuarios")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.crearUsuario(usuario));
    }
}