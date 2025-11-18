#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.fullstack.dtos;

public record RolResponseDTO(
        Long id,
        String nombre
) {}
