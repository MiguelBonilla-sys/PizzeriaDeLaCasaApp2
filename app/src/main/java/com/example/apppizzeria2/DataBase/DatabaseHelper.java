package com.example.apppizzeria2.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Nombre de la base de datos
    private static final String DATABASE_NAME = "pizzeriaDeLaCasa.db";
    // Versión de la base de datos. Incrementar este número para ejecutar el método onUpgrade
    private static final int DATABASE_VERSION = 1;
    // Consulta SQL para la creación de la tabla "productos"
    private static final String CREATE_TABLE_PRODUCTS =
            "CREATE TABLE productos (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, " +
                    "descripcion TEXT, " +
                    "precio REAL, " +
                    "stock INTEGER)";

    private static final String CREATE_TABLE_BEBIDAS =
            "CREATE TABLE bebidas (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, " +
                    "descripcion TEXT, " +
                    "precio REAL, " +
                    "stock INTEGER)";

    private static final String CREATE_TABLE_CARRITO_COMPRA =
            "CREATE TABLE IF NOT EXISTS carrito_compra (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "fecha TEXT, " +
                    "usuario_id TEXT)";

    private static final String CREATE_TABLE_CARRITO_COMPRA_PRODUCTO =
            "CREATE TABLE IF NOT EXISTS carrito_compra_producto (" +
                    "carrito_id INTEGER, " +
                    "producto_id INTEGER, " +
                    "bebida_id INTEGER, " +
                    "cantidad INTEGER, " +
                    "precio REAL, " +
                    "PRIMARY KEY (carrito_id, producto_id, bebida_id), " +
                    "FOREIGN KEY (carrito_id) REFERENCES carrito_compra(id), " +
                    "FOREIGN KEY (bebida_id) REFERENCES bebidas(id), " +
                    "FOREIGN KEY (producto_id) REFERENCES productos(id))";

    // Constructor de la clase
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Este método se llama cuando la base de datos se crea por primera vez
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Ejecutar la consulta SQL para crear las tablas
        db.execSQL(CREATE_TABLE_PRODUCTS);
        db.execSQL(CREATE_TABLE_BEBIDAS);
        db.execSQL(CREATE_TABLE_CARRITO_COMPRA);
        db.execSQL(CREATE_TABLE_CARRITO_COMPRA_PRODUCTO);

        // Insertar datos en la tabla "productos"
        db.execSQL("INSERT INTO productos (nombre, descripcion, precio, stock) VALUES ('Pollo con champiñones', 'Una base crujiente, unos bordes perfectamente tostados, suficiente queso (bien derretido), salsa y una serie de ingredientes', 10000, 10);");
        db.execSQL("INSERT INTO productos (nombre, descripcion, precio, stock) VALUES ('Pizza Pepperoni', 'Una combinación clásica de salsa de tomate, queso y pepperoni. con un toque ahumado y picante', 10000, 20);");
        db.execSQL("INSERT INTO productos (nombre, descripcion, precio, stock) VALUES ('Pizza BBQ con Tocineta', 'Preparacion de pollo a la parrilla, tocineta, cebolla, queso y salsa BBQ como base. Comparte su delicioso sabor.', 14000, 10);");

        // Insertar datos en la tabla "bebidas"
        db.execSQL("INSERT INTO bebidas (nombre, descripcion, precio, stock) VALUES ('Gaseosa Coca Cola', 'Bebida refrescante de cola', 3000.0, 10);");
        db.execSQL("INSERT INTO bebidas (nombre, descripcion, precio, stock) VALUES ('Gaseosa Pepsi', 'Bebida refrescante de cola', 3000.0, 10);");
        db.execSQL("INSERT INTO bebidas (nombre, descripcion, precio, stock) VALUES ('Gaseosa Sprite', 'Bebida refrescante de limón', 3000.0, 10);");
        db.execSQL("INSERT INTO bebidas (nombre, descripcion, precio, stock) VALUES ('Gaseosa Fanta', 'Bebida refrescante de limon', 3000.0, 10);");

    }

    // Este método se llama cuando se necesita actualizar la base de datos, es decir,
    // cuando se incrementa DATABASE_VERSION
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar la versión anterior de las tablas
        db.execSQL("DROP TABLE IF EXISTS productos");
        db.execSQL("DROP TABLE IF EXISTS promociones");
        db.execSQL("DROP TABLE IF EXISTS carrito_compra");
        db.execSQL("DROP TABLE IF EXISTS carrito_compra_producto");
        // Crear nuevamente las tablas con la nueva estructura o cambios
        onCreate(db);
    }
}
