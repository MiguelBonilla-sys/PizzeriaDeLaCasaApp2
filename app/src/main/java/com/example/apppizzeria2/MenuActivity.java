package com.example.apppizzeria2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.apppizzeria2.Adapters.BebidaMenuListAdapter;
import com.example.apppizzeria2.Adapters.ProductoMenuListAdapter;
import com.example.apppizzeria2.DAOs.BebidasDAO;
import com.example.apppizzeria2.DAOs.ProductoDAO;
import com.example.apppizzeria2.Models.BebidasModel;
import com.example.apppizzeria2.Models.ProductoModel;
import com.example.apppizzeria2.Models.CarritoSingleton;
import com.example.apppizzeria2.databinding.ActivityMenuBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenu.toolbar);

        // Initialize DAOs
        BebidasDAO bebidaDAO = new BebidasDAO(this);
        ProductoDAO productoDAO = new ProductoDAO(this);

        // Initialize and set up buttons
        Button buttonShowProducts = findViewById(R.id.buttonMenu);
        Button buttonShowDrinks = findViewById(R.id.buttonBebidas);
        FloatingActionButton buttonShoppingCard = findViewById(R.id.botonShoppingCar);

        buttonShowProducts.setOnClickListener(v -> showProducts(productoDAO));
        buttonShowDrinks.setOnClickListener(v -> showDrinks(bebidaDAO));

        buttonShoppingCard.setOnClickListener(v -> {
            Intent intent = new Intent(MenuActivity.this, ShoppingCardActivity.class);
            intent.putExtra("carrito", new ArrayList<>(CarritoSingleton.getInstance().getCarrito()));
            startActivity(intent);
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_send)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(MenuActivity.this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showProducts(ProductoDAO productoDAO) {
        List<ProductoModel> productList = productoDAO.obtenerTodosProductos();
        ListView listView = findViewById(R.id.listView2);
        ProductoMenuListAdapter adapter = new ProductoMenuListAdapter(this, productList, CarritoSingleton.getInstance().getCarrito());
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);
    }

    private void showDrinks(BebidasDAO bebidaDAO) {
        List<BebidasModel> drinkList = bebidaDAO.obtenerTodasBebidas();
        ListView listView = findViewById(R.id.listView2);
        BebidaMenuListAdapter adapter = new BebidaMenuListAdapter(this, drinkList, CarritoSingleton.getInstance().getCarrito());
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);
    }
}