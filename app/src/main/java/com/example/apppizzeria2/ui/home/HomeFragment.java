package com.example.apppizzeria2.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.apppizzeria2.Adapters.BebidaMenuListAdapter;
import com.example.apppizzeria2.Adapters.ProductoMenuListAdapter;
import com.example.apppizzeria2.DAOs.BebidasDAO;
import com.example.apppizzeria2.DAOs.ProductoDAO;
import com.example.apppizzeria2.Models.BebidasModel;
import com.example.apppizzeria2.Models.CarritoSingleton;
import com.example.apppizzeria2.Models.ProductoModel;
import com.example.apppizzeria2.ShoppingCardActivity;
import com.example.apppizzeria2.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ProductoDAO productoDAO;
    private BebidasDAO bebidaDAO;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        productoDAO = new ProductoDAO(getContext());
        bebidaDAO = new BebidasDAO(getContext());

        final Button buttonShowProducts = binding.buttonMenu;
        final Button buttonShowDrinks = binding.buttonBebidas;
        final FloatingActionButton buttonShoppingCard = binding.botonShoppingCar;

        buttonShowProducts.setOnClickListener(v -> showProducts());
        buttonShowDrinks.setOnClickListener(v -> showDrinks());

        buttonShoppingCard.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ShoppingCardActivity.class);
            intent.putExtra("carrito", new ArrayList<>(CarritoSingleton.getInstance().getCarrito()));
            startActivity(intent);
        });

        homeViewModel.getText().observe(getViewLifecycleOwner(), binding.textHome::setText);
        return root;
    }

    private void showProducts() {
        List<ProductoModel> productList = productoDAO.obtenerTodosProductos();
        ListView listView = binding.listView2;
        ProductoMenuListAdapter adapter = new ProductoMenuListAdapter(getContext(), productList, CarritoSingleton.getInstance().getCarrito());
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);
    }

    private void showDrinks() {
        List<BebidasModel> drinkList = bebidaDAO.obtenerTodasBebidas();
        ListView listView = binding.listView2;
        BebidaMenuListAdapter adapter = new BebidaMenuListAdapter(getContext(), drinkList, CarritoSingleton.getInstance().getCarrito());
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        showProducts();
    }


}