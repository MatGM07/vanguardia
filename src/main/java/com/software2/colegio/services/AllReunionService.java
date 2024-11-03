package com.software2.colegio.services;

import com.software2.colegio.models.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AllReunionService {

    private AcudienteReunionService acudienteReunionService;
    private DocenteReunionService docenteReunionService;
    private ReunionService reunionService;
    private HoraLibreService horaLibreService;

    @Autowired
    public AllReunionService(AcudienteReunionService acudienteReunionService, DocenteReunionService docenteReunionService, ReunionService reunionService, HoraLibreService horaLibreService) {
        this.acudienteReunionService = acudienteReunionService;
        this.docenteReunionService = docenteReunionService;
        this.reunionService = reunionService;
        this.horaLibreService = horaLibreService;
    }

    @Transactional
    public void guardarTodo(Reunion reunion, DocenteReunion docenteReunion, AcudienteReunion acudienteReunion, HoraLibre horaLibre) {

        Reunion newreunion = reunionService.save(reunion);

        AcudienteReunionId id = new AcudienteReunionId();
        id.setAcudienteId(acudienteReunion.getAcudiente().getId());
        id.setReunionId(newreunion.getId());

        acudienteReunion.setReunion(newreunion);
        acudienteReunion.setId(id);

        DocenteReunionId id2 = new DocenteReunionId();
        id2.setDocenteId(docenteReunion.getDocente().getId());
        id2.setReunionId(newreunion.getId());

        docenteReunion.setId(id2);
        docenteReunion.setReunion(newreunion);


        acudienteReunionService.save(acudienteReunion);
        docenteReunionService.save(docenteReunion);

        horaLibreService.deleteById(horaLibre.getId());
    }
}
