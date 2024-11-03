package com.software2.colegio.services;

import com.software2.colegio.models.Acudiente;
import com.software2.colegio.models.Estudiante;
import com.software2.colegio.repositories.AcudienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AcudienteService {

    @Autowired
    private AcudienteRepository acudienteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean authenticate(String correo, String contraseña) {
        Acudiente acudiente = acudienteRepository.findByCorreo(correo);
        return acudiente != null && passwordEncoder.matches(contraseña, acudiente.getContraseña());
    }

    public Acudiente findByCorreo(String correo){
        return acudienteRepository.findByCorreo(correo);
    }

    public List<Acudiente> findAll() {
        return acudienteRepository.findAll();
    }

    public Optional<Acudiente> findById(Long id) {
        return acudienteRepository.findById(id);
    }

    public Acudiente save(Acudiente acudiente) {
        acudiente.setContraseña(passwordEncoder.encode(acudiente.getContraseña()));
        return acudienteRepository.save(acudiente);
    }

    public void deleteById(Long id) {
        acudienteRepository.deleteById(id);
    }
}
