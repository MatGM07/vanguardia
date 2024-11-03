package com.software2.colegio.services;

import com.software2.colegio.models.Contenido;
import com.software2.colegio.repositories.ContenidoRepository;
import com.software2.colegio.repositories.EstudianteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContenidoService {

    @Autowired
    private ContenidoRepository contenidoRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;

    public List<Contenido> findAll(){
        return contenidoRepository.findAll();
    }

    public Optional<Contenido> findById(Long id){
        return contenidoRepository.findById(id);
    }

    public Contenido save(Contenido contenido){
        return contenidoRepository.save(contenido);
    }

    public void deleteById(long id){
        contenidoRepository.deleteById(id);
    }


    public List<Contenido> findBySeccionNombre(String nombre) {
        return contenidoRepository.findBySeccionNombre(nombre);
    }
}
