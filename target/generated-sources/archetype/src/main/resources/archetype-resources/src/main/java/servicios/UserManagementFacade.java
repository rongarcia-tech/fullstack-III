#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.servicios;

import ${package}.dtos.UserRegistrationRequest;
import ${package}.entidades.Usuario;

public interface UserManagementFacade {

    Usuario crearUsuarioConRol(UserRegistrationRequest request);

    void asignarRolAUsuario(String username, String rolNombre);
}