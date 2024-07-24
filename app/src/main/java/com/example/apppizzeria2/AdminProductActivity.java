package com.example.apppizzeria2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apppizzeria2.Adapters.ProductoListAdapter;
import com.example.apppizzeria2.DAOs.ProductoDAO;
import com.example.apppizzeria2.Models.ProductoModel;

import java.util.List;

public class AdminProductActivity extends AppCompatActivity {
    // Variables
    private ProductoDAO productoDAO;
    private EditText etNameProduct, etDescriptionProduct, etPriceProduct, etStockProduct;
    private Button buttonCreateProduct, buttonShowProducts, buttonback;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);

        productoDAO = new ProductoDAO(this);
        productoDAO.abrir();

        etNameProduct = findViewById(R.id.etNameProduct);
        etDescriptionProduct = findViewById(R.id.etDescriptionProduct);
        etPriceProduct = findViewById(R.id.etPriceProduct);
        etStockProduct = findViewById(R.id.etStockProduct);
        buttonCreateProduct = findViewById(R.id.buttonCreateProduct); // Asignar correctamente
        buttonShowProducts = findViewById(R.id.buttonShowProducts);   // Asignar correctamente
        buttonback = findViewById(R.id.buttonBackProduct);
        listView = findViewById(R.id.listView);

        buttonCreateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProduct();
            }
        });

        buttonShowProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProducts();
                buttonShowProducts.setVisibility(View.GONE);
            }
        });

        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminProductActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createProduct() {
        String nombre = etNameProduct.getText().toString();
        String descripcion = etDescriptionProduct.getText().toString();
        double precio = Double.parseDouble(etPriceProduct.getText().toString());
        int stock = Integer.parseInt(etStockProduct.getText().toString());

        if(!nombre.isEmpty() && !descripcion.isEmpty() && precio > 0 && stock > 0) {
            long resultado = productoDAO.insertarProducto(nombre, descripcion, precio, stock);
            if(resultado != -1) {
                Toast.makeText(AdminProductActivity.this, "Producto creado correctamente", Toast.LENGTH_SHORT).show();
                etNameProduct.setText("");
                etDescriptionProduct.setText("");
                etPriceProduct.setText("");
                etStockProduct.setText("");
            } else {
                Toast.makeText(AdminProductActivity.this, "Error al crear el producto", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AdminProductActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
        }
        updateListProducts();
    }

    private void showProducts() {
        List<ProductoModel> productos = productoDAO.obtenerTodosProductos();
        StringBuilder stringBuilder = new StringBuilder();
        for(ProductoModel producto : productos) {
            stringBuilder.append("ID de producto: ").append(producto.getId()).append("\n");
            stringBuilder.append("Nombre: ").append(producto.getNombre()).append("\n");
            stringBuilder.append("Descripción: ").append(producto.getDescripcion()).append("\n");
            stringBuilder.append("Precio: ").append(producto.getPrecio()).append("\n");
            stringBuilder.append("Stock: ").append(producto.getStock()).append("\n\n");
        }
        ProductoListAdapter adapter = new ProductoListAdapter(this, productos);
        listView.setAdapter(adapter);

        adapter.setOnEditClickListener(new ProductoListAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(int position) {
                ProductoModel producto = productos.get(position);
                etNameProduct.setText(producto.getNombre());
                etDescriptionProduct.setText(producto.getDescripcion());
                etPriceProduct.setText(String.valueOf(producto.getPrecio()));
                etStockProduct.setText(String.valueOf(producto.getStock()));
                int idProducto = producto.getId();
                buttonCreateProduct.setText("Guardar");
                buttonCreateProduct.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateProduct(idProducto);
                    }
                });
            }
        });

        adapter.setOnDeleteClickListener(new ProductoListAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Lógica para eliminar un usuario
                ProductoModel producto = productos.get(position);
                int idProducto = producto.getId();
                deleteProduct(idProducto);
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void updateProduct(int idProducto) {
        String nombre = etNameProduct.getText().toString().trim();
        String descripcion = etDescriptionProduct.getText().toString().trim();
        double precio = Double.parseDouble(etPriceProduct.getText().toString().trim());
        int stock = Integer.parseInt(etStockProduct.getText().toString().trim());

        if(!nombre.isEmpty() && !descripcion.isEmpty() && precio > 0 && stock > 0) {
            productoDAO.actualizarProducto(idProducto,nombre, descripcion, precio, stock);
            Toast.makeText(AdminProductActivity.this, "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
            etNameProduct.setText("");
            etDescriptionProduct.setText("");
            etPriceProduct.setText("");
            etStockProduct.setText("");
            buttonCreateProduct.setText("Agregar");
            buttonCreateProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createProduct();
                }
            });
            updateListProducts();
        } else {
            Toast.makeText(AdminProductActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteProduct(int idProducto) {
        productoDAO.eliminarProducto(idProducto);
        Toast.makeText(AdminProductActivity.this, "Producto eliminado correctamente", Toast.LENGTH_SHORT).show();
        updateListProducts();
    }

    private void updateListProducts(){
        showProducts();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        productoDAO.cerrar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showProducts();
    }

}
