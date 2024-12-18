package com.software2.colegio.controllers;

import com.software2.colegio.models.*;
import com.software2.colegio.services.ContenidoService;
import com.software2.colegio.services.DocenteService;
import com.software2.colegio.services.HoraLibreService;
import com.software2.colegio.services.SeccionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String citas(Model model){
        List<Docente> docentes = docenteService.findAll();
        model.addAttribute("docentes",docentes);

        return "citas/agendar";
    }

    @GetMapping("/citas/{id}")
    public String citaExacta(Model model, @PathVariable Long id){
        Optional<Docente> docente = docenteService.findById(id);
        List<HoraLibre> horas = horaLibreService.findByDocente(id);
        List<String> diaSemana = new ArrayList<>();
        for (HoraLibre hora : horas){
            System.out.println("horas");
            System.out.println(hora.getHoraInicio());
        }

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
    public String comunicados(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model, HttpSession session){
        Page<Contenido> comunicadosPage = contenidoService.findPagedBySeccionNombre("Comunicados", PageRequest.of(page, size));

        model.addAttribute("comunicados", comunicadosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", comunicadosPage.getTotalPages());

        String role = (String) session.getAttribute("role");
        if ("ROLE_ADMIN".equals(role)) {
            model.addAttribute("isAdmin", true);
        }
        return "administrativo/comunicados";
    }

    @GetMapping("/resoluciones")
    public String resoluciones(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, Model model, HttpSession session){
        Page<Contenido> comunicadosPage = contenidoService.findPagedBySeccionNombre("Resoluciones", PageRequest.of(page, size));

        model.addAttribute("resoluciones", comunicadosPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", comunicadosPage.getTotalPages());

        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return "administrativo/resoluciones";
    }

    @GetMapping("/rendicion")
    public String rendicion(Model model, HttpSession session){
        Optional<Seccion> listaAños = seccionService.findById(Long.valueOf(10));

        String años = listaAños.get().getDescripcion();

        String[] elementos = años.split(",");

        List<String> listaDeAños = Arrays.asList(elementos);

        List<Contenido> rendiciones = contenidoService.findBySeccionNombre("Rendiciones");
        model.addAttribute("años", listaDeAños);
        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return"administrativo/rendicion";
    }

    @GetMapping("/rendicionanual/{año}")
    public String rendicion(@PathVariable Long año, Model model, HttpSession session ){
        String nombre = "Rendiciones"+año;
        model.addAttribute("año", año);
        List<Contenido> contenidos = contenidoService.findBySeccionNombre(nombre);
        Optional<Seccion> seccionActual = seccionService.findByNombre(nombre);
        long id = seccionActual.get().getId();

        model.addAttribute("id", id);
        if (!contenidos.isEmpty()){
            model.addAttribute("contenidos",contenidos);
        }
        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return "administrativo/rendicionanual";
    }

    @GetMapping("/convocatorias")
    public String convocatorias(Model model, HttpSession session){
        Optional<Seccion> listaAños = seccionService.findById(Long.valueOf(18));

        String años = listaAños.get().getDescripcion();

        String[] elementos = años.split(",");

        List<String> listaDeAños = Arrays.asList(elementos);

        List<Contenido> Convocatorias = contenidoService.findBySeccionNombre("Convocatorias");
        model.addAttribute("años", listaDeAños);
        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return"administrativo/convocatorias";
    }

    @GetMapping("/convocatoriaanual/{año}")
    public String convocatoriaAnual(@PathVariable Long año, Model model, HttpSession session ){
        String nombre = "Convocatorias"+año;
        model.addAttribute("año", año);
        List<Contenido> contenidos = contenidoService.findBySeccionNombre(nombre);
        Optional<Seccion> seccionActual = seccionService.findByNombre(nombre);
        long id = seccionActual.get().getId();

        model.addAttribute("id", id);
        if (!contenidos.isEmpty()){
            model.addAttribute("contenidos",contenidos);
        }
        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return "administrativo/convocatoriaanual";
    }

    @GetMapping("/contrataciones")
    public String contrataciones(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model,
            HttpSession session) {
        Optional<Seccion> listaAños = seccionService.findById(Long.valueOf(21));

        String años = listaAños.map(Seccion::getDescripcion).orElse("");

        String[] elementos = años.split(",");
        List<String> listaDeAños = Arrays.asList(elementos);

        // Paginación manual
        int totalAnios = listaDeAños.size();
        int totalPages = (int) Math.ceil((double) totalAnios / size);

        int fromIndex = Math.min(page * size, totalAnios);
        int toIndex = Math.min(fromIndex + size, totalAnios);

        List<String> pagedAnios = listaDeAños.subList(fromIndex, toIndex);

        model.addAttribute("años", pagedAnios);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }

        return "administrativo/contrataciones";
    }

    @GetMapping("/contratacionanual/{año}")
    public String contratacionAnual(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size,@PathVariable Long año, Model model, HttpSession session ){
        String nombre = "Contrataciones"+año;
        model.addAttribute("año", año);
        Page<Contenido> contenidos = contenidoService.findPagedBySeccionNombre(nombre, PageRequest.of(page, size));
        Optional<Seccion> seccionActual = seccionService.findByNombre(nombre);
        long id = seccionActual.get().getId();

        model.addAttribute("id", id);
        if (!contenidos.isEmpty()){
            model.addAttribute("contenidos", contenidos.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", contenidos.getTotalPages());
        }
        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return "administrativo/contratacionanual";
    }

    @GetMapping("/pedagogicos")
    public String pedagogicos(Model model, HttpSession session){
        List<Seccion> listaproyectos = seccionService.findByDescripcion("Proyecto Pedagogico");
        if(!listaproyectos.isEmpty()){
            model.addAttribute("proyectos", listaproyectos);
        }
        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return"proyectos/pedagogicos";
    }

    @GetMapping("/proyectoindi/{id}")
    public String pedagogicoIndividual(@PathVariable Long id ,Model model, HttpSession session){
        Optional<Seccion> seccionOptional = seccionService.findById(id);
        List<Contenido> contenidos = contenidoService.findBySeccionNombre(seccionOptional.get().getNombre());
        model.addAttribute("contenidos", contenidos);
        model.addAttribute("seccion",seccionOptional.get());

        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }

        return"proyectos/especifico";
    }

    @GetMapping("/productivos")
    public String productivos(Model model, HttpSession session){
        List<Seccion> listaproyectos = seccionService.findByDescripcion("Proyecto Productivo");
        if(!listaproyectos.isEmpty()){
            model.addAttribute("proyectos", listaproyectos);
        }
        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return"proyectos/productivos";
    }



    @GetMapping("/aula")
    public String aula(Model model, HttpSession session){
        List<Seccion> aula = seccionService.findByDescripcion("Proyecto de Aula");
        model.addAttribute("aula", aula);
        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return"proyectos/aula";
    }

    @GetMapping("/mercado")
    public String mercado(Model model, HttpSession session){

        return"mercado/campesino";
    }

    @GetMapping("/periodico")
    public String periodico(Model model, HttpSession session){
        List<Contenido> contenidos = contenidoService.findBySeccionNombre("Periodico");
        model.addAttribute("contenidos", contenidos);
        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return"periodico/institucional";
    }

    @GetMapping("/ra")
    public String realidadAumentada(){
        return "ra";
    }

    @GetMapping("/biblioteca")
    public String bibliotecaVirtual(Model model, HttpSession session){
        List<Contenido> bibliotecas = contenidoService.findBySeccionNombre("Bibliotecas");
        model.addAttribute("bibliotecas", bibliotecas);
        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }

        return "biblioteca";
    }

    @GetMapping("/comunidad")
    public String comunidad(Model model, HttpSession session) {
        Optional<Seccion> seccionOptional = seccionService.findByNombre("Eventos");
        List<Contenido> contenidos = contenidoService.findBySeccionNombre(seccionOptional.get().getNombre());
        model.addAttribute("contenidos", contenidos);


        String role = (String) session.getAttribute("role");
        if (role == "ROLE_ADMIN"){
            boolean isAdmin = true;
            model.addAttribute("isAdmin", isAdmin);
        }
        return "comunidad";
    }
}
