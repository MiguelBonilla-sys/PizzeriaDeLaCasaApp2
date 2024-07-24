package com.example.apppizzeria2.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apppizzeria2.DataBase.DatabaseHelper;
import com.example.apppizzeria2.Models.BebidasModel;

import java.util.ArrayList;
import java.util.List;
public class BebidasDAO {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public BebidasDAO(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        if(db != null && db.isOpen()){
            db.close();
        }
    }

    // Inserta un nuevo producto en la base de datos
    public long insertarBebida(String nombre, String descripcion, double precio, int stock){
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("stock", stock);

        long resultado = -1;
        try {
            resultado = db.insert("bebidas", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    // Obtiene todos los productos de la base de datos
    public List<BebidasModel> obtenerTodasBebidas(){
        open(); // Abrir la base de datos antes de realizar operaciones
        List<BebidasModel> bebidas = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM bebidas", null);
            if(cursor.moveToFirst()){
                do{
                    BebidasModel bebida = new BebidasModel();
                    int idIndex = cursor.getColumnIndex("id");
                    if(idIndex != -1) bebida.setId(cursor.getInt(idIndex));

                    int nombreIndex = cursor.getColumnIndex("nombre");
                    if(nombreIndex != -1) bebida.setNombre(cursor.getString(nombreIndex));

                    int descripcionIndex = cursor.getColumnIndex("descripcion");
                    if(descripcionIndex != -1) bebida.setDescripcion(cursor.getString(descripcionIndex));

                    int precioIndex = cursor.getColumnIndex("precio");
                    if(precioIndex != -1) bebida.setPrecio(cursor.getDouble(precioIndex));

                    int stockIndex = cursor.getColumnIndex("stock");
                    if(stockIndex != -1) bebida.setStock(cursor.getInt(stockIndex));

                    bebidas.add(bebida);
                }while(cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
            close(); // Cerrar la base de datos para evitar fugas de memoria
        }
        return bebidas;
    }

    public int actualizarBebida(int id, String nombre, String descripcion, double precio, int stock){
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("stock", stock);

        int resultado = 0;
        try {
            resultado = db.update("bebidas", values, "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void eliminarBebida(int id){
        try {
            db.delete("bebidas", "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
