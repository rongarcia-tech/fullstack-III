package DemoFromArquetype.fullstack.controladores;

import DemoFromArquetype.fullstack.dtos.UserRegistrationRequest;
import DemoFromArquetype.fullstack.dtos.UsuarioRequestDTO;
import DemoFromArquetype.fullstack.dtos.UsuarioResponseDTO;
import DemoFromArquetype.fullstack.entidades.Usuario;
import DemoFromArquetype.fullstack.servicios.IUsuarioService;
import DemoFromArquetype.fullstack.servicios.UserManagementFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final IUsuarioService usuarioService;
    private final UserManagementFacade userManagementFacade;

    @GetMapping
    public ResponseEntity<Page<UsuarioResponseDTO>> listar(Pageable pageable) {
        Page<Usuario> page = usuarioService.listar(pageable);
        Page<UsuarioResponseDTO> dtoPage = page.map(this::mapToResponseDTO);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> obtenerPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);
        return ResponseEntity.ok(mapToResponseDTO(usuario));
    }

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crear(@Valid @RequestBody UsuarioRequestDTO request) {
        Usuario nuevo = mapToEntity(request);
        Usuario creado = usuarioService.crear(nuevo, request.roles());
        UsuarioResponseDTO response = mapToResponseDTO(creado);

        URI location = URI.create("/api/usuarios/" + creado.getId());
        return ResponseEntity.created(location).body(response);
    }
    //usando patron facade
    @PostMapping("/registrar")
    public ResponseEntity<Usuario> registrar(@RequestBody UserRegistrationRequest request) {
        Usuario usuario = userManagementFacade.crearUsuarioConRol(request);
        return ResponseEntity.ok(usuario);
    }
    //usando patron facade
     @PostMapping("/{username}/roles/{rol}")
    public ResponseEntity<Void> asignarRol(
            @PathVariable String username,
            @PathVariable String rol) {

        userManagementFacade.asignarRolAUsuario(username, rol);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO request
    ) {
        Usuario datos = mapToEntity(request);
        Usuario actualizado = usuarioService.actualizar(id, datos, request.roles());
        return ResponseEntity.ok(mapToResponseDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // --------- mapeos privados ---------

    private UsuarioResponseDTO mapToResponseDTO(Usuario usuario) {
        Set<String> roles = usuario.getRoles()
                .stream()
                .map(r -> r.getNombre())
                .collect(Collectors.toSet());

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getUsername(),
                usuario.isEnabled(),
                usuario.getEmail(),
                roles
        );
    }

    private Usuario mapToEntity(UsuarioRequestDTO request) {
        Usuario usuario = new Usuario();
        usuario.setUsername(request.username());
        usuario.setPassword(request.password());
        usuario.setEnabled(Boolean.TRUE.equals(request.enabled()));
        usuario.setEmail(request.email());
        return usuario;
    }
}
