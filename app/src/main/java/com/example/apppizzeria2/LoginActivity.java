package com.example.apppizzeria2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etlCorreo, etlContraseña;
    private Button btnLogin, btnregister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        etlCorreo = findViewById(R.id.etlCorreo);
        etlContraseña =findViewById(R.id.etlContraseña);
        btnLogin = findViewById(R.id.btnLogin);
        btnregister = findViewById(R.id.btnregister);


        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish(); // Finaliza esta actividad para que el usuario no pueda volver atrás
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = etlCorreo.getText().toString().trim();
        String password = etlContraseña.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Aquí asumimos que tienes una manera de obtener el rol del usuario
                        // Por ejemplo, desde Firebase Firestore o Realtime Database
                        obtenerRolYContinuar();
                    } else {
                        Toast.makeText(LoginActivity.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void obtenerRolYContinuar() {
        String userId = mAuth.getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference docRef = db.collection("usuarios").document(userId);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    String rol = document.getString("Usuario");
                    if ("Admin".equals(rol)) {
                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                    } else {
                        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                    }
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LoginActivity.this, "Error al obtener el usuario", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void cerrarSesionGlobal(Context context) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Toast.makeText(context, "Sesión cerrada", Toast.LENGTH_SHORT).show();
    }
}