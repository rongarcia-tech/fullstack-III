package DemoFromArquetype.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import DemoFromArquetype.entidades.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long> {

}
