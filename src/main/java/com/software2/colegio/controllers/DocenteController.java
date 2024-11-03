package com.software2.colegio.controllers;

import com.software2.colegio.models.Docente;
import com.software2.colegio.services.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/docente")
public class DocenteController {
    @Autowired
    private DocenteService docenteService;


    @GetMapping
    public List<Docente> getAllDocentes() {
        return docenteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Docente> getDocenteById(@PathVariable Long id) {
        Optional<Docente> docente = docenteService.findById(id);
        return docente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Docente createDocente(@RequestBody Docente docente) {
        return docenteService.save(docente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Docente> updateDocente(@PathVariable Long id, @RequestBody Docente docenteDetails) {
        Optional<Docente> docente = docenteService.findById(id);
        if (docente.isPresent()) {
            Docente updatedDocente = docente.get();
            updatedDocente.setNombre(docenteDetails.getNombre());
            updatedDocente.setApellido(docenteDetails.getApellido());
            updatedDocente.setCorreo(docenteDetails.getCorreo());
            updatedDocente.setContraseña(docenteDetails.getContraseña());
            updatedDocente.setTelefono(docenteDetails.getTelefono());
            updatedDocente.setContenidos(docenteDetails.getContenidos());
            return ResponseEntity.ok(docenteService.save(updatedDocente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocente(@PathVariable Long id) {
        if (docenteService.findById(id).isPresent()) {
            docenteService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
