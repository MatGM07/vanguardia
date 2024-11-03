package com.software2.colegio.services;

import com.software2.colegio.models.Acudiente;
import com.software2.colegio.models.Estudiante;
import com.software2.colegio.repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean authenticate(String correo, String contraseña) {
        Estudiante estudiante = estudianteRepository.findByCorreo(correo);
        return estudiante != null && passwordEncoder.matches(contraseña, estudiante.getContraseña());
    }

    public List<Estudiante> findAll() {
        return estudianteRepository.findAll();
    }

    public Optional<Estudiante> findById(Long id) {
        return estudianteRepository.findById(id);
    }

    public Estudiante findByCorreo(String correo){
        return estudianteRepository.findByCorreo(correo);
    }

    public Estudiante save(Estudiante estudiante) {
        estudiante.setContraseña(passwordEncoder.encode(estudiante.getContraseña()));
        return estudianteRepository.save(estudiante);
    }

    public void deleteById(Long id) {
        estudianteRepository.deleteById(id);
    }
}
