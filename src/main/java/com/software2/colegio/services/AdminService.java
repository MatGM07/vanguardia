package com.software2.colegio.services;

import com.software2.colegio.models.Admin;
import com.software2.colegio.models.Admin;
import com.software2.colegio.models.Estudiante;
import com.software2.colegio.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean authenticate(String correo, String contraseña) {
        Admin admin = adminRepository.findByCorreo(correo);
        return admin != null && passwordEncoder.matches(contraseña, admin.getContraseña());
    }

    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    public Admin findByCorreo(String correo){
        return adminRepository.findByCorreo(correo);
    }

    public Admin save(Admin admin) {
        admin.setContraseña(passwordEncoder.encode(admin.getContraseña()));
        return adminRepository.save(admin);
    }

    public void deleteById(Long id) {
        adminRepository.deleteById(id);
    }
}
