package DemoFromArquetype.dtos;

import DemoFromArquetype.entidades.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LibroRequestDTO(
        @NotBlank(message = "El título es obligatorio")
        @Size(max = 255)
        String titulo,

        @NotBlank(message = "El autor es obligatorio")
        @Size(max = 255)
        String autor,

        // Año como String validado a 4 dígitos, o usa Integer con @Min/@Max
        @NotBlank(message = "El año es obligatorio")
        @Pattern(regexp = "\\d{4}", message = "El año debe tener 4 dígitos")
        String anio,

        @NotNull(message = "El género es obligatorio")
        Genero genero
) {}
