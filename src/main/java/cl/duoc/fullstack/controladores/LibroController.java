package cl.duoc.fullstack.controladores;


import cl.duoc.fullstack.config.PageResponse;
import cl.duoc.fullstack.dtos.LibroRequestDTO;
import cl.duoc.fullstack.dtos.LibroResponseDTO;
import cl.duoc.fullstack.mappers.LibroMapper;
import cl.duoc.fullstack.servicios.ILibroService;
import jakarta.validation.constraints.Positive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.fullstack.entidades.Libro;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/libros")
@RequiredArgsConstructor
@Validated
public class LibroController {

    private final ILibroService service;
    private final LibroMapper mapper;

    @GetMapping
    public ResponseEntity<PageResponse<LibroResponseDTO>> listar( @PageableDefault(sort = "titulo", direction = Sort.Direction.ASC)Pageable pageable) {
        Page<Libro> page = service.listar(pageable);
        Page<LibroResponseDTO> dtoPage = page.map(mapper::toResponse);
        return ResponseEntity.ok(PageResponse.from(dtoPage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> obtenerPorId(@Positive @PathVariable Long id) {
        Libro e = service.obtenerPorId(id);
        return ResponseEntity.ok(mapper.toResponse(e));
    }

    @PostMapping
    public ResponseEntity<LibroResponseDTO> crear(@Valid @RequestBody LibroRequestDTO request) {

        Libro entity = LibroMapper.toEntity(request);
        Libro saved = service.crear(entity);
        LibroResponseDTO response = mapper.toResponse(saved);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody LibroRequestDTO body) {

        Libro actualizado = service.actualizar(id, LibroMapper.toEntity(body));
        return ResponseEntity.ok(mapper.toResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}