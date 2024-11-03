package com.software2.colegio.services;

import com.software2.colegio.models.AcudienteReunion;
import com.software2.colegio.repositories.AcudienteReunionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcudienteReunionService {
    @Autowired
    private AcudienteReunionRepository acudientereunionRepository;

    public List<AcudienteReunion> findAll(){
        return acudientereunionRepository.findAll();
    }

    public Optional<AcudienteReunion> findById(Long id){
        return acudientereunionRepository.findById(id);
    }

    public AcudienteReunion save(AcudienteReunion acudientereunion){
        return acudientereunionRepository.save(acudientereunion);
    }

    public void deleteById(long id){
        acudientereunionRepository.deleteById(id);
    }
}
