package com.mycompany.ferreteria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Lista compartida para que los cambios administrativos se vean en el catálogo del cliente. */
public final class ProductoStore {
    private static final ObservableList<Producto> PRODUCTOS = FXCollections.observableArrayList();
    static {
        PRODUCTOS.addAll(
            new Producto("FER-001", "Martillo", 25000, 12),
            new Producto("FER-002", "Destornillador", 12000, 25),
            new Producto("FER-003", "Taladro eléctrico", 185000, 7),
            new Producto("FER-004", "Pintura blanca 1 galón", 68000, 10),
            new Producto("FER-005", "Cinta métrica", 15000, 18)
        );
    }
    private ProductoStore() { }
    public static ObservableList<Producto> getProductos() { return PRODUCTOS; }
}
