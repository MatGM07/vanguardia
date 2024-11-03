package com.software2.colegio.models;

import jakarta.persistence.*;

@MappedSuperclass
public class Usuario {
    private String nombre;
    private String apellido;
    @Column(unique = true)
    private String correo;
    private String contraseña;
    private String tipoRol;

    public String gettipoRol() {
        return tipoRol;
    }

    public void settipoRol(String tipoRol) {
        this.tipoRol = tipoRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public Usuario(String nombre, String apellido, String correo, String contraseña, String tipoRol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.tipoRol = tipoRol;
    }

    public Usuario(){

    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", correo='" + correo + '\'' +
                ", contraseña='" + contraseña + '\'' +
                ", tipoRol='" + tipoRol + '\'' +
                '}';
    }
}


