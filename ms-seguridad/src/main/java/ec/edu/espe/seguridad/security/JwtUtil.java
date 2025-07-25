// src/main/java/ec/edu/espe/seguridad/security/JwtUtil.java

package ec.edu.espe.seguridad.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    private Key key;
    private final long expirationMs = 86400000; // 24 horas

    // Inicializa la clave después de inyectar 'secret'
    public void init() {
        if (secret == null) {
            throw new IllegalStateException("La propiedad 'jwt.secret' no está definida en application.yml");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Método para obtener la clave (usa init si es necesario)
    private Key getKey() {
        if (key == null) {
            init();
        }
        return key;
    }

    public String generarToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .setSubject(username)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(getKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims obtenerClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean esTokenValido(String token, String username) {
        try {
            return obtenerClaims(token).getSubject().equals(username)
                    && !obtenerClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean esTokenExpirado(String token) {
        try {
            return obtenerClaims(token).getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
}