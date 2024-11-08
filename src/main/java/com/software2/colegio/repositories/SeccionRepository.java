package com.software2.colegio.repositories;

import com.software2.colegio.models.Contenido;
import com.software2.colegio.models.Seccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeccionRepository extends JpaRepository<Seccion, Long> {

}