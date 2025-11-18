package DemoFromArquetype.fullstack.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import DemoFromArquetype.fullstack.entidades.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
