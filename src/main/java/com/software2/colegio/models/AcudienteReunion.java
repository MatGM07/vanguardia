package com.software2.colegio.models;

import jakarta.persistence.*;

import java.io.Serializable;


@Entity
@Table (name = "AcudienteReunion")
public class AcudienteReunion {

    @EmbeddedId
    private AcudienteReunionId id;

    public AcudienteReunionId getId() {
        return id;
    }

    public void setId(AcudienteReunionId id) {
        this.id = id;
    }

    @ManyToOne
    @MapsId("acudienteId")
    @JoinColumn(name = "acudiente_id")
    private Acudiente acudiente;

    @ManyToOne
    @MapsId("reunionId")
    @JoinColumn(name = "reunion_id")
    private Reunion reunion;

    public AcudienteReunion() {
        this.id = new AcudienteReunionId();
    }

    public Acudiente getAcudiente() {
        return acudiente;
    }

    public void setAcudiente(Acudiente acudiente) {
        this.acudiente = acudiente;
        this.id.setAcudienteId(acudiente.getId());
    }

    public Reunion getReunion() {
        return reunion;
    }

    public void setReunion(Reunion reunion) {
        this.reunion = reunion;
        this.id.setReunionId(reunion.getId());
    }

    public AcudienteReunion(Acudiente acudiente, Reunion reunion) {
        this.acudiente = acudiente;
        this.reunion = reunion;
        this.id = new AcudienteReunionId(acudiente.getId(), reunion.getId());
    }

    @Override
    public String toString() {
        return "AcudienteReunion{" +
                "acudiente=" + acudiente +
                ", reunion=" + reunion +
                '}';
    }
}
