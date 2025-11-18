#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.fullstack.servicios;

import ${package}.fullstack.entidades.Rol;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRolService {

    Page<Rol> listar(Pageable pageable);

    List<Rol> listarTodos();

    Rol obtenerPorId(Long id);

    Rol crear(@Valid Rol rol);

    void eliminar(Long id);
}
