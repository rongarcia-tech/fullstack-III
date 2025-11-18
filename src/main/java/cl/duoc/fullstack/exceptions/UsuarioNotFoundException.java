package cl.duoc.fullstack.exceptions;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(Long id) {
        super("Usuario " + id + " not found");
    }
}

