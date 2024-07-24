package com.example.apppizzeria2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.apppizzeria2.Adapters.CombinedMenuListAdapter;
import com.example.apppizzeria2.DAOs.BebidasDAO;
import com.example.apppizzeria2.DAOs.ProductoDAO;
import com.example.apppizzeria2.Models.BebidasModel;
import com.example.apppizzeria2.Models.ProductoModel;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCardActivity extends AppCompatActivity {

    private ListView listViewItems;
    private Button btnBack;

   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_card);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> startActivity(new Intent(ShoppingCardActivity.this, MenuActivity.class)));

        listViewItems = findViewById(R.id.listViewItems);

        // Retrieve selected items from the intent
        List<Object> selectedItems = (List<Object>) getIntent().getSerializableExtra("carrito");
        if (selectedItems == null) {
            selectedItems = new ArrayList<>();
        }

        CombinedMenuListAdapter adapter = new CombinedMenuListAdapter(this, selectedItems);
        listViewItems.setAdapter(adapter);
    }

    private List<Object> getSelectedItems() {
        // This method should return the list of selected items
        // For now, it returns an empty list
        return new ArrayList<>();
    }
}