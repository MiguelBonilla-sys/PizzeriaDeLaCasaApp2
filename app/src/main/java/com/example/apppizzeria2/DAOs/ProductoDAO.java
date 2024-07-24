package com.example.apppizzeria2.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apppizzeria2.DataBase.DatabaseHelper;
import com.example.apppizzeria2.Models.ProductoModel;

import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public ProductoDAO(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    // Abre la base de datos para escritura
    public void abrir(){
        db = dbHelper.getWritableDatabase();
    }

    // Cierra la conexión a la base de datos
    public void cerrar(){
        if(db != null && db.isOpen()){
            db.close();
        }
    }

    // Inserta un nuevo producto en la base de datos
    public long insertarProducto(String nombre, String descripcion, double precio, int stock){
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("stock", stock);

        long resultado = -1;
        try {
            resultado = db.insert("productos", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    // Obtiene todos los productos de la base de datos
    public List<ProductoModel> obtenerTodosProductos(){
        abrir(); // Abrir la base de datos antes de realizar operaciones
        List<ProductoModel> productos = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM productos", null);
            if(cursor.moveToFirst()){
                do{
                    ProductoModel producto = new ProductoModel();
                    int idIndex = cursor.getColumnIndex("id");
                    if(idIndex != -1) producto.setId(cursor.getInt(idIndex));

                    int nombreIndex = cursor.getColumnIndex("nombre");
                    if(nombreIndex != -1) producto.setNombre(cursor.getString(nombreIndex));

                    int descripcionIndex = cursor.getColumnIndex("descripcion");
                    if(descripcionIndex != -1) producto.setDescripcion(cursor.getString(descripcionIndex));

                    int precioIndex = cursor.getColumnIndex("precio");
                    if(precioIndex != -1) producto.setPrecio(cursor.getDouble(precioIndex));

                    int stockIndex = cursor.getColumnIndex("stock");
                    if(stockIndex != -1) producto.setStock(cursor.getInt(stockIndex));

                    productos.add(producto);
                }while(cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return productos;
    }

    // Actualiza los datos de un producto existente
    public int actualizarProducto(int id, String nombre, String descripcion, double precio, int stock){
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("stock", stock);

        int resultado = 0;
        try {
            resultado = db.update("productos", values, "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    // Elimina un producto de la base de datos
    public void eliminarProducto(int id){
        try {
            db.delete("productos", "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}