package com.example.apppizzeria2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apppizzeria2.Adapters.PromocionListAdapter;
import com.example.apppizzeria2.DAOs.PromocionDAO;
import com.example.apppizzeria2.Models.PromocionModel;

import java.util.List;

public class AdminPromoActivity extends AppCompatActivity {
    private PromocionDAO promocionDAO;
    private EditText etNamePromo, etDescriptionPromo, etPricePromo, etStockPromo;
    private Button buttonCreatePromo, buttonShowPromos, buttonBack;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_promo);

        promocionDAO = new PromocionDAO(this);
        promocionDAO.abrir();

        etNamePromo = findViewById(R.id.etNamePromo);
        etDescriptionPromo = findViewById(R.id.etDescriptionPromo);
        etPricePromo = findViewById(R.id.etPricePromo);
        etStockPromo = findViewById(R.id.etStockPromo);
        buttonCreatePromo = findViewById(R.id.buttonCreatePromo);
        buttonShowPromos = findViewById(R.id.buttonShowPromo);
        buttonBack = findViewById(R.id.buttonBackPromo);
        listView = findViewById(R.id.listView);

        buttonCreatePromo.setOnClickListener(v -> createPromo());

        buttonShowPromos.setOnClickListener(v -> {
            showPromo();
            buttonShowPromos.setVisibility(View.GONE);
        });

        buttonBack.setOnClickListener(v -> startActivity(new Intent(AdminPromoActivity.this, AdminActivity.class)));
    }

    private void createPromo() {
        String nombre = etNamePromo.getText().toString();
        String descripcion = etDescriptionPromo.getText().toString();
        double precio = Double.parseDouble(etPricePromo.getText().toString());
        int stock = Integer.parseInt(etStockPromo.getText().toString());

        if(!nombre.isEmpty() && !descripcion.isEmpty() && precio > 0 && stock > 0) {
            long resultado = promocionDAO.insertarPromocion(nombre, descripcion, precio, stock);
            if(resultado != -1) {
                Toast.makeText(AdminPromoActivity.this, "Promoción creado correctamente", Toast.LENGTH_SHORT).show();
                etNamePromo.setText("");
                etDescriptionPromo.setText("");
                etPricePromo.setText("");
                etStockPromo.setText("");
            } else {
                Toast.makeText(AdminPromoActivity.this, "Error al crear el Promoción", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AdminPromoActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
        }
        updateListPromo();
    }

    private void showPromo() {
        List<PromocionModel> promociones = promocionDAO.obtenerTodasPromociones();
        StringBuilder stringBuilder = new StringBuilder();
        for(PromocionModel promocion : promociones) {
            stringBuilder.append("ID de producto: ").append(promocion.getId()).append("\n");
            stringBuilder.append("Nombre: ").append(promocion.getNombre()).append("\n");
            stringBuilder.append("Descripción: ").append(promocion.getDescripcion()).append("\n");
            stringBuilder.append("Precio: ").append(promocion.getPrecio()).append("\n");
            stringBuilder.append("Stock: ").append(promocion.getStock()).append("\n\n");
        }

        PromocionListAdapter adapter = new PromocionListAdapter(this, promociones);
        listView.setAdapter(adapter);

        adapter.setOnEditClickListener(new PromocionListAdapter.OnEditClickListener() {
            @Override
            public void onEditClick(int position) {
                PromocionModel promocion = promociones.get(position);
                etNamePromo.setText(promocion.getNombre());
                etDescriptionPromo.setText(promocion.getDescripcion());
                etPricePromo.setText(String.valueOf(promocion.getPrecio()));
                etStockPromo.setText(String.valueOf(promocion.getStock()));
                int idPromo = promocion.getId();
                buttonCreatePromo.setText("Guardar");
                buttonCreatePromo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updatePromo(idPromo);
                    }
                });
            }
        });

        adapter.setOnDeleteClickListener(new PromocionListAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                // Lógica para eliminar un usuario
                PromocionModel promocion = promociones.get(position);
                int idPromo = promocion.getId();
                deletePromo(idPromo);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void updatePromo(int idPromo) {
        String nombre = etNamePromo.getText().toString().trim();
        String descripcion = etDescriptionPromo.getText().toString().trim();
        double precio = Double.parseDouble(etPricePromo.getText().toString().trim());
        int stock = Integer.parseInt(etStockPromo.getText().toString().trim());

        if(!nombre.isEmpty() && !descripcion.isEmpty() && precio > 0 && stock > 0) {
            promocionDAO.actualizarPromocion(idPromo,nombre, descripcion, precio, stock);
            Toast.makeText(AdminPromoActivity.this, "Promoción actualizado correctamente", Toast.LENGTH_SHORT).show();
            etNamePromo.setText("");
            etDescriptionPromo.setText("");
            etPricePromo.setText("");
            etStockPromo.setText("");
            buttonCreatePromo.setText("Agregar");
            buttonCreatePromo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createPromo();
                }
            });
            updateListPromo();
        } else {
            Toast.makeText(AdminPromoActivity.this, "Por favor, rellene todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void deletePromo(int idPromo) {
        promocionDAO.eliminarPromocion(idPromo);
        Toast.makeText(AdminPromoActivity.this, "Promoción eliminado correctamente", Toast.LENGTH_SHORT).show();
        updateListPromo();
    }

    private void updateListPromo() {
        showPromo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        promocionDAO.cerrar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showPromo();
    }
}