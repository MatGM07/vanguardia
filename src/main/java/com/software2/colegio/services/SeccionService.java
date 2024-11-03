package com.software2.colegio.services;

import com.software2.colegio.models.Seccion;

import com.software2.colegio.repositories.SeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeccionService {
    @Autowired
    private SeccionRepository seccionRepository;

    public List<Seccion> findAll(){
        return seccionRepository.findAll();
    }

    public Optional<Seccion> findById(Long id){
        return seccionRepository.findById(id);
    }

    public Seccion save(Seccion seccion){
        return seccionRepository.save(seccion);
    }

    public void deleteById(long id){
        seccionRepository.deleteById(id);
    }

}
