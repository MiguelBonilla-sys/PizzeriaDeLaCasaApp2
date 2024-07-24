package com.example.apppizzeria2.Models;

public class CarritoCompraProductoModel {
    private int carrito_id;
    private int producto_id;
    private int bebida_id;
    private String promocion_id;
    private int cantidad;
    private double precio;

    public CarritoCompraProductoModel(int carrito_id, int producto_id, int bebida_id, String promocion_id, int cantidad, double precio) {
        this.carrito_id = carrito_id;
        this.producto_id = producto_id;
        this.bebida_id = bebida_id;
        this.promocion_id = promocion_id;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    public int getCarrito_id() {
        return carrito_id;
    }

    public void setCarrito_id(int carrito_id) {
        this.carrito_id = carrito_id;
    }

    public int getProducto_id() {
        return producto_id;
    }

    public void setProducto_id(int producto_id) {
        this.producto_id = producto_id;
    }

    public int getBebida_id() {
        return bebida_id;
    }

    public void setBebida_id(int bebida_id) {
        this.bebida_id = bebida_id;
    }

    public String getPromocion_id() {
        return promocion_id;
    }

    public void setPromocion_id(String promocion_id) {
        this.promocion_id = promocion_id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}