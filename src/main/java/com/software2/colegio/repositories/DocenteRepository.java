package com.software2.colegio.repositories;

import com.software2.colegio.models.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    Docente findByCorreo(String correo);
}
