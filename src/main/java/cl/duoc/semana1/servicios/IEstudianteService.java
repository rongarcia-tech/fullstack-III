package cl.duoc.semana1.servicios;

import cl.duoc.semana1.entidades.Estudiante;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IEstudianteService {
    Page<Estudiante> listar(Pageable pageable);
    Estudiante obtenerPorId(Long id);
    Estudiante crear(@Valid Estudiante estudiante);
    Estudiante actualizar(Long id, @Valid Estudiante estudiante);
    void eliminar(Long id);
}
