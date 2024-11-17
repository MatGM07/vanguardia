package com.software2.colegio.controllers;


import com.software2.colegio.models.Admin;
import com.software2.colegio.models.Contenido;
import com.software2.colegio.models.Seccion;
import com.software2.colegio.services.ContenidoService;
import com.software2.colegio.services.SeccionService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/seccion")
public class SeccionController {

    @Autowired
    private SeccionService seccionService;

    @Autowired
    private ContenidoService contenidoService;


    @PostMapping
    public ResponseEntity<String> createSeccion(@RequestParam("nombre") String nombre, @RequestParam("tipo") String tipo, HttpSession session, HttpServletRequest request){
        Seccion nuevaSeccion = new Seccion();
        nuevaSeccion.setNombre(nombre);
        if (Objects.equals(tipo, "Pedagogico")){
            nuevaSeccion.setDescripcion("Proyecto Pedagogico");
        } else if (Objects.equals(tipo, "Aula")) {
            nuevaSeccion.setDescripcion("Proyecto de Aula");
        } else if (Objects.equals(tipo, "Productivo")) {
            nuevaSeccion.setDescripcion("Proyecto Productivo");
        }
        seccionService.save(nuevaSeccion);
        String referer = request.getHeader("Referer");
        if (referer != null && !referer.isBlank()) {
            // Redirigir de vuelta a la URL de origen
            URI redirectUri = URI.create(referer);
            return ResponseEntity.status(HttpStatus.SEE_OTHER)
                    .location(redirectUri)
                    .build();
        }

        // Si no hay Referer, redirigir a una URL predeterminada
        return ResponseEntity.status(HttpStatus.SEE_OTHER)
                .location(URI.create("/"))
                .build();
    }

    @PutMapping("/{id}/{año}/{accion}")
    public ResponseEntity<Seccion> updateSeccion(@PathVariable Long id, @PathVariable String año, @PathVariable String accion){

        Optional<Seccion> seccionOptional = seccionService.findById(id);
        Seccion seccion = seccionOptional.get();
        if (id == 10 || id == 18 || id == 21){
            System.out.println("Accion:");
            System.out.println(accion);
            if (Objects.equals(accion, "e")){
                System.out.println("Eliminar");
                List<String> listaAños = new ArrayList<>(Arrays.asList(seccion.getDescripcion().split(",")));
                listaAños.removeIf(añoAEliminar -> añoAEliminar.equals(año));

                String nuevaLista = listaAños.stream().collect(Collectors.joining(","));

                seccion.setDescripcion(nuevaLista);

                Seccion seccionUpdated = seccionService.save(seccion);

                String nombreSeccion="";

                if (id ==10){
                    nombreSeccion = "Rendiciones"+año;
                } else if (id == 18) {
                    nombreSeccion = "Convocatorias"+año;
                }else {
                    nombreSeccion = "Contrataciones"+año;
                }


                if (!contenidoService.findBySeccionNombre(nombreSeccion).isEmpty()){
                    List<Contenido> contenidoAnual = contenidoService.findBySeccionNombre(nombreSeccion);
                    for (Contenido contenido: contenidoAnual) {
                        contenidoService.deleteById(contenido.getId());
                    }
                }

                Optional<Seccion> seccionAño = seccionService.findByNombre(nombreSeccion);
                if (seccionAño.isPresent()) {
                    seccionService.deleteById(seccionAño.get().getId());
                }

                return ResponseEntity.ok(seccionUpdated);
            }else {
                System.out.println("Agregar");
                List<Integer> listaAños = Arrays.stream(seccion.getDescripcion().split(","))
                        .filter(s -> !s.isEmpty()) // Eliminar elementos vacíos
                        .map(s -> {
                            try {
                                return Integer.parseInt(s);
                            } catch (NumberFormatException e) {
                                return null; // Ignorar valores no numéricos
                            }
                        })
                        .filter(Objects::nonNull) // Eliminar valores nulos
                        .collect(Collectors.toList());

                try {
                    // Agregar el nuevo año
                    listaAños.add(Integer.parseInt(año));
                } catch (NumberFormatException e) {
                    return ResponseEntity.badRequest().body(null); // El año proporcionado no es válido
                }

                // Ordenar la lista de años
                Collections.sort(listaAños);

                // Convertir la lista de nuevo a un String separado por comas
                String añosOrdenados = listaAños.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));

                seccion.setDescripcion(añosOrdenados);

                Seccion seccionUpdated = seccionService.save(seccion);

                Seccion nuevaSeccion = new Seccion();
                String descripcion= "";
                String nombre= "";

                if (id ==10){
                    descripcion= "rendiciones de cuentas del año "+año;
                    nombre= "Rendiciones"+año;
                } else if (id == 18) {
                    descripcion= "rendiciones de cuentas del año "+año;
                    nombre= "Rendiciones"+año;
                }else {
                    descripcion= "contrataciones de cuentas del año "+año;
                    nombre= "Contrataciones"+año;
                }
                nuevaSeccion.setDescripcion(descripcion);
                nuevaSeccion.setNombre(nombre);

                seccionService.save(nuevaSeccion);

                return ResponseEntity.ok(seccionUpdated);
            }

        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeccion(@PathVariable Long id){
        if (seccionService.findById(id).isPresent()) {
            String nombreSeccion = seccionService.findById(id).get().getNombre();
            if (!contenidoService.findBySeccionNombre(nombreSeccion).isEmpty()){
                List<Contenido> contenidoTotal = contenidoService.findBySeccionNombre(nombreSeccion);
                for (Contenido contenido: contenidoTotal) {
                    contenidoService.deleteById(contenido.getId());
                }
            }
            seccionService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
