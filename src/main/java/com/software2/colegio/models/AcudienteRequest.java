package com.software2.colegio.models;

public class AcudienteRequest {
    private String nombre;
    private String apellido;
    private String correo;
    private String contraseña;
    private String telefono; // Agrega el teléfono aquí
    private String parentezco;
    private String estudianteCorreo;
    private String tipoRol;// Para validar el correo del estudiante

    // Getters y Setters

    public String getTipoRol() {
        return tipoRol;
    }

    public void setTipoRol(String tipoRol) {
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getParentezco() {
        return parentezco;
    }

    public void setParentezco(String parentezco) {
        this.parentezco = parentezco;
    }

    public String getEstudianteCorreo() {
        return estudianteCorreo;
    }

    public void setEstudianteCorreo(String estudianteCorreo) {
        this.estudianteCorreo = estudianteCorreo;
    }


    public AcudienteRequest(String nombre, String apellido, String correo, String contraseña, String telefono, String parentezco, String estudianteCorreo, String tipoRol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        this.telefono = telefono;
        this.parentezco = parentezco;
        this.estudianteCorreo = estudianteCorreo;
        this.tipoRol = tipoRol;
    }

    public AcudienteRequest(){

    }
}