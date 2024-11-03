package com.software2.colegio.controllers;

import com.software2.colegio.models.*;
import com.software2.colegio.services.AcudienteService;
import com.software2.colegio.services.AdminService;
import com.software2.colegio.services.DocenteService;
import com.software2.colegio.services.EstudianteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.beans.BeanUtils.copyProperties;

@Controller
@RequestMapping("/user")
public class RegisterController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private EstudianteService estudianteService;

    @Autowired
    private AcudienteService acudienteService;

    @Autowired
    private DocenteService docenteService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario()); // Para enviar a la vista
        return "registerlogin/register";
    }

    @PostMapping("/register/a")
    public String registerAcudiente(@RequestBody AcudienteRequest request) {
        // Validar el correo del estudiante
        Estudiante estudianteExistente = estudianteService.findByCorreo(request.getEstudianteCorreo());
        if (estudianteExistente == null) {
            throw new RuntimeException("Estudiante no encontrado con el correo proporcionado.");
        }

        // Crear y guardar el acudiente
        Acudiente acudiente = new Acudiente();
        acudiente.setNombre(request.getNombre());
        acudiente.setApellido(request.getApellido());
        acudiente.setCorreo(request.getCorreo());
        acudiente.setContraseña(request.getContraseña());
        acudiente.setTelefono(request.getTelefono());
        acudiente.setParentezco(request.getParentezco());
        acudiente.settipoRol(request.getTipoRol());

        // Asociar el estudiante al acudiente
        acudiente.setEstudiante(estudianteExistente);

        acudienteService.save(acudiente);
        return "redirect:/user/login"; // O redirigir a otro lugar
    }

    @PostMapping("/register/e")
    public String registerEstudiante(@RequestBody Estudiante request) {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre(request.getNombre());
        estudiante.setApellido(request.getApellido());
        estudiante.setCorreo(request.getCorreo());
        estudiante.setContraseña(request.getContraseña());
        estudiante.settipoRol(request.gettipoRol());
        estudianteService.save(estudiante);
        return "redirect:/user/login"; // O redirigir a otro lugar
    }

    @PostMapping("/register/admin")
    public String registerAdmin(@RequestBody Admin request) {
        System.out.println(request);
        Admin admin = new Admin();
        admin.setNombre(request.getNombre());
        admin.setApellido(request.getApellido());
        admin.setCorreo(request.getCorreo());
        admin.setContraseña(request.getContraseña());
        admin.setTelefono(request.getTelefono());
        admin.settipoRol(request.gettipoRol());
        adminService.save(admin);
        return "redirect:/user/login"; // O redirigir a otro lugar
    }

    @PostMapping("/register/doc")
    public String registerAdmin(@RequestBody Docente request) {
        System.out.println(request);
        Docente docente = new Docente();
        docente.setNombre(request.getNombre());
        docente.setApellido(request.getApellido());
        docente.setCorreo(request.getCorreo());
        docente.setContraseña(request.getContraseña());
        docente.setTelefono(request.getTelefono());
        docente.settipoRol(request.gettipoRol());
        docenteService.save(docente);
        return "redirect:/user/login"; // O redirigir a otro lugar
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "registerlogin/login";
    }



}
