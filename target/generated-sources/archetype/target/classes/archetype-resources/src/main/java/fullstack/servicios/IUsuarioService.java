#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.fullstack.servicios;

import ${package}.fullstack.entidades.Usuario;
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
