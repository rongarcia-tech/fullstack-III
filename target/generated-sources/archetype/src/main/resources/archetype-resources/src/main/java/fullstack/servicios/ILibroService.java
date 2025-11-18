#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.fullstack.servicios;

import ${package}.fullstack.entidades.Libro;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ILibroService {
    Page<Libro> listar(Pageable pageable);
    Libro obtenerPorId(Long id);
    Libro crear(@Valid Libro libro);
    Libro actualizar(Long id, @Valid Libro libro);
    void eliminar(Long id);
}
