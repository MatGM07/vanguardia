package com.software2.colegio.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Acudiente")
public class Acudiente extends Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telefono;
    private String parentezco;

    public Acudiente() {

    }

    public Acudiente(Long id, String  telefono, String parentezco, Estudiante estudiante, List<Contenido> contenidos, List<AcudienteReunion> reunions) {
        this.id = id;
        this.telefono = telefono;
        this.parentezco = parentezco;
        this.estudiante = estudiante;
        this.contenidos = contenidos;
        this.reunions = reunions;
    }

    @ManyToOne
    @JoinColumn(name="estudiante_id")
    private Estudiante estudiante;

    @OneToMany(mappedBy = "acudiente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contenido> contenidos;

    @OneToMany(mappedBy = "acudiente", cascade = CascadeType.ALL)
    private List<AcudienteReunion> reunions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getParentezco() {
        return parentezco;
    }

    public void setParentezco(String parentezco) {
        this.parentezco = parentezco;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public List<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(List<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    public List<AcudienteReunion> getReunions() {
        return reunions;
    }

    public void setReunions(List<AcudienteReunion> reunions) {
        this.reunions = reunions;
    }

    @Override
    public String toString() {
        return "Acudiente{" +
                "id=" + id +
                ", telefono=" + telefono +
                '}';
    }
}
