#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dtos;

import java.util.Set;

public record UsuarioResponseDTO(
        Long id,
        String username,
        Boolean enabled,
        String email,
        Set<String> roles
) {}
