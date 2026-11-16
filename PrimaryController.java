package com.mycompany.ferreteria;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class PrimaryController {
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMensaje;

    @FXML
    private void switchToSecondary() throws IOException {
        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText();
        if (usuario.isEmpty() || password.isEmpty()) {
            lblMensaje.setText("Por favor, completa todos los campos.");
            return;
        }
        Usuario cuenta = UsuarioStore.autenticar(usuario, password);
        if (cuenta == null) {
            lblMensaje.setText("Usuario o contraseña incorrectos.");
            txtPassword.clear();
            return;
        }
        Sesion.setUsuarioActual(cuenta);
        App.setRoot(cuenta.getRol().equals("ADMIN") ? "secondary" : "catalogo-cliente");
    }

    @FXML private void abrirRegistro() throws IOException { App.setRoot("registro"); }
}
