package com.mycompany.ferreteria;

import java.io.IOException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SecondaryController {

    @FXML private TableView<Producto> tablaProductos;
    @FXML private TableColumn<Producto, String> colCodigo;
    @FXML private TableColumn<Producto, String> colNombre;
    @FXML private TableColumn<Producto, Double> colPrecio;
    @FXML private TableColumn<Producto, Integer> colCantidad;
    @FXML private TextField txtBuscar;

    private final ObservableList<Producto> productos = ProductoStore.getProductos();
    private FilteredList<Producto> productosFiltrados;

    @FXML
    private void initialize() {
        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        productosFiltrados = new FilteredList<>(productos, p -> true);
        tablaProductos.setItems(productosFiltrados);
        txtBuscar.textProperty().addListener((obs, anterior, nuevo) -> {
            String filtro = nuevo == null ? "" : nuevo.trim().toLowerCase();
            productosFiltrados.setPredicate(p -> filtro.isEmpty()
                || p.getCodigo().toLowerCase().contains(filtro)
                || p.getNombre().toLowerCase().contains(filtro));
        });
    }

    @FXML
    private void agregarProducto() {
        TextInputDialog dialogo = new TextInputDialog();
        dialogo.setTitle("Nuevo producto");
        dialogo.setHeaderText("Registrar producto");
        dialogo.setContentText("Ingresa los datos separados por punto y coma:\nCódigo; Nombre; Precio; Cantidad");
        Optional<String> respuesta = dialogo.showAndWait();
        if (!respuesta.isPresent()) return;

        String[] datos = respuesta.get().split(";", -1);
        if (datos.length != 4 || datos[0].trim().isEmpty() || datos[1].trim().isEmpty()) {
            mostrarAlerta("Datos inválidos", "Usa el formato: Código; Nombre; Precio; Cantidad");
            return;
        }
        try {
            String codigo = datos[0].trim();
            String nombre = datos[1].trim();
            double precio = Double.parseDouble(datos[2].trim());
            int cantidad = Integer.parseInt(datos[3].trim());
            if (precio < 0 || cantidad < 0) throw new NumberFormatException();
            boolean existe = productos.stream().anyMatch(p -> p.getCodigo().equalsIgnoreCase(codigo));
            if (existe) {
                mostrarAlerta("Código repetido", "Ya existe un producto con ese código.");
                return;
            }
            productos.add(new Producto(codigo, nombre, precio, cantidad));
        } catch (NumberFormatException ex) {
            mostrarAlerta("Datos inválidos", "El precio y la cantidad deben ser números positivos.");
        }
    }

    @FXML
    private void editarProducto() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un producto", "Primero selecciona una fila de la tabla.");
            return;
        }
        TextInputDialog dialogo = new TextInputDialog(seleccionado.getNombre() + "; "
                + seleccionado.getPrecio() + "; " + seleccionado.getCantidad());
        dialogo.setTitle("Editar producto");
        dialogo.setHeaderText("Editar " + seleccionado.getCodigo());
        dialogo.setContentText("Nombre; Precio; Cantidad");
        Optional<String> respuesta = dialogo.showAndWait();
        if (!respuesta.isPresent()) return;
        String[] datos = respuesta.get().split(";", -1);
        if (datos.length != 3 || datos[0].trim().isEmpty()) {
            mostrarAlerta("Datos inválidos", "Usa el formato: Nombre; Precio; Cantidad"); return;
        }
        try {
            double precio = Double.parseDouble(datos[1].trim());
            int cantidad = Integer.parseInt(datos[2].trim());
            if (precio < 0 || cantidad < 0) throw new NumberFormatException();
            seleccionado.setNombre(datos[0].trim());
            seleccionado.setPrecio(precio);
            seleccionado.setCantidad(cantidad);
            tablaProductos.refresh();
        } catch (NumberFormatException ex) {
            mostrarAlerta("Datos inválidos", "Precio y cantidad deben ser números positivos.");
        }
    }

    @FXML
    private void eliminarProducto() {
        Producto seleccionado = tablaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta("Selecciona un producto", "Primero selecciona una fila de la tabla.");
            return;
        }
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION,
            "¿Deseas eliminar '" + seleccionado.getNombre() + "'?", ButtonType.YES, ButtonType.NO);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        if (confirmacion.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            productos.remove(seleccionado);
        }
    }

    @FXML
    private void limpiarBusqueda() {
        txtBuscar.clear();
    }

    @FXML
    private void switchToPrimary() throws IOException {
        Sesion.cerrar();
        App.setRoot("primary");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
