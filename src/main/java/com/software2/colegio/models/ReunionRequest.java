package com.software2.colegio.models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReunionRequest {
    private String dia;
    private String hora;
    private String descripcion;
    private Long idDocente;

    public Long getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(Long idDocente) {
        this.idDocente = idDocente;
    }

    public LocalDate getDia() {
        return LocalDate.parse(dia, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public LocalTime getHora() {
        return LocalTime.parse(hora, DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
