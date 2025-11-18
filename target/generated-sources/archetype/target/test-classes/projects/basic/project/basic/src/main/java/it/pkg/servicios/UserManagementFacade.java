package it.pkg.servicios;

import it.pkg.dtos.UserRegistrationRequest;
import it.pkg.entidades.Usuario;

public interface UserManagementFacade {

    Usuario crearUsuarioConRol(UserRegistrationRequest request);

    void asignarRolAUsuario(String username, String rolNombre);
}