package DemoFromArquetype.fullstack.servicios.impl;

import DemoFromArquetype.fullstack.entidades.Rol;
import DemoFromArquetype.fullstack.exceptions.BusinessRuleException;
import DemoFromArquetype.fullstack.exceptions.RolNotFoundException;
import DemoFromArquetype.fullstack.repositorio.RolRepository;
import DemoFromArquetype.fullstack.servicios.IRolService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolService implements IRolService {

    private static final Logger log = LoggerFactory.getLogger(RolService.class);

    private final RolRepository rolRepository;

    @Override
    @Transactional
    public Page<Rol> listar(Pageable pageable) {
        return rolRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }

    @Override
    @Transactional
    public Rol obtenerPorId(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Rol con ID={} no encontrado", id);
                    return new RolNotFoundException(id);
                });
    }

    @Override
    @Transactional
    public Rol crear(@Valid Rol rol) {
        if (rolRepository.existsByNombre(rol.getNombre())) {
            throw new BusinessRuleException("El rol ya existe: " + rol.getNombre());
        }
        Rol guardado = rolRepository.save(rol);
        log.info("Rol creado con ID={}, nombre={}", guardado.getId(), guardado.getNombre());
        return guardado;
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!rolRepository.existsById(id)) {
            log.warn("No se puede eliminar, rol con ID={} no existe", id);
            throw new RolNotFoundException(id);
        }
        rolRepository.deleteById(id);
        log.info("Rol eliminado con ID={}", id);
    }
}
