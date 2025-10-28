package cl.duoc.semana1.mappers;

import cl.duoc.semana1.dtos.EstudianteDTO;
import cl.duoc.semana1.entidades.Estudiante;
import org.springframework.stereotype.Component;

@Component
public class EstudianteMapper {
    public EstudianteDTO toDto(Estudiante e) {
        if (e == null) return null;
        return new EstudianteDTO(e.getId(), e.getNombre(), e.getApellido());
    }
    public Estudiante toEntity(EstudianteDTO d) {
        if (d == null) return null;
        Estudiante e = new Estudiante();
        e.setId(d.id());              // opcional: en create puedes ignorar ID
        e.setNombre(d.nombre());
        e.setApellido(d.apellido());
        return e;
    }
}

