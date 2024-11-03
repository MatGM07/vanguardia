package com.software2.colegio.controllers;

import com.software2.colegio.models.*;
import com.software2.colegio.models.Contenido;
import com.software2.colegio.services.ContenidoService;
import com.software2.colegio.services.SeccionService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contenido")
public class ContenidoController {

    @Autowired
    private ContenidoService contenidoService;
    @Autowired
    private SeccionService seccionService;

    @GetMapping
    public List<Contenido> getAllContenido(){
        return contenidoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contenido> getContenidoById(@PathVariable Long id){
        Optional<Contenido> contenido = contenidoService.findById(id);
        return contenido.map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }



    @PostMapping
    public ResponseEntity<Contenido> createContenido(@RequestBody Contenido contenido, HttpSession session){
        String role = (String) session.getAttribute("role");
          // Esto se verá en la consola del IDE
        if (role == "ROLE_ADMIN"){
            Admin admin = (Admin) session.getAttribute("user");
            contenido.setAdmin(admin);
        }
        if (role == "ROLE_ESTUDIANTE"){
            Estudiante estudiante = (Estudiante) session.getAttribute("user");
            contenido.setEstudiante(estudiante);
        }
        if (role == "ROLE_ACUDIENTE"){
            Acudiente acudiente = (Acudiente) session.getAttribute("user");
            contenido.setAcudiente(acudiente);
        }
        if (role == "ROLE_DOCENTE"){
            Docente docente = (Docente) session.getAttribute("user");
            contenido.setDocente(docente);
        }
        Contenido savedContenido = contenidoService.save(contenido);
        return ResponseEntity.ok(savedContenido);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contenido> updateContenido(@PathVariable Long id, @RequestBody Contenido contenidoDetails, HttpSession session){
        Optional<Contenido> contenidoOptional = contenidoService.findById(id);
        if (contenidoOptional.isPresent()) {
            Contenido contenidoToUpdate = contenidoOptional.get();
            contenidoToUpdate.setTitulo(contenidoDetails.getTitulo());
            contenidoToUpdate.setDescripcion(contenidoDetails.getDescripcion());
            contenidoToUpdate.setFecha_modificacion(LocalDate.now());
            contenidoToUpdate.setHora_modificacion(LocalTime.now());

            String role = (String) session.getAttribute("role");
            // Esto se verá en la consola del IDE
            if (role == "ROLE_ADMIN"){
                Admin admin = (Admin) session.getAttribute("user");
                if (admin != null) {
                    Hibernate.initialize(admin.getContenidos());  // Inicializar la colección de contenidos
                }
                contenidoToUpdate.setAdmin(admin);
            }
            if (role == "ROLE_ESTUDIANTE"){
                Estudiante estudiante = (Estudiante) session.getAttribute("user");
                if (estudiante != null) {
                    Hibernate.initialize(estudiante.getContenidos());  // Inicializar la colección de contenidos
                }
                contenidoToUpdate.setEstudiante(estudiante);
            }
            if (role == "ROLE_ACUDIENTE"){
                Acudiente acudiente = (Acudiente) session.getAttribute("user");
                if (acudiente != null) {
                    Hibernate.initialize(acudiente.getContenidos());  // Inicializar la colección de contenidos
                }
                contenidoToUpdate.setAcudiente(acudiente);
            }
            if (role == "ROLE_DOCENTE"){
                Docente docente = (Docente) session.getAttribute("user");
                if (docente != null) {
                    Hibernate.initialize(docente.getContenidos());  // Inicializar la colección de contenidos
                }
                contenidoToUpdate.setDocente(docente);
            }
            Contenido updatedContenido = contenidoService.save(contenidoToUpdate);
            return ResponseEntity.ok(updatedContenido);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
