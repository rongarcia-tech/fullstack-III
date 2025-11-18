#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.fullstack.servicios;

import ${package}.fullstack.dtos.UserRegistrationRequest;
import ${package}.fullstack.entidades.Usuario;

public interface UserManagementFacade {

    Usuario crearUsuarioConRol(UserRegistrationRequest request);

    void asignarRolAUsuario(String username, String rolNombre);
}