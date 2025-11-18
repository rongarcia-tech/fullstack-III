package cl.duoc.fullstack.servicios;

import cl.duoc.fullstack.dtos.UserRegistrationRequest;
import cl.duoc.fullstack.entidades.Usuario;

public interface UserManagementFacade {

    Usuario crearUsuarioConRol(UserRegistrationRequest request);

    void asignarRolAUsuario(String username, String rolNombre);
}