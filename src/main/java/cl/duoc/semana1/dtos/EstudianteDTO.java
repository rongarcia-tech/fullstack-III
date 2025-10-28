package cl.duoc.semana1.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EstudianteDTO(
        Long id,
        @NotBlank String nombre,
        @NotBlank String apellido
) {}
