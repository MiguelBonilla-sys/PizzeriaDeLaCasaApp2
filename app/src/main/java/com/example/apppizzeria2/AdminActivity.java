package com.example.apppizzeria2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    private Button buttonProduct, buttonPromo, buttonDrink, buttonClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        buttonProduct = findViewById(R.id.buttonProduct);
        buttonPromo = findViewById(R.id.buttonPromo);
        buttonDrink = findViewById(R.id.buttonBebi);
        buttonClose = findViewById(R.id.buttonClose);

        buttonProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminProductActivity.class);
                startActivity(intent);
            }
        });

        buttonPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminPromoActivity.class);
                startActivity(intent);
            }
        });

        buttonDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminBebidasActivity.class);
                startActivity(intent);
            }
        });

        buttonClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.cerrarSesionGlobal(AdminActivity.this);
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}