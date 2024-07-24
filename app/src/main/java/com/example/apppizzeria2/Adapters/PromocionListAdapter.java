package com.example.apppizzeria2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.apppizzeria2.Models.PromocionModel;
import com.example.apppizzeria2.R;

import java.util.List;

public class PromocionListAdapter extends BaseAdapter{

    private List<PromocionModel> promociones; // Lista de promociones a mostrar
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

    public PromocionListAdapter(Context context, List<PromocionModel> promociones) {
        this.promociones = promociones;
        this.inflater = LayoutInflater.from(context);
    }

    // Devuelve el número de elementos en la lista
    @Override
    public int getCount() {
        return promociones.size();
    }

    // Devuelve el producto en la posición especificada
    @Override
    public PromocionModel getItem(int position) {
        return promociones.get(position);
    }

    // Devuelve el ID del elemento en la posición especificada
    @Override
    public long getItemId(int position) {
        return promociones.get(position).getId();
    }

    // Devuelve la vista de un elemento de la lista
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.list_item_promociones, null);
            holder = new ViewHolder();
            holder.tvIdPromo = convertView.findViewById(R.id.etIdPromo);
            holder.tvNombrePromo = convertView.findViewById(R.id.etNombrePromo);
            holder.tvDescripcionPromo = convertView.findViewById(R.id.tvDescripcionPromo);
            holder.tvPrecioPromo = convertView.findViewById(R.id.tvPrecioPromo);
            holder.tvStockPromo = convertView.findViewById(R.id.tvStockPromo);
            holder.btnEditarPromo = convertView.findViewById(R.id.btnEditarPromo);
            holder.btnEliminarPromo = convertView.findViewById(R.id.btnEliminarPromo);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        PromocionModel promocion = promociones.get(position);

        holder.tvIdPromo.setText(String.valueOf("ID de la promoción:"+promocion.getId()));
        holder.tvNombrePromo.setText("Nombre de la promoción:"+promocion.getNombre());
        holder.tvDescripcionPromo.setText("Descripción de la promoción:"+promocion.getDescripcion());
        holder.tvPrecioPromo.setText("Precio de la promoción:"+promocion.getPrecio());
        holder.tvStockPromo.setText("Stock de la promoción:"+promocion.getStock());

        holder.btnEditarPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editClickListener != null) {
                    editClickListener.onEditClick(position);
                }
            }
        });

        holder.btnEliminarPromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteClickListener != null) {
                    deleteClickListener.onDeleteClick(position);
                }
            }
        });
        return convertView;
    }


    static class ViewHolder {
        TextView tvIdPromo;
        TextView tvNombrePromo;
        TextView tvDescripcionPromo;
        TextView tvPrecioPromo;
        TextView tvStockPromo;
        Button btnEditarPromo;
        Button btnEliminarPromo;
    }
}
