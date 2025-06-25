package publicaciones.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity(name = "papers")
@Getter
@Setter
public class Articulo extends Publicacion {
    private String revista;
    @Column(nullable = false, unique = true)
    private String doi;
    private String areaInvestigacion;
    private Date fechaPublicacion;

    @ManyToOne
    @JoinColumn(name = "id_autor")
    private Autor autor;
}
