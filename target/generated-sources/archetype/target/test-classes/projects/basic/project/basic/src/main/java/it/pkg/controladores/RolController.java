package it.pkg.controladores;

import it.pkg.dtos.RolRequestDTO;
import it.pkg.dtos.RolResponseDTO;
import it.pkg.entidades.Rol;
import it.pkg.servicios.IRolService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RolController {

    private final IRolService rolService;

    @GetMapping
    public ResponseEntity<Page<RolResponseDTO>> listar(Pageable pageable) {
        Page<Rol> page = rolService.listar(pageable);
        Page<RolResponseDTO> dtoPage = page.map(r -> new RolResponseDTO(r.getId(), r.getNombre()));
        return ResponseEntity.ok(dtoPage);
    }

    // Si quieres una lista completa sin paginar:
    @GetMapping("/all")
    public ResponseEntity<List<RolResponseDTO>> listarTodos() {
        List<Rol> roles = rolService.listarTodos();
        List<RolResponseDTO> dtoList = roles.stream()
                .map(r -> new RolResponseDTO(r.getId(), r.getNombre()))
                .toList();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RolResponseDTO> obtenerPorId(@PathVariable Long id) {
        Rol rol = rolService.obtenerPorId(id);
        return ResponseEntity.ok(new RolResponseDTO(rol.getId(), rol.getNombre()));
    }

    @PostMapping
    public ResponseEntity<RolResponseDTO> crear(@Valid @RequestBody RolRequestDTO request) {
        Rol rol = new Rol(request.nombre());
        Rol creado = rolService.crear(rol);
        RolResponseDTO response = new RolResponseDTO(creado.getId(), creado.getNombre());
        URI location = URI.create("/api/roles/" + creado.getId());
        return ResponseEntity.created(location).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rolService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
