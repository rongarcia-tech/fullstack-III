package it.pkg.fullstack.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import it.pkg.fullstack.entidades.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
