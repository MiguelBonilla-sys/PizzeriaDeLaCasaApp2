package com.example.apppizzeria2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ProfileActivity  extends AppCompatActivity{

    private Button btnBack, btnUpdate;
    private EditText etName, etEmail, etPassword, etLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnBack = findViewById(R.id.btnback);
        btnUpdate = findViewById(R.id.btnUpdate);
        etName = findViewById(R.id.etName); // Make sure ID matches with your layout
        etEmail = findViewById(R.id.etEmail); // Make sure ID matches with your layout
        etPassword = findViewById(R.id.etPassword); // Make sure ID matches with your layout
        etLocation = findViewById(R.id.etLocation); // Make sure ID matches with your layout

        btnUpdate.setOnClickListener(view -> actualizarDatosUsuario());

        btnBack.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, MenuActivity.class);
            startActivity(intent);
            finish();
        });

        cargarDatosUsuario();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_close) {
            LoginActivity.cerrarSesionGlobal(ProfileActivity.this);
            Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void cargarDatosUsuario() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("usuarios").document(userId);

        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    etName.setText(document.getString("name"));
                    etEmail.setText(document.getString("email"));
                    etPassword.setText(document.getString("password"));
                    etLocation.setText(document.getString("direccion"));
                } else {
                    Toast.makeText(ProfileActivity.this, "No se encontraron datos del usuario. Por favor, intenta nuevamente.", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(ProfileActivity.this, "Error al cargar los datos. Por favor, verifica tu conexión y vuelve a intentarlo.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void actualizarDatosUsuario() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> user = new HashMap<>();
        user.put("name", etName.getText().toString());
        user.put("email", etEmail.getText().toString());
        user.put("password", etPassword.getText().toString());
        user.put("direccion", etLocation.getText().toString());
        // Añade más campos según sea necesario

        db.collection("usuarios").document(userId)
                .update(user)
                .addOnSuccessListener(aVoid -> {
                    // Manejar éxito de la actualización
                    Toast.makeText(ProfileActivity.this, "Datos actualizados con éxito", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Manejar el error
                    Toast.makeText(ProfileActivity.this, "Error al actualizar los datos", Toast.LENGTH_SHORT).show();
                });
    }

}
