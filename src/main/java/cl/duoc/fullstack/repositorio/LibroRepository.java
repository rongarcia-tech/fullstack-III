package cl.duoc.fullstack.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.duoc.fullstack.entidades.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
