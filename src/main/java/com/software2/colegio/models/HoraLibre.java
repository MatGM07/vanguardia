package com.software2.colegio.models;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class HoraLibre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String diaSemana;

    private LocalTime horaInicio;

    @ManyToOne
    @JoinColumn(name = "docente_id", nullable = false)
    private Docente docente;

    // Constructores, getters y setters

    public HoraLibre() {}

    public HoraLibre(String diaSemana, LocalTime horaInicio, Docente docente) {
        this.diaSemana = diaSemana;
        this.horaInicio = horaInicio;
        this.docente = docente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }
}
