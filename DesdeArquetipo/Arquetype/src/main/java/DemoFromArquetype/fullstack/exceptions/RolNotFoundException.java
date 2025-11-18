package DemoFromArquetype.fullstack.exceptions;

public class RolNotFoundException extends RuntimeException {
    public RolNotFoundException(Long id) {
        super("Rol " + id + " not found");
    }

    public RolNotFoundException(String nombre) {
        super("Rol " + nombre + " not found");
    }
}

