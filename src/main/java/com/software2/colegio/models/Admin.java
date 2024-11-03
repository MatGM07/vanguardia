package com.software2.colegio.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;


@Entity
@Table (name="Admin")
public class Admin extends Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String telefono;

    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Contenido> contenidos;


    public Admin() {

    }

    public Admin(Long id, String telefono, List<Contenido> contenidos) {
        this.id = id;
        this.telefono = telefono;
        this.contenidos = contenidos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(List<Contenido> contenidos) {
        this.contenidos = contenidos;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", telefono=" + telefono +
                ", contenidos=" + contenidos +
                ", " + super.toString() +
                '}';
    }
}
