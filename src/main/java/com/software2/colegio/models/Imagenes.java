package com.software2.colegio.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Imagenes")
public class Imagenes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "contenido_id")
    private Contenido contenido;

    public Imagenes(Long id, String titulo, String direccion, Contenido contenido) {
        this.id = id;
        this.titulo = titulo;
        this.direccion = direccion;
        this.contenido = contenido;
    }

    public Imagenes() {

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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Contenido getContenido() {
        return contenido;
    }

    public void setContenido(Contenido contenido) {
        this.contenido = contenido;
    }

    @Override
    public String toString() {
        return "Imagenes{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", contenido=" + contenido +
                '}';
    }
}
