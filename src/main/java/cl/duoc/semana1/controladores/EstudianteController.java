package cl.duoc.semana1.controladores;


import cl.duoc.semana1.config.PageResponse;
import cl.duoc.semana1.dtos.EstudianteDTO;
import cl.duoc.semana1.mappers.EstudianteMapper;
import cl.duoc.semana1.servicios.IEstudianteService;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import cl.duoc.semana1.entidades.Estudiante;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
@Validated
public class EstudianteController {

    private final IEstudianteService service;     // trabaja con entidad
    private final EstudianteMapper mapper;        // convierte entidad <-> DTO

    @GetMapping
    public ResponseEntity<PageResponse<EstudianteDTO>> listar(Pageable pageable) {
        Page<Estudiante> page = service.listar(pageable);
        Page<EstudianteDTO> dtoPage = page.map(mapper::toDto);
        return ResponseEntity.ok(PageResponse.from(dtoPage));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstudianteDTO> obtenerPorId(@Positive @PathVariable Long id) {
        Estudiante e = service.obtenerPorId(id);
        return ResponseEntity.ok(mapper.toDto(e));
    }

    @PostMapping
    public ResponseEntity<EstudianteDTO> crear(
            @Valid @RequestBody EstudianteDTO body,
            UriComponentsBuilder uriBuilder) {

        Estudiante creado = service.crear(mapper.toEntity(body));
        EstudianteDTO out = mapper.toDto(creado);

        return ResponseEntity
                .created(
                        uriBuilder
                                .path("/api/estudiantes/{id}")
                                .buildAndExpand(out.id())
                                .toUri()
                )
                .body(out);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstudianteDTO> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EstudianteDTO body) {

        Estudiante actualizado = service.actualizar(id, mapper.toEntity(body));
        return ResponseEntity.ok(mapper.toDto(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}