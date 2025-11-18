package it.pkg.exceptions;

public class LibroNotFoundException extends RuntimeException {
    public LibroNotFoundException(Long id) {
        super("Libro " + id + " not found");
    }
}