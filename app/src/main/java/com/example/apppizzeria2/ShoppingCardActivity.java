package com.example.apppizzeria2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apppizzeria2.Adapters.CombinedMenuListAdapter;
import com.example.apppizzeria2.Models.BebidasModel;
import com.example.apppizzeria2.Models.ProductoModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ShoppingCardActivity extends AppCompatActivity {

    private ListView listViewItems;
    private Button btnBack, btnRealizarPedido;
    private TextView tvlocalizacion, tvPrecioTotal;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private List<Object> selectedItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_card);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> startActivity(new Intent(ShoppingCardActivity.this, MenuActivity.class)));

        listViewItems = findViewById(R.id.listViewItems);
        tvlocalizacion = findViewById(R.id.tvlocation);
        tvPrecioTotal = findViewById(R.id.tvPrecioTotal);
        btnRealizarPedido = findViewById(R.id.btnRealizarPedido);

        // Retrieve selected items from the intent
        selectedItems = (List<Object>) getIntent().getSerializableExtra("carrito");
        if (selectedItems == null) {
            selectedItems = new ArrayList<>();
        }

        CombinedMenuListAdapter adapter = new CombinedMenuListAdapter(this, selectedItems, tvPrecioTotal);
        listViewItems.setAdapter(adapter);

        // Check if user is logged in and retrieve location
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DocumentReference docRef = db.collection("usuarios").document(userId);
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String location = document.getString("direccion");
                        tvlocalizacion.setText(location);
                    } else {
                        Toast.makeText(ShoppingCardActivity.this, "No se encontró la dirección del usuario.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ShoppingCardActivity.this, "Error al obtener la dirección del usuario.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No hay usuario autenticado.", Toast.LENGTH_SHORT).show();
        }

        // Set OnClickListener for btnRealizarPedido
        btnRealizarPedido.setOnClickListener(v -> saveOrderToFirebase());
    }

   private void saveOrderToFirebase() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("HistorialPedidos").child(userId);

            Map<String, Object> orderDetails = new HashMap<>();
            orderDetails.put("location", tvlocalizacion.getText().toString());
            orderDetails.put("totalPrice", tvPrecioTotal.getText().toString());

            List<Map<String, Object>> itemsList = new ArrayList<>();
            for (Object item : selectedItems) {
                Map<String, Object> itemDetails = new HashMap<>();
                if (item instanceof ProductoModel) {
                    ProductoModel producto = (ProductoModel) item;
                    itemDetails.put("name", producto.getNombre());
                    itemDetails.put("description", producto.getDescripcion());
                    itemDetails.put("price", producto.getPrecio());
                    itemDetails.put("quantity", producto.getQuantity());
                } else if (item instanceof BebidasModel) {
                    BebidasModel bebida = (BebidasModel) item;
                    itemDetails.put("name", bebida.getNombre());
                    itemDetails.put("description", bebida.getDescripcion());
                    itemDetails.put("price", bebida.getPrecio());
                    itemDetails.put("quantity", bebida.getQuantity());
                }
                itemsList.add(itemDetails);
            }
            orderDetails.put("items", itemsList);

            databaseReference.push().setValue(orderDetails).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(ShoppingCardActivity.this, "Pedido realizado con éxito.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ShoppingCardActivity.this, MenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ShoppingCardActivity.this, "Error al realizar el pedido.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No hay usuario autenticado.", Toast.LENGTH_SHORT).show();
        }
    }
}