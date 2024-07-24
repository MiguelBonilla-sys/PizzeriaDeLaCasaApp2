package com.example.apppizzeria2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apppizzeria2.DAOs.BebidasDAO;
import com.example.apppizzeria2.Models.BebidasModel;
import com.example.apppizzeria2.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class BebidaMenuListAdapter extends BaseAdapter{

    private Context context;
    private List<BebidasModel> bebidas;
    private LayoutInflater inflater;
    private BebidasDAO bebidaDAO;

    public BebidaMenuListAdapter(Context context, List<BebidasModel> bebidas) {
        this.context = context;
        this.bebidas = bebidas;
        inflater = LayoutInflater.from(context);
        bebidaDAO = new BebidasDAO(context);
    }

    @Override
    public int getCount() {
        return bebidas.size();
    }

    @Override
    public Object getItem(int position) {
        return bebidas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_bebidamenu, parent, false);
            holder = new ViewHolder();
            holder.etnombre_bebida = convertView.findViewById(R.id.nombre_bebida);
            holder.etdescripcion_bebida = convertView.findViewById(R.id.descripcion_bebida);
            holder.etprecio_bebida = convertView.findViewById(R.id.precio_bebida);
            holder.btAgregarBebida = convertView.findViewById(R.id.buttonAgregarBebida);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BebidasModel bebida = bebidas.get(position);
        holder.etnombre_bebida.setText(bebida.getNombre());
        holder.etdescripcion_bebida.setText(bebida.getDescripcion());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        String precio = format.format(bebida.getPrecio());
        holder.etprecio_bebida.setText(precio);

        holder.btAgregarBebida.setOnClickListener(v -> {
            if (bebida.getStock() > 0) {
                bebida.setStock(bebida.getStock() - 1);
                bebidaDAO.actualizarBebida(bebida.getId(), bebida.getNombre(), bebida.getDescripcion(), bebida.getPrecio(), bebida.getStock());
                Toast.makeText(context, "Bebida agregado al carrito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Stock agotado", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView etnombre_bebida;
        TextView etdescripcion_bebida;
        TextView etprecio_bebida;
        Button btAgregarBebida;
    }

}
