#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.fullstack.servicios.facade;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ${package}.fullstack.dtos.UserRegistrationRequest;
import ${package}.fullstack.entidades.Rol;
import ${package}.fullstack.entidades.Usuario;
import ${package}.fullstack.repositorio.RolRepository;
import ${package}.fullstack.repositorio.UsuarioRepository;
import ${package}.fullstack.servicios.UserManagementFacade;

import java.util.Collections;

@Service
public class UserManagementFacadeImpl implements UserManagementFacade {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    public UserManagementFacadeImpl(UsuarioRepository usuarioRepository,
                                    RolRepository rolRepository,
                                    PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuario crearUsuarioConRol(UserRegistrationRequest request) {
        // 1. Buscar el rol
        Rol rol = rolRepository.findByNombre(request.rol())
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + request.rol()));

        // 2. Crear el usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(request.username());
        usuario.setPassword(passwordEncoder.encode(request.rawPassword()));
        usuario.setEmail(request.email());
        usuario.setEnabled(true);
        usuario.setRoles(Collections.singleton(rol)); // asumiendo Set<Rol>

        // 3. Guardar el usuario
        return usuarioRepository.save(usuario);
    }

    @Override
    public void asignarRolAUsuario(String username, String rolNombre) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado: " + username));

        Rol rol = rolRepository.findByNombre(rolNombre)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado: " + rolNombre));

        usuario.getRoles().add(rol);
        usuarioRepository.save(usuario);
    }
}
