package it.pkg.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UsuarioRequestDTO(

        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(max = 50, message = "El nombre de usuario no puede superar los 50 caracteres")
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
        String password,

        @NotNull(message = "El estado 'enabled' es obligatorio")
        Boolean enabled,

        @Email(message = "El correo electrónico no es válido")
        @Size(max = 255, message = "El correo electrónico no puede superar los 255 caracteres")
        String email,

        // nombres de roles, por ejemplo: ["ROLE_USER", "ROLE_ADMIN"]
        @NotNull(message = "Debe indicar al menos un rol")
        @NotEmpty(message = "Debe indicar al menos un rol")
        Set<String> roles
) {}
