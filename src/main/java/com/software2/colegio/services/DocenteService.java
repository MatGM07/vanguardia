package com.software2.colegio.services;

import com.software2.colegio.models.Acudiente;
import com.software2.colegio.models.Docente;
import com.software2.colegio.models.Estudiante;
import com.software2.colegio.repositories.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean authenticate(String correo, String contraseña) {
        Docente docente = docenteRepository.findByCorreo(correo);
        return docente != null && passwordEncoder.matches(contraseña, docente.getContraseña());
    }

    public List<Docente> findAll() {
        return docenteRepository.findAll();
    }

    public Optional<Docente> findById(Long id) {
        return docenteRepository.findById(id);
    }

    public Docente findByCorreo(String correo){
        return docenteRepository.findByCorreo(correo);
    }

    public Docente save(Docente docente) {
        docente.setContraseña(passwordEncoder.encode(docente.getContraseña()));
        return docenteRepository.save(docente);
    }

    public void deleteById(Long id) {
        docenteRepository.deleteById(id);
    }
}
