package com.software2.colegio.services;

import com.software2.colegio.models.HoraLibre;
import com.software2.colegio.repositories.HoraLibreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class HoraLibreService {
    @Autowired
    private HoraLibreRepository horaLibreRepository;

    public List<HoraLibre> findAll(){
        return horaLibreRepository.findAll();
    }

    public Optional<HoraLibre> findById(Long id){
        return horaLibreRepository.findById(id);
    }

    public HoraLibre save(HoraLibre horaLibre){
        return horaLibreRepository.save(horaLibre);
    }

    public void deleteById(long id){
        horaLibreRepository.deleteById(id);
    }

    public List<HoraLibre> findByDocente(Long docenteId) {
        return horaLibreRepository.findByDocenteId(docenteId);
    }

    public Optional<HoraLibre> findByDiaSemanaAndHoraInicioAndDocenteId(String diaSemana, LocalTime horaInicio, Long docenteId){
        return horaLibreRepository.findByDiaSemanaAndHoraInicioAndDocenteId(diaSemana, horaInicio, docenteId);
    };

}
