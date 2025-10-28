package cl.duoc.semana1.servicios.impl;



import cl.duoc.semana1.exceptions.RecursoNoEncontrado;
import cl.duoc.semana1.servicios.IEstudianteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cl.duoc.semana1.entidades.Estudiante;
import cl.duoc.semana1.repositorio.EstudianteRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class EstudianteService implements IEstudianteService {

    private final EstudianteRepository repo;



    @Override
    public Page<Estudiante> listar(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public Estudiante obtenerPorId(Long id) {
        return repo.findById(id).orElseThrow(() -> new RecursoNoEncontrado("Estudiante", id));
    }

    @Override
    @Transactional
    public Estudiante crear(@Valid Estudiante estudiante) {
        // reglas de negocio previas si aplica (unicidades, etc.)
        return repo.save(estudiante);
    }

    @Override
    @Transactional
    public Estudiante actualizar(Long id, @Valid Estudiante in) {
        Estudiante existente = obtenerPorId(id);
        existente.setNombre(in.getNombre());
        existente.setApellido(in.getApellido());
        return repo.save(existente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) throw new RecursoNoEncontrado("Estudiante", id);
        repo.deleteById(id);
    }
}

