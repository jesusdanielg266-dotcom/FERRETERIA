package com.mycompany.ferreteria;

public final class Sesion {
    private static Usuario usuarioActual;
    private Sesion() { }
    public static Usuario getUsuarioActual() { return usuarioActual; }
    public static void setUsuarioActual(Usuario usuario) { usuarioActual = usuario; }
    public static void cerrar() { usuarioActual = null; }
}
