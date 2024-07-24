package com.example.apppizzeria2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.apppizzeria2.Models.ProductoModel;
import com.example.apppizzeria2.R;

import java.util.List;

public class ProductoListAdapter extends BaseAdapter{
    // Variables
    private List<ProductoModel> productos; // Lista de productos a mostrar
    private LayoutInflater inflater; // Inflador de diseño para inflar vistas
    private OnEditClickListener editClickListener; // Escucha para clics en el botón de editar
    private OnDeleteClickListener deleteClickListener; // Escucha para clics en el botón de eliminar

    // Interfaz para escuchar clics en el botón de editar
    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    // Interfaz para escuchar clics en el botón de eliminar
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    // Métodos para establecer los listeners
    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    // Constructor
    public ProductoListAdapter(Context context, List<ProductoModel> productos) {
        this.productos = productos;
        this.inflater = LayoutInflater.from(context);
    }

    // Devuelve el número de elementos en la lista
    @Override
    public int getCount() {
        return productos.size();
    }

    // Devuelve el producto en la posición especificada
    @Override
    public ProductoModel getItem(int position) {
        return productos.get(position);
    }

    // Devuelve el ID del elemento en la posición especificada
    @Override
    public long getItemId(int position) {
        return productos.get(position).getId();
    }

    // Devuelve la vista que representa a un elemento de la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_producto, null);
            holder = new ViewHolder();
            holder.tvIdProduct = convertView.findViewById(R.id.etIdProduct);
            holder.tvNombreProduct = convertView.findViewById(R.id.etNombreProduct);
            holder.tvDescripcionProduct = convertView.findViewById(R.id.tvDescripcionProduct);
            holder.tvPrecioProduct = convertView.findViewById(R.id.tvPrecioProduct);
            holder.tvStockProduct = convertView.findViewById(R.id.tvStockProduct);
            holder.btnEditarProduct = convertView.findViewById(R.id.btnEditarProduct);
            holder.btnEliminarProduct = convertView.findViewById(R.id.btnEliminarProduct);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductoModel producto = productos.get(position);

        holder.tvIdProduct.setText(String.valueOf("ID de producto:"+producto.getId()));
        holder.tvNombreProduct.setText("Nombre: "+producto.getNombre());
        holder.tvDescripcionProduct.setText("Descripción: "+producto.getDescripcion());
        holder.tvPrecioProduct.setText("Precio: "+producto.getPrecio());
        holder.tvStockProduct.setText("Stock: "+producto.getStock());

        holder.btnEditarProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editClickListener != null) {
                    editClickListener.onEditClick(position);
                }
            }
        });

        holder.btnEliminarProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(position);
                }
            }
        });

        return convertView; // Devolver la vista actualizada
    }

    static class ViewHolder {
        TextView tvIdProduct;
        TextView tvNombreProduct;
        TextView tvDescripcionProduct;
        TextView tvPrecioProduct;
        TextView tvStockProduct;
        Button btnEditarProduct;
        Button btnEliminarProduct;
    }
}