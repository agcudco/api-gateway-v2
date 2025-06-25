package publicaciones.dto;

import jakarta.persistence.AssociationOverrides;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutorDto {
    private String nombre;
    private String apellido;
    private String email;
    private String orcid;
    private String nacionalidad;
    private String telefono;
    private String institucion;
}
