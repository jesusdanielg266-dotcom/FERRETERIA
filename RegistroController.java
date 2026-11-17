package com.mycompany.ferreteria;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegistroController {
    @FXML private TextField txtNombre;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtClave;
    @FXML private PasswordField txtConfirmar;
    @FXML private Label lblMensaje;

    @FXML
    private void registrar() {
        String nombre = txtNombre.getText().trim();
        String correo = txtCorreo.getText().trim();
        String usuario = txtUsuario.getText().trim();
        String clave = txtClave.getText();
        String confirmar = txtConfirmar.getText();
        if (nombre.isEmpty() || correo.isEmpty() || usuario.isEmpty() || clave.isEmpty() || confirmar.isEmpty()) {
            lblMensaje.setText("Completa todos los campos."); return;
        }
        if (!correo.matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            lblMensaje.setText("Escribe un correo electrónico válido."); return;
        }
        if (clave.length() < 4) { lblMensaje.setText("La contraseña debe tener al menos 4 caracteres."); return; }
        if (!clave.equals(confirmar)) { lblMensaje.setText("Las contraseñas no coinciden."); return; }
        if (!UsuarioStore.registrarCliente(nombre, correo, usuario, clave)) {
            lblMensaje.setText("Ese usuario o correo ya está registrado."); return;
        }
        lblMensaje.setStyle("-fx-text-fill: #15803d;");
        lblMensaje.setText("Cuenta creada. Ya puedes iniciar sesión.");
        txtNombre.clear(); txtCorreo.clear(); txtUsuario.clear(); txtClave.clear(); txtConfirmar.clear();
    }
    @FXML private void volverLogin() throws IOException { App.setRoot("primary"); }
}
