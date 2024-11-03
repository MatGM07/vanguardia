package com.software2.colegio.controllers;

import com.software2.colegio.models.Acudiente;
import com.software2.colegio.services.AcudienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/acudiente")
public class AcudienteController {
    @Autowired
    private AcudienteService acudienteService;


    @GetMapping
    public List<Acudiente> getAllAcudientes() {
        return acudienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Acudiente> getAcudienteById(@PathVariable Long id) {
        Optional<Acudiente> acudiente = acudienteService.findById(id);
        return acudiente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Acudiente createAcudiente(@RequestBody Acudiente acudiente) {
        return acudienteService.save(acudiente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Acudiente> updateAcudiente(@PathVariable Long id, @RequestBody Acudiente acudienteDetails) {
        Optional<Acudiente> acudiente = acudienteService.findById(id);
        if (acudiente.isPresent()) {
            Acudiente updatedAcudiente = acudiente.get();
            updatedAcudiente.setNombre(acudienteDetails.getNombre());
            updatedAcudiente.setApellido(acudienteDetails.getApellido());
            updatedAcudiente.setCorreo(acudienteDetails.getCorreo());
            updatedAcudiente.setContraseña(acudienteDetails.getContraseña());
            updatedAcudiente.setEstudiante(acudienteDetails.getEstudiante());
            updatedAcudiente.setContenidos(acudienteDetails.getContenidos());
            updatedAcudiente.setTelefono(acudienteDetails.getTelefono());
            updatedAcudiente.setParentezco(acudienteDetails.getParentezco());
            return ResponseEntity.ok(acudienteService.save(updatedAcudiente));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcudiente(@PathVariable Long id) {
        if (acudienteService.findById(id).isPresent()) {
            acudienteService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
