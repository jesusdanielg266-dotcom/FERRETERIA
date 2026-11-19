package com.mycompany.ferreteria;

public class Usuario {
    private final String nombreCompleto;
    private final String correo;
    private final String usuario;
    private final String contrasena;
    private final String rol;

    public Usuario(String nombreCompleto, String correo, String usuario, String contrasena, String rol) {
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.rol = rol;
    }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCorreo() { return correo; }
    public String getUsuario() { return usuario; }
    public String getContrasena() { return contrasena; }
    public String getRol() { return rol; }
}
