package com.example.apppizzeria2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.apppizzeria2.Models.BebidasModel;
import com.example.apppizzeria2.R;

import java.util.List;

public class BebidasListAdapter extends BaseAdapter {

    private List<BebidasModel> bebidas;
    private LayoutInflater inflater;
    private OnEditClickListener editClickListener;
    private OnDeleteClickListener deleteClickListener;

    public interface OnEditClickListener {
        void onEditClick(int position);
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }

    public void setOnEditClickListener(OnEditClickListener listener) {
        this.editClickListener = listener;
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.deleteClickListener = listener;
    }

    public BebidasListAdapter(Context context, List<BebidasModel> bebidas) {
        this.bebidas = bebidas;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return bebidas.size();
    }

    @Override
    public BebidasModel getItem(int position) {
        return bebidas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bebidas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_bebidas, null);
            holder = new ViewHolder();
            holder.tvIdBebi = convertView.findViewById(R.id.etIdBebi);
            holder.tvNombreBebi = convertView.findViewById(R.id.etNombreBebi);
            holder.tvDescripcionBebi = convertView.findViewById(R.id.tvDescripcionBebi);
            holder.tvPrecioBebi = convertView.findViewById(R.id.tvPrecioBebi);
            holder.tvStockBebi = convertView.findViewById(R.id.tvStockBebi);
            holder.btnEditarBebi = convertView.findViewById(R.id.btnEditarBebi);
            holder.btnEliminarBebi = convertView.findViewById(R.id.btnEliminarBebi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        BebidasModel bebida = bebidas.get(position);
        holder.tvIdBebi.setText(String.valueOf(bebida.getId()));
        holder.tvNombreBebi.setText(bebida.getNombre());
        holder.tvDescripcionBebi.setText(bebida.getDescripcion());
        holder.tvPrecioBebi.setText(String.valueOf(bebida.getPrecio()));
        holder.tvStockBebi.setText(String.valueOf(bebida.getStock()));
        holder.btnEditarBebi.setOnClickListener(v -> {
            if (editClickListener != null) {
                editClickListener.onEditClick(position);
            }
        });
        holder.btnEliminarBebi.setOnClickListener(v -> {
            if (deleteClickListener != null) {
                deleteClickListener.onDeleteClick(position);
            }
        });
        return convertView;
    }

    static class ViewHolder {
        TextView tvIdBebi;
        TextView tvNombreBebi;
        TextView tvDescripcionBebi;
        TextView tvPrecioBebi;
        TextView tvStockBebi;
        Button btnEditarBebi;
        Button btnEliminarBebi;
    }
}