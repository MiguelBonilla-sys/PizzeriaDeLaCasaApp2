package com.example.apppizzeria2.DAOs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.apppizzeria2.DataBase.DatabaseHelper;
import com.example.apppizzeria2.Models.PromocionModel;

import java.util.ArrayList;
import java.util.List;

public class PromocionDAO {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public PromocionDAO(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void abrir(){
        db = dbHelper.getWritableDatabase();
    }

    public void cerrar(){
        dbHelper.close();
    }

    public long insertarPromocion(String nombre, String descripcion, double precio, int stock){
        ContentValues valores = new ContentValues();
        valores.put("nombre", nombre);
        valores.put("descripcion", descripcion);
        valores.put("precio", precio);
        valores.put("stock", stock);

        long resultado = -1;
        try{
            resultado = db.insert("promociones", null, valores);
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }

    public List<PromocionModel> obtenerTodasPromociones(){
        List<PromocionModel> promociones = new ArrayList<>();
        Cursor cursor = null;
        try{
            cursor = db.rawQuery("SELECT * FROM promociones", null);
            if(cursor.moveToFirst()){
                do{
                    PromocionModel promocion = new PromocionModel();
                    promocion.setId(cursor.getInt(0));
                    promocion.setNombre(cursor.getString(1));
                    promocion.setDescripcion(cursor.getString(2));
                    promocion.setPrecio(cursor.getDouble(3));
                    promocion.setStock(cursor.getInt(4));
                    promociones.add(promocion);
                }while(cursor.moveToNext());
            }
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(cursor != null && !cursor.isClosed()){
                cursor.close();
            }
        }
        return promociones;
    }

    public int actualizarPromocion(int id, String nombre, String descripcion, double precio, int stock){
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("descripcion", descripcion);
        values.put("precio", precio);
        values.put("stock", stock);

        int resultado = 0;
        try {
            resultado = db.update("promociones", values, "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public void eliminarPromocion(int id){
        try {
            db.delete("promociones", "id = ?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

