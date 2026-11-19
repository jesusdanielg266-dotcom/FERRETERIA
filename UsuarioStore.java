package com.mycompany.ferreteria;

import java.util.ArrayList;
import java.util.List;

/** Almacenamiento temporal en memoria para la demostración académica. */
public final class UsuarioStore {
    private static final List<Usuario> USUARIOS = new ArrayList<>();
    static {
        USUARIOS.add(new Usuario("Administrador", "admin@ferreteria.com", "admin", "1234", "ADMIN"));
    }
    private UsuarioStore() { }
    public static Usuario autenticar(String usuario, String clave) {
        return USUARIOS.stream().filter(u -> u.getUsuario().equalsIgnoreCase(usuario)
                && u.getContrasena().equals(clave)).findFirst().orElse(null);
    }
    public static boolean registrarCliente(String nombre, String correo, String usuario, String clave) {
        boolean repetido = USUARIOS.stream().anyMatch(u -> u.getUsuario().equalsIgnoreCase(usuario)
                || u.getCorreo().equalsIgnoreCase(correo));
        if (repetido) return false;
        USUARIOS.add(new Usuario(nombre, correo, usuario, clave, "CLIENTE"));
        return true;
    }
}
