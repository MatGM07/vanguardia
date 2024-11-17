package com.software2.colegio.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Docente")
public class Docente extends Usuario {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    private String telefono;

    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL)
    private List<DocenteReunion> reunions;

    @OneToMany(mappedBy = "docente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contenido> contenidos;

    public Docente(Long id, String telefono, List<DocenteReunion> reunions, List<Contenido> contenidos) {
        this.id = id;
        this.telefono = telefono;
        this.reunions = reunions;
        this.contenidos = contenidos;
    }

    public Docente() {

    }

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

    public List<DocenteReunion> getReunions() {
        return reunions;
    }

    public void setReunions(List<DocenteReunion> reunions) {
        this.reunions = reunions;
    }

    public List<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(List<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "id=" + id +
                ", telefono=" + telefono +
                '}';
    }
}
