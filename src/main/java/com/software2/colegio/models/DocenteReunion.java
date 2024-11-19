package com.software2.colegio.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

@Entity
@Table(name = "DocenteReunion")
public class DocenteReunion {

    @EmbeddedId
    private DocenteReunionId id;

    @ManyToOne
    @MapsId("docenteId")
    @JsonIgnore
    @JoinColumn(name = "docente_id")
    private Docente docente;


    @ManyToOne
    @MapsId("reunionId")
    @JsonIgnore
    @JoinColumn(name = "reunion_id")
    private Reunion reunion;

    public DocenteReunion(Docente docente, Reunion reunion) {
        this.docente = docente;
        this.reunion = reunion;
        this.id = new DocenteReunionId(docente.getId(), reunion.getId());
    }

    public DocenteReunion() {
        this.id = new DocenteReunionId();
    }

    public DocenteReunionId getId() {
        return id;
    }

    public void setId(DocenteReunionId id) {
        this.id = id;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public Reunion getReunion() {
        return reunion;
    }

    public void setReunion(Reunion reunion) {
        this.reunion = reunion;
    }

    @Override
    public String toString() {
        return "DocenteReunion{" +
                "docente=" + docente +
                ", reunion=" + reunion +
                '}';
    }
}
