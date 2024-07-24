package com.example.apppizzeria2.Models;

import java.util.ArrayList;
import java.util.List;

public class CarritoSingleton {
    private static CarritoSingleton instance;
    private List<Object> carrito;

    private CarritoSingleton() {
        carrito = new ArrayList<>();
    }

    public static synchronized CarritoSingleton getInstance() {
        if (instance == null) {
            instance = new CarritoSingleton();
        }
        return instance;
    }

    public List<Object> getCarrito() {
        return carrito;
    }

    public void addItem(Object item) {
        carrito.add(item);
    }
}