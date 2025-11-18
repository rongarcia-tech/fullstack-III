package it.pkg.servicios.impl;



import it.pkg.exceptions.BusinessRuleException;
import it.pkg.exceptions.LibroNotFoundException;
import it.pkg.servicios.ILibroService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import it.pkg.entidades.Libro;
import it.pkg.repositorio.LibroRepository;

import java.util.Optional;

@Service
@Transactional
public class LibroService implements ILibroService {

    private static final Logger log = LoggerFactory.getLogger(LibroService.class.getName());

    private final LibroRepository repo;

    public LibroService(LibroRepository repo) {
        this.repo = repo;
    }


    @Override
    public Page<Libro> listar(Pageable pageable) {
        log.info("Obteniendo todos los libros");
        log.debug("Select * from libros");
        return repo.findAll(pageable);
    }

    @Override
    public Libro obtenerPorId(Long id) {
        Optional libro = repo.findById(id);
        if(!libro.isEmpty()){
            log.warn("Libro con ID="+id+", no emcontrado");
        }
        return repo.findById(id).orElseThrow(() ->  new LibroNotFoundException(id));
    }

    @Override
    @Transactional
    public Libro crear(@Valid Libro libro) {
        if (libro.getTitulo().length() < 3) throw new BusinessRuleException("Title must be length >= 0");
        log.info("Guardando el libro " + libro.getTitulo() + ", actualmente" );
        return repo.save(libro);
    }

    @Override
    @Transactional
    public Libro actualizar(Long id, @Valid Libro libro) {
        log.info("Actualizando el libro " + libro.getTitulo() + ", actualmente" );
        Libro existente = obtenerPorId(id);
        existente.setTitulo(libro.getTitulo());
        existente.setAutor(libro.getAutor());
        existente.setAnio(libro.getAnio());
        existente.setGenero(libro.getGenero());
        return repo.save(existente);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!repo.existsById(id)) {
            log.warn("No se puede eliminar el libro con ID="+id);
            throw new LibroNotFoundException(id);
        }
        repo.deleteById(id);
    }
}

