package com.software2.colegio.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="Seccion")
public class Seccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @OneToMany(mappedBy = "seccion", cascade = CascadeType.ALL)
    private List<Contenido> contenidos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(List<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    public Seccion(Long id, String nombre, String descripcion, List<Contenido> contenidos) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.contenidos = contenidos;
    }

    public Seccion() {

    }

    @Override
    public String toString() {
        return "Seccion{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", contenidos=" + contenidos +
                '}';
    }


}
