package it.pkg.fullstack.mappers;

import it.pkg.fullstack.dtos.LibroRequestDTO;
import it.pkg.fullstack.dtos.LibroResponseDTO;
import it.pkg.fullstack.entidades.Genero;
import it.pkg.fullstack.entidades.Libro;
import org.springframework.stereotype.Component;

@Component
public class LibroMapper {
    /** Crea entidad nueva desde el request */
    public static Libro toEntity(LibroRequestDTO dto) {
        if (dto == null) return null;
        Libro l = new Libro();
        l.setTitulo(trim(dto.titulo()));
        l.setAutor(trim(dto.autor()));
        l.setAnio(trim(dto.anio()));            // anio es String en tu entidad
        l.setGenero(dto.genero());              // Enum directo
        return l;
    }

    /** Actualiza una entidad existente (full update PUT) */
    public static void updateEntity(Libro target, LibroRequestDTO dto) {
        if (target == null || dto == null) return;
        target.setTitulo(trim(dto.titulo()));
        target.setAutor(trim(dto.autor()));
        target.setAnio(trim(dto.anio()));
        target.setGenero(dto.genero());
    }

    /** Convierte entidad a response DTO */
    public LibroResponseDTO toResponse(Libro l) {
        if (l == null) return null;
        // Si tu enum Genero tiene getLabel(), úsalo; si no, usa name()
        String generoOut = generoToString(l.getGenero());
        return new LibroResponseDTO(
                l.getId(),
                l.getTitulo(),
                l.getAutor(),
                l.getAnio(),
                generoOut
        );
    }

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }

    private static String generoToString(Genero g) {
        if (g == null) return null;
        // Si definiste en el enum un label “bonito”, cambia por g.getLabel()
        return g.name();
    }
}

