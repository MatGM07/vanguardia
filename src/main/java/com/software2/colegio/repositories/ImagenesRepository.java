package com.software2.colegio.repositories;

import com.software2.colegio.models.Imagenes;
import com.software2.colegio.models.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImagenesRepository extends JpaRepository<Imagenes, Long> {
}
