package DemoFromArquetype.fullstack.entidades;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Genero {
    FICCION("Ficción"),
    NO_FICCION("No ficción"),
    FANTASIA("Fantasía"),
    TERROR("Terror"),
    CIENCIA("Ciencia"),
    BIOGRAFIA("Biografía"),
    POESIA("Poesía"),
    INFANTIL("Infantil"),
    COMICS("Cómics"),
    OTRO("Otro");

    private final String label;

    Genero(String label) { this.label = label; }

    /** Lo que verás en el JSON (p. ej., "Ficción") */
    @JsonValue
    public String getLabel() { return label; }

    /** Permite enviar "FICCION" o "Ficción" y mapear correctamente */
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static Genero fromJson(String value) {
        if (value == null) return null;
        String v = value.trim();
        // Coincidencia por nombre del enum
        for (Genero g : values()) {
            if (g.name().equalsIgnoreCase(v)) return g;
        }
        // Coincidencia por etiqueta bonita
        for (Genero g : values()) {
            if (g.label.equalsIgnoreCase(v)) return g;
        }
        throw new IllegalArgumentException("Género inválido: " + value);
    }
}

