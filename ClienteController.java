package com.mycompany.ferreteria;

import java.io.IOException;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ClienteController {
    @FXML private Label lblBienvenida;
    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colCodigo;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, String> colDisponibilidad;
    @FXML private TextField txtBuscar;

    @FXML
    private void initialize() {
        Usuario actual = Sesion.getUsuarioActual();
        lblBienvenida.setText(actual == null ? "Bienvenido" : "Hola, " + actual.getNombreCompleto());
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colDisponibilidad.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(
                c.getValue().getCantidad() > 0 ? "Disponible" : "Agotado"));
        FilteredList<Producto> filtrados = new FilteredList<>(ProductoStore.getProductos(), p -> true);
        tablaProductos.setItems(filtrados);
        txtBuscar.textProperty().addListener((obs, anterior, nuevo) -> {
            String filtro = nuevo == null ? "" : nuevo.trim().toLowerCase();
            filtrados.setPredicate(p -> filtro.isEmpty()
                    || p.getCodigo().toLowerCase().contains(filtro)
                    || p.getNombre().toLowerCase().contains(filtro));
        });
    }
    @FXML private void limpiarBusqueda() { txtBuscar.clear(); }
    @FXML private void cerrarSesion() throws IOException { Sesion.cerrar(); App.setRoot("primary"); }
}
