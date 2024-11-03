package com.software2.colegio.services;

import com.software2.colegio.models.DocenteReunion;

import com.software2.colegio.repositories.DocenteReunionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocenteReunionService {
    @Autowired
    private DocenteReunionRepository docentereunionRepository;

    public List<DocenteReunion> findAll(){
        return docentereunionRepository.findAll();
    }

    public Optional<DocenteReunion> findById(Long id){
        return docentereunionRepository.findById(id);
    }

    public DocenteReunion save(DocenteReunion reunion){
        return docentereunionRepository.save(reunion);
    }

    public void deleteById(long id){
        docentereunionRepository.deleteById(id);
    }
}
