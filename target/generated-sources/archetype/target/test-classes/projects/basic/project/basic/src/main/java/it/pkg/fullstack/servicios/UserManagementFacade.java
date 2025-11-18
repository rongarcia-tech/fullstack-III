package it.pkg.fullstack.servicios;

import it.pkg.fullstack.dtos.UserRegistrationRequest;
import it.pkg.fullstack.entidades.Usuario;

public interface UserManagementFacade {

    Usuario crearUsuarioConRol(UserRegistrationRequest request);

    void asignarRolAUsuario(String username, String rolNombre);
}