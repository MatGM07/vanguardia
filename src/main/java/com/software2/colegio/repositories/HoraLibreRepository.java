package com.software2.colegio.repositories;

import com.software2.colegio.models.HoraLibre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoraLibreRepository extends JpaRepository<HoraLibre, Long> {
    Optional<HoraLibre> findByDiaSemanaAndHoraInicioAndDocenteId(String diaSemana, LocalTime horaInicio, Long docenteId);
    List<HoraLibre> findByDocenteId(Long docenteId);
}
