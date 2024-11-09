package com.software2.colegio.controllers;

import com.software2.colegio.models.*;
import com.software2.colegio.models.Contenido;
import com.software2.colegio.services.ContenidoService;
import com.software2.colegio.services.ImagenesService;
import com.software2.colegio.services.SeccionService;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contenido")
public class ContenidoController {

    @Autowired
    private ContenidoService contenidoService;
    @Autowired
    private SeccionService seccionService;
    @Autowired
    private ImagenesService imagenesService;

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
    public ResponseEntity<String> createContenido(@RequestParam("titulo") String titulo, @RequestParam(value="texto",required = false) String descripcion, @RequestParam(value = "archivo",required = false) List<MultipartFile> archivos, @RequestParam("seccionid") Long seccionid,HttpSession session){

        Optional<Seccion> seccion = seccionService.findById(seccionid);

        Seccion seccionencontrada = seccion.get();
        try {
            Contenido contenido = new Contenido();
            String role = (String) session.getAttribute("role");

            if ("ROLE_ADMIN".equals(role)) {
                Admin admin = (Admin) session.getAttribute("user");
                contenido.setAdmin(admin);
            } else if ("ROLE_ESTUDIANTE".equals(role)) {
                Estudiante estudiante = (Estudiante) session.getAttribute("user");
                contenido.setEstudiante(estudiante);
            } else if ("ROLE_ACUDIENTE".equals(role)) {
                Acudiente acudiente = (Acudiente) session.getAttribute("user");
                contenido.setAcudiente(acudiente);
            } else if ("ROLE_DOCENTE".equals(role)) {
                Docente docente = (Docente) session.getAttribute("user");
                contenido.setDocente(docente);
            }

            contenido.setTitulo(titulo);
            if (descripcion != null){
                contenido.setDescripcion(descripcion);
            }
            contenido.setFecha_creacion(LocalDate.now());
            contenido.setHora_creacion(LocalTime.now());
            contenido.setSeccion(seccionencontrada);

            // Guardar el contenido en la base de datos
            Contenido contenidoGuardado = contenidoService.save(contenido);

            if (archivos != null && !archivos.isEmpty()) {  // Verificar que haya elementos en la lista
                String uploadsDir = "src/main/resources/static/uploads/";
                Path uploadPath = Paths.get(uploadsDir);

                if (!Files.exists(uploadPath)) {
                    try {
                        Files.createDirectories(uploadPath);
                    } catch (IOException e) {
                        if (e instanceof AccessDeniedException) {
                            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                    .body("Error: No tienes permiso para acceder a la ubicación especificada.");
                        } else {
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body("Error: No se pudo crear el directorio. Verifica la ruta.");
                        }
                    }
                }

                for (MultipartFile archivo : archivos) {
                    if (!archivo.isEmpty()) {  // Verificar si el archivo tiene contenido
                        String nombreArchivo = archivo.getOriginalFilename();
                        String rutaArchivo = uploadsDir + nombreArchivo;

                        // Guardar archivo en el sistema
                        Path ruta = Paths.get(rutaArchivo);
                        Files.write(ruta, archivo.getBytes());

                        // Crear instancia de Imagenes y asociarla al contenido
                        Imagenes imagen = new Imagenes();
                        imagen.setTitulo(nombreArchivo);
                        imagen.setDireccion("/uploads/" + nombreArchivo);  // Ruta relativa
                        imagen.setContenido(contenidoGuardado);

                        // Guardar imagen en la base de datos
                        imagenesService.save(imagen);
                    }
                }
            }

            return ResponseEntity.ok("Comunicado y archivos guardados exitosamente.");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al guardar archivos.");
        }
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContenido(@PathVariable Long id) {
        if (contenidoService.findById(id).isPresent()) {
            System.out.println("holaa");
            System.out.println(id);
            contenidoService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}
