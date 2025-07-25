package ec.edu.espe.seguridad.repository;

import ec.edu.espe.seguridad.entidades.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByNombre(String nombre);
}