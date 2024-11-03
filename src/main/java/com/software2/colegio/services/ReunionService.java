package com.software2.colegio.services;

import com.software2.colegio.models.Reunion;
import com.software2.colegio.repositories.ReunionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReunionService {
    @Autowired
    private ReunionRepository reunionRepository;

    public List<Reunion> findAll(){
        return reunionRepository.findAll();
    }

    public Optional<Reunion> findById(Long id){
        return reunionRepository.findById(id);
    }

    public Reunion save(Reunion reunion){
        return reunionRepository.save(reunion);
    }

    public void deleteById(long id){
        reunionRepository.deleteById(id);
    }
}
