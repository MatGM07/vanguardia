package com.software2.colegio.controllers;

import com.software2.colegio.models.*;
import com.software2.colegio.services.AcudienteService;
import com.software2.colegio.services.AdminService;
import com.software2.colegio.services.DocenteService;
import com.software2.colegio.services.EstudianteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("usuario")
public class LoginController {
    private final AdminService adminService;
    private final DocenteService docenteService;
    private final AcudienteService acudienteService;
    private final EstudianteService estudianteService;

    public LoginController(AdminService adminService, DocenteService docenteService, AcudienteService acudienteService, EstudianteService estudianteService) {
        this.adminService = adminService;
        this.docenteService = docenteService;
        this.acudienteService = acudienteService;
        this.estudianteService = estudianteService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpSession session) {
        String correo = loginRequest.getCorreo();
        String contraseña = loginRequest.getContraseña();

        System.out.println("estamos en proceso");

        if (adminService.authenticate(correo, contraseña)) {
            Admin admin = adminService.findByCorreo(correo);
            session.setAttribute("user", admin);
            session.setAttribute("role","ROLE_ADMIN");
            System.out.println("login exitoso ADMIN");

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    admin, null, List.of(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());


            return ResponseEntity.ok("Login exitoso como Admin");

        } else if (docenteService.authenticate(correo, contraseña)) {
            Docente docente = docenteService.findByCorreo(correo);
            session.setAttribute("role","ROLE_DOCENTE");
            session.setAttribute("user", docente);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    docente, null, List.of(new SimpleGrantedAuthority("ROLE_DOCENTE"))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

            return ResponseEntity.ok("Login exitoso como Docente");

        } else if (acudienteService.authenticate(correo, contraseña)) {
            Acudiente acudiente = acudienteService.findByCorreo(correo);
            session.setAttribute("user", acudiente);
            session.setAttribute("role","ROLE_ACUDIENTE");

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    acudiente, null, List.of(new SimpleGrantedAuthority("ROLE_ACUDIENTE"))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

            return ResponseEntity.ok("Login exitoso como Acudiente");

        } else if (estudianteService.authenticate(correo, contraseña)) {
            Estudiante estudiante = estudianteService.findByCorreo(correo);
            session.setAttribute("user", estudiante);
            session.setAttribute("role","ROLE_ESTUDIANTE");

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    estudiante, null, List.of(new SimpleGrantedAuthority("ROLE_ESTUDIANTE"))
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

            return ResponseEntity.ok("Login exitoso como Estudiante");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}
