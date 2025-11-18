package DemoFromArquetype.fullstack.servicios;

import DemoFromArquetype.fullstack.dtos.UserRegistrationRequest;
import DemoFromArquetype.fullstack.entidades.Usuario;

public interface UserManagementFacade {

    Usuario crearUsuarioConRol(UserRegistrationRequest request);

    void asignarRolAUsuario(String username, String rolNombre);
}