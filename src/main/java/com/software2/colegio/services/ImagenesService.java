package com.software2.colegio.services;

import com.software2.colegio.models.Imagenes;
import com.software2.colegio.repositories.ImagenesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImagenesService {
    @Autowired
    private ImagenesRepository imagenesRepository;

    public List<Imagenes> findAll(){
        return imagenesRepository.findAll();
    }

    public Optional<Imagenes> findById(Long id){
        return imagenesRepository.findById(id);
    }

    public Imagenes save(Imagenes imagenes){
        return imagenesRepository.save(imagenes);
    }

    public void deleteById(long id){
        imagenesRepository.deleteById(id);
    }
}
