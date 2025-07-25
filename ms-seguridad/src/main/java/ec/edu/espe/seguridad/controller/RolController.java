package ec.edu.espe.seguridad.controller;

import ec.edu.espe.seguridad.entidades.Rol;
import ec.edu.espe.seguridad.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolRepository rolRepository;

    /**
     * Listar todos los roles
     * Acceso: ROLE_USER y ROLE_ADMIN
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public List<Rol> getAll() {
        return rolRepository.findAll();
    }

    /**
     * Crear un nuevo rol
     * Acceso: solo ROLE_ADMIN
     */
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Rol> create(@RequestBody Rol rol) {
        if (rolRepository.findByNombre(rol.getNombre()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Rol saved = rolRepository.save(rol);
        return ResponseEntity.ok(saved);
    }

    /**
     * Actualizar un rol
     * Acceso: solo ROLE_ADMIN
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Rol> update(@PathVariable Long id, @RequestBody Rol rolDetails) {
        return rolRepository.findById(id)
                .map(rol -> {
                    rol.setNombre(rolDetails.getNombre());
                    Rol updated = rolRepository.save(rol);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Eliminar un rol
     * Acceso: solo ROLE_ADMIN
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return rolRepository.findById(id)
                .map(rol -> {
                    rolRepository.delete(rol);
                    return ResponseEntity.ok((Void) null);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}