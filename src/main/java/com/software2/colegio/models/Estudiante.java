package com.software2.colegio.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name="Estudiante")
public class Estudiante extends Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Estudiante() {

    }

    public Estudiante(Long id, List<Acudiente> acudientes, List<Contenido> contenidos) {
        this.id = id;
        this.acudientes = acudientes;
        this.contenidos = contenidos;
    }

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL)
    private List<Acudiente> acudientes;

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL,  fetch = FetchType.EAGER)
    private List<Contenido> contenidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Acudiente> getAcudientes() {
        return acudientes;
    }

    public void setAcudientes(List<Acudiente> acudientes) {
        this.acudientes = acudientes;
    }

    public List<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(List<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "id=" + id +
                ", acudientes=" + acudientes +
                ", " + super.toString() +
                '}';
    }
}
