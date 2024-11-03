package com.software2.colegio.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class AcudienteReunionId implements Serializable {
    private Long acudienteId;
    private Long reunionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AcudienteReunionId that = (AcudienteReunionId) o;
        return Objects.equals(acudienteId, that.acudienteId) && Objects.equals(reunionId, that.reunionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acudienteId, reunionId);
    }

    public Long getAcudienteId() {
        return acudienteId;
    }

    public void setAcudienteId(Long acudienteId) {
        this.acudienteId = acudienteId;
    }

    public Long getReunionId() {
        return reunionId;
    }

    public void setReunionId(Long reunionId) {
        this.reunionId = reunionId;
    }

    public AcudienteReunionId(Long acudienteId, Long reunionId) {
        this.acudienteId = acudienteId;
        this.reunionId = reunionId;
    }

    public AcudienteReunionId() {

    }
}
