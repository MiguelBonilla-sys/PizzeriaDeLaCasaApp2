package com.example.apppizzeria2.Models;

import java.io.Serializable;

public class BebidasModel implements Serializable {
    // Variables
    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private int stock;
    private int quantity;

    public BebidasModel(String nombre, String descripcion, double precio, int stock) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.quantity = 0;
    }
    public BebidasModel() {
    }

    public void updateStock(int quantity) {
        this.stock -= quantity;
    }
    public void updateQuantity(int quantity) {
        this.quantity += quantity;
    }

    public int getQuantity() {
        return quantity;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
