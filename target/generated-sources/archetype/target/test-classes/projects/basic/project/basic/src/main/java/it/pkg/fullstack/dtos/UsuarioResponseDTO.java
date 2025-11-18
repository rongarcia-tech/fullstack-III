package it.pkg.fullstack.dtos;

import java.util.Set;

public record UsuarioResponseDTO(
        Long id,
        String username,
        Boolean enabled,
        String email,
        Set<String> roles
) {}
