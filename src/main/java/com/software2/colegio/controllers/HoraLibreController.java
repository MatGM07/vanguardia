package com.software2.colegio.controllers;

import com.software2.colegio.models.*;
import com.software2.colegio.repositories.DocenteRepository;
import com.software2.colegio.services.HoraLibreService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/horas")
public class HoraLibreController {

    @Autowired
    private HoraLibreService horaLibreService;

    @Autowired
    private DocenteRepository docenteRepository;

    @PostMapping()
    public String guardarDisponibilidad(@RequestBody DisponibilidadRequest disponibilidadRequest, HttpSession session) {
        Docente docente = (Docente) session.getAttribute("user");



        List<HoraLibre> horasLibres = disponibilidadRequest.getDisponibilidad().stream().map(disp -> new HoraLibre(disp.getDia(), LocalTime.parse(disp.getHora()), docente)).toList();

        horasLibres.forEach(horaLibre -> horaLibreService.save(horaLibre));

        return "Disponibilidad guardada exitosamente";
    }


}
