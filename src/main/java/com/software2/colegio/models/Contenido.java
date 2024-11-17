package com.software2.colegio.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table (name = "Contenido")
public class Contenido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    private LocalDate fecha_creacion;

    private LocalTime hora_creacion;

    private LocalDate fecha_modificacion;

    private LocalTime hora_modificacion;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = true)
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "seccion_id")
    private Seccion seccion;

    @ManyToOne
    @JoinColumn(name = "acudiente_id", nullable = true)
    private Acudiente acudiente;

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = true)
    private Docente docente;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = true)
    private Estudiante estudiante;

    @OneToMany(mappedBy = "contenido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Imagenes> imagenes;

    public Contenido() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDate fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public LocalTime getHora_creacion() {
        return hora_creacion;
    }

    public void setHora_creacion(LocalTime hora_creacion) {
        this.hora_creacion = hora_creacion;
    }

    public LocalDate getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(LocalDate fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public LocalTime getHora_modificacion() {
        return hora_modificacion;
    }

    public void setHora_modificacion(LocalTime hora_modificacion) {
        this.hora_modificacion = hora_modificacion;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Acudiente getAcudiente() {
        return acudiente;
    }

    public void setAcudiente(Acudiente acudiente) {
        this.acudiente = acudiente;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public List<Imagenes> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagenes> imagenes) {
        this.imagenes = imagenes;
    }

    public Seccion getSeccion() {
        return seccion;
    }

    public void setSeccion(Seccion seccion) {
        this.seccion = seccion;
    }


    public Contenido(Long id, String titulo, String descripcion, LocalDate fecha_creacion, LocalTime hora_creacion, LocalDate fecha_modificacion, LocalTime hora_modificacion, Admin admin, Seccion seccion, Acudiente acudiente, Docente docente, Estudiante estudiante, List<Imagenes> imagenes) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fecha_creacion = fecha_creacion;
        this.hora_creacion = hora_creacion;
        this.fecha_modificacion = fecha_modificacion;
        this.hora_modificacion = hora_modificacion;
        this.admin = admin;
        this.seccion = seccion;
        this.acudiente = acudiente;
        this.docente = docente;
        this.estudiante = estudiante;
        this.imagenes = imagenes;
    }


    @Override
    public String toString() {
        return "Contenido{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha_creacion=" + fecha_creacion +
                ", hora_creacion=" + hora_creacion +
                ", fecha_modificacion=" + fecha_modificacion +
                ", hora_modificacion=" + hora_modificacion +
                ", admin=" + admin.getNombre() +
                '}';
    }
}
