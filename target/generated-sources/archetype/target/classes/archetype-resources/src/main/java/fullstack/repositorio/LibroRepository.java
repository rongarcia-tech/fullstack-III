#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.fullstack.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import ${package}.fullstack.entidades.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
