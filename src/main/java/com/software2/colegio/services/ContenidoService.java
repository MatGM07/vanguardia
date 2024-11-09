package com.software2.colegio.services;

import com.software2.colegio.models.Contenido;
import com.software2.colegio.models.Imagenes;
import com.software2.colegio.repositories.ContenidoRepository;
import com.software2.colegio.repositories.EstudianteRepository;
import com.software2.colegio.repositories.ImagenesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.nio.file.Files;

import java.io.IOException;

@Service
public class ContenidoService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ContenidoRepository contenidoRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private ImagenesRepository imagenesRepository;

    public List<Contenido> findAll(){
        return contenidoRepository.findAll();
    }

    public Optional<Contenido> findById(Long id){
        return contenidoRepository.findById(id);
    }

    public Contenido save(Contenido contenido){
        return contenidoRepository.save(contenido);
    }
    @Transactional
    public void deleteById(long id) {
        Optional<Contenido> contenidoOptional = contenidoRepository.findById(id);
        if (contenidoOptional.isPresent()) {
            Contenido contenido = contenidoOptional.get();

            // Desvincular imágenes
            if (contenido.getImagenes() != null && !contenido.getImagenes().isEmpty()) {
                for (Imagenes imagen : contenido.getImagenes()) {
                    try {
                        Path path = Paths.get("src/main/resources/static", imagen.getDireccion());
                        if (Files.exists(path)) {
                            System.out.println("El archivo existe: " + path.toString());
                        } else {
                            System.out.println("El archivo no existe en la ruta: " + path.toString());
                        }
                        Files.deleteIfExists(path); // Elimina la imagen del sistema de archivos
                        System.out.println("Imagen eliminada físicamente: " + imagen.getDireccion());
                    } catch (IOException e) {
                        System.err.println("Error al eliminar la imagen: " + e.getMessage());
                        e.printStackTrace();
                    }
                    imagen.setContenido(null); // Desvincular la relación de la imagen
                }
            }

            // Desvincular de otras entidades (ejemplo con secciones y otras relaciones)
            if (contenido.getSeccion() != null) {
                contenido.getSeccion().getContenidos().remove(contenido); // Remover de la colección de la otra entidad
                contenido.setSeccion(null); // Desvincular la referencia
            }

            if (contenido.getAcudiente() != null) {
                contenido.getAcudiente().getContenidos().remove(contenido);
                contenido.setAcudiente(null);
            }

            if (contenido.getDocente() != null) {
                contenido.getDocente().getContenidos().remove(contenido);
                contenido.setDocente(null);
            }

            if (contenido.getEstudiante() != null) {
                contenido.getEstudiante().getContenidos().remove(contenido);
                contenido.setEstudiante(null);
            }

            if (contenido.getAdmin() != null) {
                contenido.getAdmin().getContenidos().remove(contenido);
                contenido.setAdmin(null);
            }

            // Persistir los cambios antes de la eliminación
            entityManager.flush();

            // Ahora eliminar el contenido
            contenidoRepository.delete(contenido);
            System.out.println("Contenido con ID: " + contenido.getId() + " eliminado.");
        } else {
            System.out.println("Contenido no encontrado.");
        }
    }


    public List<Contenido> findBySeccionNombre(String nombre) {
        return contenidoRepository.findBySeccionNombre(nombre);
    }
}
