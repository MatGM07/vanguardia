package com.software2.colegio.repositories;

import com.software2.colegio.models.Acudiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcudienteRepository extends JpaRepository<Acudiente, Long> {
    Acudiente findByCorreo(String correo);
}
