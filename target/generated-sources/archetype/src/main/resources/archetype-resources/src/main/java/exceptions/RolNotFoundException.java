#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.exceptions;

public class RolNotFoundException extends RuntimeException {
    public RolNotFoundException(Long id) {
        super("Rol " + id + " not found");
    }

    public RolNotFoundException(String nombre) {
        super("Rol " + nombre + " not found");
    }
}

