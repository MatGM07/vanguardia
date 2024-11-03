package com.software2.colegio.models;

import java.util.List;

public class DisponibilidadRequest {

    private Long docenteId;
    private List<Disponibilidad> disponibilidad;

    public Long getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Long docenteId) {
        this.docenteId = docenteId;
    }

    public List<Disponibilidad> getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(List<Disponibilidad> disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

// Getters y setters

    public static class Disponibilidad {
        private String dia;
        private String hora;

        public String getDia() {
            return dia;
        }

        public void setDia(String dia) {
            this.dia = dia;
        }

        public String getHora() {
            return hora;
        }

        public void setHora(String hora) {
            this.hora = hora;
        }

        // Getters y setters
    }
}
