package com.example.apppizzeria2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apppizzeria2.Models.BebidasModel;
import com.example.apppizzeria2.Models.ProductoModel;
import com.example.apppizzeria2.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CombinedMenuListAdapter extends BaseAdapter {

    private Context context;
    private List<Object> items;
    private LayoutInflater inflater;
    private TextView tvPrecioTotal;

    public CombinedMenuListAdapter(Context context, List<Object> items, TextView tvPrecioTotal) {
        this.context = context;
        this.items = (items != null) ? items : new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
        this.tvPrecioTotal = tvPrecioTotal;
        updateTotalPrice();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_combined, parent, false);
            holder = new ViewHolder();
            holder.etNombre = convertView.findViewById(R.id.nombre_item);
            holder.etDescripcion = convertView.findViewById(R.id.descripcion_item);
            holder.etPrecio = convertView.findViewById(R.id.precio_item);
            holder.spinnerQuantity = convertView.findViewById(R.id.spinner_quantity);
            holder.buttonRemove = convertView.findViewById(R.id.button_remove);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Object item = items.get(position);
        if (item instanceof ProductoModel) {
            ProductoModel producto = (ProductoModel) item;
            holder.etNombre.setText(producto.getNombre());
            holder.etDescripcion.setText(producto.getDescripcion());
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
            String precioFormateado = format.format(producto.getPrecio());
            holder.etPrecio.setText(precioFormateado);

            holder.spinnerQuantity.setSelection(producto.getQuantity() - 1);
            holder.spinnerQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int quantity = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    if (quantity + producto.getQuantity() > 5) {
                        Toast.makeText(context, "El máximo es 5", Toast.LENGTH_SHORT).show();
                        holder.spinnerQuantity.setSelection(producto.getQuantity() - 1);
                    } else {
                        producto.updateQuantity(quantity);
                        producto.updateStock(quantity);
                        updateTotalPrice();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            });
        } else if (item instanceof BebidasModel) {
            BebidasModel bebida = (BebidasModel) item;
            holder.etNombre.setText(bebida.getNombre());
            holder.etDescripcion.setText(bebida.getDescripcion());
            NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
            String precio = format.format(bebida.getPrecio());
            holder.etPrecio.setText(precio);

            holder.spinnerQuantity.setSelection(bebida.getQuantity() - 1);
            holder.spinnerQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int quantity = Integer.parseInt(parent.getItemAtPosition(position).toString());
                    if (quantity + bebida.getQuantity() > 5) {
                        Toast.makeText(context, "El máximo es 5", Toast.LENGTH_SHORT).show();
                        holder.spinnerQuantity.setSelection(bebida.getQuantity() - 1);
                    } else {
                        bebida.updateQuantity(quantity);
                        bebida.updateStock(quantity);
                        updateTotalPrice();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            });
        }

        holder.buttonRemove.setOnClickListener(v -> {
            items.remove(position);
            notifyDataSetChanged();
            updateTotalPrice();
        });

        return convertView;
    }

    private void updateTotalPrice() {
        double totalPrice = 0.0;
        for (Object item : items) {
            if (item instanceof ProductoModel) {
                totalPrice += ((ProductoModel) item).getPrecio() * ((ProductoModel) item).getQuantity();
            } else if (item instanceof BebidasModel) {
                totalPrice += ((BebidasModel) item).getPrecio() * ((BebidasModel) item).getQuantity();
            }
        }

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        String formattedTotalPrice = format.format(totalPrice);
        tvPrecioTotal.setText(formattedTotalPrice);
    }

    static class ViewHolder {
        TextView etNombre;
        TextView etDescripcion;
        TextView etPrecio;
        Spinner spinnerQuantity;
        Button buttonRemove;
    }
}