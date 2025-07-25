package ec.edu.espe.seguridad.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
    private String token;
    private String tipo = "Bearer";
}