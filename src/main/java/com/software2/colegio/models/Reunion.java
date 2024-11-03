package com.software2.colegio.models;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name="Reunion")
public class Reunion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate fecha;
    private LocalTime hora;
    private String descripcion;

    @OneToMany(mappedBy = "reunion", cascade = CascadeType.ALL)
    private List<DocenteReunion> docentes;

    @OneToMany(mappedBy = "reunion", cascade = CascadeType.ALL)
    private List<AcudienteReunion> acudientes;

    public Reunion(long id, LocalDate fecha, LocalTime hora, String descripcion, List<DocenteReunion> docentes, List<AcudienteReunion> acudientes) {
        this.id = id;
        this.fecha = fecha;
        this.hora = hora;
        this.descripcion = descripcion;
        this.docentes = docentes;
        this.acudientes = acudientes;
    }

    public Reunion() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<DocenteReunion> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<DocenteReunion> docentes) {
        this.docentes = docentes;
    }

    public List<AcudienteReunion> getAcudientes() {
        return acudientes;
    }

    public void setAcudientes(List<AcudienteReunion> acudientes) {
        this.acudientes = acudientes;
    }

    @Override
    public String toString() {
        return "Reunion{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", hora=" + hora +
                ", descripcion='" + descripcion + '\'' +
                ", docentes=" + docentes +
                ", acudientes=" + acudientes +
                '}';
    }
}
