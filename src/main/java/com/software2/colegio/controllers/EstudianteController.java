package com.software2.colegio.controllers;

import com.software2.colegio.models.Estudiante;
import com.software2.colegio.repositories.EstudianteRepository;
import com.software2.colegio.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService estudianteService;


    @GetMapping
    public List<Estudiante> getAllEstudiantes() {
        return estudianteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> getEstudianteById(@PathVariable Long id) {
        Optional<Estudiante> estudiante = estudianteService.findById(id);
        return estudiante.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Estudiante createEstudiante(@RequestBody Estudiante estudiante) {
        return estudianteService.save(estudiante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> updateEstudiante(@PathVariable Long id, @RequestBody Estudiante estudianteDetails) {
        Optional<Estudiante> estudiante = estudianteService.findById(id);
        if (estudiante.isPresent()) {
            Estudiante updatedEstudiante = estudiante.get();
            updatedEstudiante.setNombre(estudianteDetails.getNombre());
            updatedEstudiante.setApellido(estudianteDetails.getApellido());
            updatedEstudiante.setCorreo(estudianteDetails.getCorreo());
            updatedEstudiante.setContraseña(estudianteDetails.getContraseña());
            updatedEstudiante.setAcudientes(estudianteDetails.getAcudientes());
            updatedEstudiante.setContenidos(estudianteDetails.getContenidos());
            return ResponseEntity.ok(estudianteService.save(updatedEstudiante));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEstudiante(@PathVariable Long id) {
        if (estudianteService.findById(id).isPresent()) {
            estudianteService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
