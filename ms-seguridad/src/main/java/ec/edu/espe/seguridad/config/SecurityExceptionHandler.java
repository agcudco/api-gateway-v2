package ec.edu.espe.seguridad.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityExceptionHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        response.getWriter().write("""
            {
                "timestamp": "%s",
                "status": 403,
                "error": "Acceso denegado",
                "message": "No tienes permiso para acceder a este recurso",
                "path": "%s"
            }
            """.formatted(System.currentTimeMillis(), request.getRequestURI()));
    }
}