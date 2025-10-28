package cl.duoc.semana1.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.semana1.entidades.Estudiante;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

}
