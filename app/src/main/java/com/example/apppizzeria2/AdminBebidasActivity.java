package com.example.apppizzeria2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apppizzeria2.Adapters.BebidasListAdapter;
import com.example.apppizzeria2.DAOs.BebidasDAO;
import com.example.apppizzeria2.Models.BebidasModel;

import java.util.List;

public class AdminBebidasActivity extends AppCompatActivity {

    private BebidasDAO bebidasDAO;
    private EditText etNameBebida, etDescriptionBebida, etPriceBebida, etStockBebida;
    private Button buttonCreateBebida, buttonShowBebidas, buttonback;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_bebidas);

        bebidasDAO = new BebidasDAO(this);
        bebidasDAO.open();

        etNameBebida = findViewById(R.id.etNameBebi);
        etDescriptionBebida = findViewById(R.id.etDescriptionBebi);
        etPriceBebida = findViewById(R.id.etPriceBebi);
        etStockBebida = findViewById(R.id.etStockBebi);
        buttonCreateBebida = findViewById(R.id.buttonCreateBebi);
        buttonShowBebidas = findViewById(R.id.buttonShowBebi);
        buttonback = findViewById(R.id.buttonBackBebi);
        listView = findViewById(R.id.listView);

        buttonCreateBebida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDrinks();
            }
        });

        buttonShowBebidas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDrinks();
                buttonShowBebidas.setVisibility(View.GONE);
            }
        });

        buttonback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminBebidasActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }


    private void createDrinks() {
        String nombre = etNameBebida.getText().toString();
        String descripcion = etDescriptionBebida.getText().toString();
        double precio = Double.parseDouble(etPriceBebida.getText().toString());
        int stock = Integer.parseInt(etStockBebida.getText().toString());

        if(!nombre.isEmpty() && !descripcion.isEmpty() && precio > 0 && stock > 0) {
            long resultado = bebidasDAO.insertarBebida(nombre, descripcion, precio, stock);
            if(resultado != -1) {
                Toast.makeText(AdminBebidasActivity.this, "Bebida creado correctamente", Toast.LENGTH_SHORT).show();
                etNameBebida.setText("");
                etDescriptionBebida.setText("");
                etPriceBebida.setText("");
                etStockBebida.setText("");
            } else {
                Toast.makeText(AdminBebidasActivity.this, "Error al crear el Bebida", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AdminBebidasActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
        }
        updateListDrinks();
    }

    private void showDrinks() {
        List<BebidasModel> bebidas = bebidasDAO.obtenerTodasBebidas();
        StringBuilder stringBuilder = new StringBuilder();
        for(BebidasModel bebida : bebidas) {
            stringBuilder.append("ID de Bebida: ").append(bebida.getId()).append("\n");
            stringBuilder.append("Nombre: ").append(bebida.getNombre()).append("\n");
            stringBuilder.append("Descripcion: ").append(bebida.getDescripcion()).append("\n");
            stringBuilder.append("Precio: ").append(bebida.getPrecio()).append("\n");
            stringBuilder.append("Stock: ").append(bebida.getStock()).append("\n");
        }
        BebidasListAdapter adapter = new BebidasListAdapter(this, bebidas);
        listView.setAdapter(adapter);

        adapter.setOnEditClickListener(new BebidasListAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(int position) {
                BebidasModel bebida = bebidas.get(position);
                etNameBebida.setText(bebida.getNombre());
                etDescriptionBebida.setText(bebida.getDescripcion());
                etPriceBebida.setText(String.valueOf(bebida.getPrecio()));
                etStockBebida.setText(String.valueOf(bebida.getStock()));
                int idbebida= bebida.getId();
                buttonCreateBebida.setText("Guardar");
                buttonCreateBebida.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateDrinks(idbebida);
                    }
                });
            }
        });

        adapter.setOnDeleteClickListener(new BebidasListAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Lógica para eliminar un usuario
                BebidasModel bebida = bebidas.get(position);
                int idbebida= bebida.getId();
                deleteDrinks(idbebida);
                adapter.notifyDataSetChanged();
            }
        });
        }



    private void updateDrinks(int idBebida) {
        String nombre = etNameBebida.getText().toString();
        String descripcion = etDescriptionBebida.getText().toString();
        double precio = Double.parseDouble(etPriceBebida.getText().toString());
        int stock = Integer.parseInt(etStockBebida.getText().toString());

        if(!nombre.isEmpty() && !descripcion.isEmpty() && precio > 0 && stock > 0) {
            bebidasDAO.actualizarBebida(idBebida,nombre, descripcion, precio, stock);
            Toast.makeText(AdminBebidasActivity.this, "Bebida actualizado correctamente", Toast.LENGTH_SHORT).show();
            etNameBebida.setText("");
            etDescriptionBebida.setText("");
            etPriceBebida.setText("");
            etStockBebida.setText("");
            buttonCreateBebida.setText("Crear");

            buttonCreateBebida.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDrinks();
                }
            });
            updateListDrinks();
        } else {
            Toast.makeText(AdminBebidasActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();

        }
    }

    private void deleteDrinks(int idBebida) {
        bebidasDAO.eliminarBebida(idBebida);
        Toast.makeText(AdminBebidasActivity.this, "Bebida eliminado correctamente", Toast.LENGTH_SHORT).show();
        updateListDrinks();
    }

    private void updateListDrinks(){
        showDrinks();
    }





    protected void onDestroy() {
        super.onDestroy();
        bebidasDAO.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showDrinks();
    }
}