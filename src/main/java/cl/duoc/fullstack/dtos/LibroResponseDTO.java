package cl.duoc.fullstack.dtos;

public record LibroResponseDTO(
        Long id,
        String titulo,
        String autor,
        String anio,
        String genero

) {}
