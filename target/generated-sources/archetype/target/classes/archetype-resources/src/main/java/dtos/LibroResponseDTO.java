#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dtos;

public record LibroResponseDTO(
        Long id,
        String titulo,
        String autor,
        String anio,
        String genero

) {}
