package com.example.apppizzeria2.Models;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompraModel {
    private int id;
    private String fecha;
    private String usuario_id;
    private List<CarritoCompraProductoModel> productos;

    public CarritoCompraModel(int id, String fecha, String usuario_id) {
        this.id = id;
        this.fecha = fecha;
        this.usuario_id = usuario_id;
        this.productos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getUsuario_id() {
        return usuario_id;
    }

    public void setUsuario_id(String usuario_id) {
        this.usuario_id = usuario_id;
    }

    public List<CarritoCompraProductoModel> getProductos() {
        return productos;
    }

    public void setProductos(List<CarritoCompraProductoModel> productos) {
        this.productos = productos;
    }

    // Método para agregar un producto al carrito
    public void agregarProducto(CarritoCompraProductoModel producto) {
        this.productos.add(producto);
    }

    // Método para eliminar un producto del carrito
    public void eliminarProducto(CarritoCompraProductoModel producto) {
        this.productos.remove(producto);
    }
}