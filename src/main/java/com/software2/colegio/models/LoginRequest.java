package com.software2.colegio.models;

public class LoginRequest {
    private String correo;
    private String contraseña;

    // Constructor vacío
    public LoginRequest() {
    }

    // Getters y Setters
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
}
