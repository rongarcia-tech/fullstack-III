package cl.duoc.fullstack.servicios;

import cl.duoc.fullstack.entidades.Usuario;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface IUsuarioService {

    Page<Usuario> listar(Pageable pageable);

    Usuario obtenerPorId(Long id);

    /**
     * Crea un usuario con los roles indicados por nombre (ej: "ROLE_USER", "ROLE_ADMIN").
     */
    Usuario crear(@Valid Usuario usuario, Set<String> nombresRoles);

    /**
     * Actualiza usuario y roles asociados.
     */
    Usuario actualizar(Long id, @Valid Usuario usuario, Set<String> nombresRoles);

    void eliminar(Long id);
}
