package com.software2.colegio.controllers;

import com.software2.colegio.models.*;
import com.software2.colegio.services.ContenidoService;
import com.software2.colegio.services.DocenteService;
import com.software2.colegio.services.HoraLibreService;
import com.software2.colegio.services.SeccionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/content")
public class VistaController {
    private final ContenidoService contenidoService;
    private final SeccionService seccionService;
    private final DocenteService docenteService;
    private final HoraLibreService horaLibreService;

    public VistaController(ContenidoService contenidoService, SeccionService seccionService, DocenteService docenteService, HoraLibreService horaLibreService) {
        this.contenidoService = contenidoService;
        this.seccionService = seccionService;
        this.docenteService = docenteService;
        this.horaLibreService = horaLibreService;
    }

    @GetMapping("/principios")
    public String principios(Model model, HttpSession session) {
        Optional<Seccion> principio = seccionService.findById(Long.valueOf(1));

        model.addAttribute("seccion",principio);

        String role = (String) session.getAttribute("role");

        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        System.out.println("Autoridades del usuario: " + role);


        List<Contenido> contenidos = contenidoService.findBySeccionNombre("Principios");
        model.addAttribute("contenidos", contenidos);
        return "contenido/principios";
    }

    @GetMapping("/misionvision")
    public String misionVision(){
        return"contenido/mision_y_vision";
    }

    @GetMapping("/simbolos")
    public String simbolos(){
        return"contenido/simbolos";
    }

    @GetMapping("/sedes")
    public String sedes(){
        return"contenido/sedes";
    }

    @GetMapping("/horas")
    public String horas(){
        return"citas/horaslibres";
    }

    @GetMapping("/citas")
    public String citas(){
        return "citas/agendar";
    }

    @GetMapping("/citas/{id}")
    public String citaExacta(Model model, @PathVariable Long id){
        Optional<Docente> docente = docenteService.findById(id);
        List<HoraLibre> horas = horaLibreService.findByDocente(id);
        model.addAttribute("docente", docente.get().getId());
        model.addAttribute("horas", horas);

        return "citas/profesor";
    }

    @GetMapping("/pei")
    public String pei(Model model, HttpSession session){
        Optional<Seccion> pei = seccionService.findById(Long.valueOf(3));
        model.addAttribute("seccion",pei.get());

        List<Contenido> contenido = contenidoService.findBySeccionNombre("PEI");

        if (contenido.isEmpty()){
            boolean noPEI = true;
            model.addAttribute("noPEI", noPEI);
        }else {
            model.addAttribute("contenido", contenido.get(0));
        }

        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return"academico/pei";

    }

    @GetMapping("/manual")
    public String manual(Model model, HttpSession session){
        Optional<Seccion> manual = seccionService.findById(Long.valueOf(4));
        model.addAttribute("seccion",manual.get());

        List<Contenido> contenido = contenidoService.findBySeccionNombre("Manual");

        if (contenido.isEmpty()){
            boolean noManual = true;
            model.addAttribute("noManual", noManual);
        }else {
            model.addAttribute("contenido", contenido.get(0));
        }

        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return"academico/manual";
    }

    @GetMapping("/plataforma")
    public String plataforma(Model model, HttpSession session){
        Optional<Seccion> seccion = seccionService.findById(Long.valueOf(5));
        model.addAttribute("seccion",seccion.get());

        List<Contenido> contenido = contenidoService.findBySeccionNombre("Plataformas");


        if (contenido.isEmpty()){
            boolean noPlat = true;
            model.addAttribute("noPlat", noPlat);
        }else {
            if (Objects.equals(contenido.get(0).getTitulo(), "Moodle")){
                model.addAttribute("Moodle", contenido.get(0));
                model.addAttribute("Classroom", contenido.get(1));
            }else {
                model.addAttribute("Moodle", contenido.get(1));
                model.addAttribute("Classroom", contenido.get(0));
            }
        }

        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return"academico/plataforma";
    }

    @GetMapping("/plan")
    public String plan(){
        return"academico/plan";
    }

    @GetMapping("/comunicados")
    public String comunicados(Model model, HttpSession session){
        List<Contenido> comunicados = contenidoService.findBySeccionNombre("Comunicados");
        model.addAttribute("comunicados", comunicados);
        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return"administrativo/comunicados";
    }
}
