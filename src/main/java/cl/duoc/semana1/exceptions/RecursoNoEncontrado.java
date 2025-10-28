package cl.duoc.semana1.exceptions;

public class RecursoNoEncontrado extends RuntimeException {
    public RecursoNoEncontrado(String recurso, Object id) {
        super(recurso + " con id " + id + " no existe");
    }
}