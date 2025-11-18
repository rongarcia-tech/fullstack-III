package it.pkg.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import it.pkg.entidades.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
