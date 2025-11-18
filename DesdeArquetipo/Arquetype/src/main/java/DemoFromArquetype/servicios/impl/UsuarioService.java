package DemoFromArquetype.servicios.impl;

import DemoFromArquetype.entidades.Rol;
import DemoFromArquetype.entidades.Usuario;
import DemoFromArquetype.exceptions.BusinessRuleException;
import DemoFromArquetype.exceptions.UsuarioNotFoundException;
import DemoFromArquetype.repositorio.RolRepository;
import DemoFromArquetype.repositorio.UsuarioRepository;
import DemoFromArquetype.servicios.IUsuarioService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService implements IUsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Page<Usuario> listar(Pageable pageable) {
        return usuarioRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario con ID={} no encontrado", id);
                    return new UsuarioNotFoundException(id);
                });
    }

    @Override
    @Transactional
    public Usuario crear(@Valid Usuario usuario, Set<String> nombresRoles) {
        validarUnicidad(usuario);

        // Encriptar password
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));

        // Resolver roles
        Set<Rol> roles = cargarRolesPorNombre(nombresRoles);
        usuario.setRoles(roles);

        Usuario guardado = usuarioRepository.save(usuario);
        log.info("Usuario creado con ID={}, username={}", guardado.getId(), guardado.getUsername());
        return guardado;
    }

    @Override
    @Transactional
    public Usuario actualizar(Long id, @Valid Usuario datos, Set<String> nombresRoles) {
        Usuario existente = usuarioRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuario con ID={} no encontrado para actualizar", id);
                    return new UsuarioNotFoundException(id);
                });

        // Validar unicidad si cambia username o email
        if (!existente.getUsername().equals(datos.getUsername())
                || (existente.getEmail() != null && !existente.getEmail().equals(datos.getEmail()))
                || (existente.getEmail() == null && datos.getEmail() != null)) {
            validarUnicidad(datos, id);
        }

        existente.setUsername(datos.getUsername());
        existente.setEnabled(datos.isEnabled());
        existente.setEmail(datos.getEmail());

        // Actualizar password solo si viene no nulo y no vacío
        if (datos.getPassword() != null && !datos.getPassword().isBlank()) {
            existente.setPassword(passwordEncoder.encode(datos.getPassword()));
        }

        // Actualizar roles
        Set<Rol> roles = cargarRolesPorNombre(nombresRoles);
        existente.setRoles(roles);

        Usuario actualizado = usuarioRepository.save(existente);
        log.info("Usuario actualizado con ID={}", actualizado.getId());
        return actualizado;
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            log.warn("No se puede eliminar, usuario con ID={} no existe", id);
            throw new UsuarioNotFoundException(id);
        }
        usuarioRepository.deleteById(id);
        log.info("Usuario eliminado con ID={}", id);
    }

    // ----------------- Métodos privados de apoyo -----------------

    private void validarUnicidad(Usuario usuario) {
        validarUnicidad(usuario, null);
    }

    private void validarUnicidad(Usuario usuario, Long idActual) {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            if (idActual == null ||
                    usuarioRepository.findByUsername(usuario.getUsername())
                            .map(u -> !u.getId().equals(idActual))
                            .orElse(true)) {
                throw new BusinessRuleException("El nombre de usuario ya está en uso");
            }
        }

        if (usuario.getEmail() != null && !usuario.getEmail().isBlank()
                && usuarioRepository.existsByEmail(usuario.getEmail())) {
            usuarioRepository.findAll().stream()
                    .filter(u -> usuario.getEmail().equals(u.getEmail()))
                    .filter(u -> idActual == null || !u.getId().equals(idActual))
                    .findAny()
                    .ifPresent(u -> {
                        throw new BusinessRuleException("El email ya está en uso");
                    });
        }
    }

    private Set<Rol> cargarRolesPorNombre(Set<String> nombresRoles) {
        if (nombresRoles == null || nombresRoles.isEmpty()) {
            throw new BusinessRuleException("Debe indicar al menos un rol");
        }

        Set<Rol> result = new HashSet<>();
        for (String nombre : nombresRoles) {
            var rol = rolRepository.findByNombre(nombre)
                    .orElseThrow(() -> new BusinessRuleException("Rol no encontrado: " + nombre));
            result.add(rol);
        }
        return result;
    }
}
