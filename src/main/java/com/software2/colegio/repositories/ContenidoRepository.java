package com.software2.colegio.repositories;

import com.software2.colegio.models.Contenido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContenidoRepository extends JpaRepository<Contenido, Long> {
    List<Contenido> findBySeccionNombre(String nombre);
}

