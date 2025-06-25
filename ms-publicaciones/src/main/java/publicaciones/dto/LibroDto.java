package publicaciones.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroDto {
    //ATRIBUTOS DEL PADRE
    private String titulo;
    private int anioPublicacion;
    private String editorial;
    private String isbn;
    private String resumen;
    private String idioma;
    //ATRIBUTOS DE LIBRO
    private String genero;
    private int numeroPaginas;
    private String edicion;
    
    private Long idAutor;
}
