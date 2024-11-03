package com.software2.colegio.controllers;

import com.software2.colegio.models.*;
import com.software2.colegio.services.AllReunionService;
import com.software2.colegio.services.DocenteService;
import com.software2.colegio.services.HoraLibreService;
import com.software2.colegio.services.ReunionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/reunion")
public class ReunionController {
    @Autowired
    private ReunionService reunionService;

    @Autowired
    private AllReunionService allReunionService;
    @Autowired
    private DocenteService docenteService;
    @Autowired
    private HoraLibreService horaLibreService;

    @GetMapping
    public List<Reunion> getAllReunions() {
        return reunionService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reunion> getReunionById(@PathVariable Long id) {
        Optional<Reunion> reunion = reunionService.findById(id);
        return reunion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Reunion> createReunion(@RequestBody ReunionRequest reunionRequest, HttpSession session) {

        System.out.println("PASO 1");
        Acudiente acudiente = (Acudiente) session.getAttribute("user");
        System.out.println("PASO 2");
        LocalDate fecha = reunionRequest.getDia();
        LocalTime hora = reunionRequest.getHora();
        System.out.println("PASO 3");
        if (fecha == null || hora == null || reunionRequest.getDescripcion() == null) {
            return ResponseEntity.badRequest().build(); // 400 si hay datos inválidos
        }

        DayOfWeek diadelasemana = fecha.getDayOfWeek();
        System.out.println("PASO 4");
        String diaEnEspañol = diadelasemana.getDisplayName(java.time.format.TextStyle.FULL, new Locale("es"));
        diaEnEspañol = diaEnEspañol.substring(0, 1).toUpperCase() + diaEnEspañol.substring(1).toLowerCase();
        System.out.println(diaEnEspañol);
        Optional<HoraLibre> horaLibreOptional = horaLibreService.findByDiaSemanaAndHoraInicioAndDocenteId(diaEnEspañol,hora,reunionRequest.getIdDocente());
        System.out.println(horaLibreOptional);
        if (!horaLibreOptional.isPresent()) {
            return ResponseEntity.notFound().build(); // 404 si no se encuentra la hora libre
        }
        System.out.println("PASO 6");
        HoraLibre horalibrereal = horaLibreOptional.get();

        Reunion reunion = new Reunion();
        reunion.setDescripcion(reunionRequest.getDescripcion());
        reunion.setHora(hora);
        reunion.setFecha(fecha);

        Optional<Docente> docenteOptional = docenteService.findById(reunionRequest.getIdDocente());

        if (!docenteOptional.isPresent()) {
            return ResponseEntity.notFound().build(); // 404 si no se encuentra el docente
        }

        Docente docenteObtenido = docenteOptional.get();

        DocenteReunion docenteReunion = new DocenteReunion();
        docenteReunion.setDocente(docenteObtenido);


        AcudienteReunion acudienteReunion = new AcudienteReunion();
        acudienteReunion.setAcudiente(acudiente);
        System.out.println("PASO 7");
        allReunionService.guardarTodo(reunion,docenteReunion,acudienteReunion,horalibrereal);

        return ResponseEntity.ok(reunion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reunion> updateReunion(@PathVariable Long id, @RequestBody Reunion reunionDetails) {
        Optional<Reunion> reunion = reunionService.findById(id);
        if (reunion.isPresent()) {
            Reunion updatedReunion = reunion.get();
            updatedReunion.setFecha(reunionDetails.getFecha());
            updatedReunion.setHora(reunionDetails.getHora());
            updatedReunion.setDescripcion(reunionDetails.getDescripcion());
            return ResponseEntity.ok(reunionService.save(updatedReunion));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReunion(@PathVariable Long id) {
        if (reunionService.findById(id).isPresent()) {
            reunionService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
