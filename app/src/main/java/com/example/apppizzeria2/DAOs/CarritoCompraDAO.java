package com.example.apppizzeria2.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.apppizzeria2.DataBase.DatabaseHelper;
import com.example.apppizzeria2.Models.CarritoCompraModel;
import com.example.apppizzeria2.Models.CarritoCompraProductoModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class CarritoCompraDAO {
    private SQLiteOpenHelper dbHelper;
    private FirebaseAuth auth;

    public CarritoCompraDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        auth = FirebaseAuth.getInstance();
    }

    public void agregarCarrito(CarritoCompraModel carrito) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String userId = auth.getCurrentUser().getUid();
        carrito.setUsuario_id(userId);

        ContentValues values = new ContentValues();
        values.put("fecha", carrito.getFecha());
        values.put("usuario_id", carrito.getUsuario_id());

        long carritoId = db.insert("carrito_compra", null, values);
        for (CarritoCompraProductoModel producto : carrito.getProductos()) {
            agregarProductoACarrito(carritoId, producto);
        }
        db.close();
    }

    public void agregarProductoACarrito(long carritoId, CarritoCompraProductoModel producto) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("carrito_id", carritoId);
        values.put("producto_id", producto.getProducto_id());
        values.put("bebida_id", producto.getBebida_id());
        values.put("cantidad", producto.getCantidad());
        values.put("precio", producto.getPrecio());

        db.insert("carrito_compra_producto", null, values);
        db.close();
    }

    public List<CarritoCompraModel> obtenerCarritos() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String userId = auth.getCurrentUser().getUid();

        Cursor cursor = db.query("carrito_compra", null, "usuario_id = ?", new String[]{userId}, null, null, null);
        List<CarritoCompraModel> carritos = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int idIndex = cursor.getColumnIndex("id");
                int fechaIndex = cursor.getColumnIndex("fecha");

                if (idIndex >= 0 && fechaIndex >= 0) {
                    int id = cursor.getInt(idIndex);
                    String fecha = cursor.getString(fechaIndex);
                    CarritoCompraModel carrito = new CarritoCompraModel(id, fecha, userId);
                    carrito.setProductos(obtenerProductosDeCarrito(id));
                    carritos.add(carrito);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return carritos;
    }

    public List<CarritoCompraProductoModel> obtenerProductosDeCarrito(int carritoId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query("carrito_compra_producto", null, "carrito_id = ?", new String[]{String.valueOf(carritoId)}, null, null, null);
        List<CarritoCompraProductoModel> productos = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                int productoIdIndex = cursor.getColumnIndex("producto_id");
                int bebidaIdIndex = cursor.getColumnIndex("bebida_id");
                int cantidadIndex = cursor.getColumnIndex("cantidad");
                int precioIndex = cursor.getColumnIndex("precio");

                if (productoIdIndex >= 0 && bebidaIdIndex >= 0 && cantidadIndex >= 0 && precioIndex >= 0) {
                    int productoId = cursor.getInt(productoIdIndex);
                    int bebidaId = cursor.getInt(bebidaIdIndex);
                    int cantidad = cursor.getInt(cantidadIndex);
                    double precio = cursor.getDouble(precioIndex);
                    CarritoCompraProductoModel producto = new CarritoCompraProductoModel(carritoId, productoId, bebidaId, null, cantidad, precio);
                    productos.add(producto);
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productos;
    }
}