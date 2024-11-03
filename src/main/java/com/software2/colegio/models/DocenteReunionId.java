package com.software2.colegio.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DocenteReunionId implements Serializable {
    private Long docenteId;
    private Long reunionId;

    public Long getDocenteId() {
        return docenteId;
    }

    public void setDocenteId(Long docenteId) {
        this.docenteId = docenteId;
    }

    public Long getReunionId() {
        return reunionId;
    }

    public void setReunionId(Long reunionId) {
        this.reunionId = reunionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocenteReunionId that = (DocenteReunionId) o;
        return Objects.equals(docenteId, that.docenteId) && Objects.equals(reunionId, that.reunionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(docenteId, reunionId);
    }

    public DocenteReunionId(Long docenteId, Long reunionId) {
        this.docenteId = docenteId;
        this.reunionId = reunionId;
    }

    public DocenteReunionId() {

    }
}
