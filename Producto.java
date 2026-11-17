package com.mycompany.ferreteria;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Producto {
    private final StringProperty codigo = new SimpleStringProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final DoubleProperty precio = new SimpleDoubleProperty();
    private final IntegerProperty cantidad = new SimpleIntegerProperty();

    public Producto(String codigo, String nombre, double precio, int cantidad) {
        this.codigo.set(codigo);
        this.nombre.set(nombre);
        this.precio.set(precio);
        this.cantidad.set(cantidad);
    }

    public String getCodigo() { return codigo.get(); }
    public void setCodigo(String valor) { codigo.set(valor); }
    public StringProperty codigoProperty() { return codigo; }

    public String getNombre() { return nombre.get(); }
    public void setNombre(String valor) { nombre.set(valor); }
    public StringProperty nombreProperty() { return nombre; }

    public double getPrecio() { return precio.get(); }
    public void setPrecio(double valor) { precio.set(valor); }
    public DoubleProperty precioProperty() { return precio; }

    public int getCantidad() { return cantidad.get(); }
    public void setCantidad(int valor) { cantidad.set(valor); }
    public IntegerProperty cantidadProperty() { return cantidad; }
}
