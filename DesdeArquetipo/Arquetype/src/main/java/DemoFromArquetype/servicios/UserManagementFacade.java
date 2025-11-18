package DemoFromArquetype.servicios;

import DemoFromArquetype.dtos.UserRegistrationRequest;
import DemoFromArquetype.entidades.Usuario;

public interface UserManagementFacade {

    Usuario crearUsuarioConRol(UserRegistrationRequest request);

    void asignarRolAUsuario(String username, String rolNombre);
}