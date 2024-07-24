package com.example.apppizzeria2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apppizzeria2.Models.CarritoSingleton;
import com.example.apppizzeria2.DAOs.ProductoDAO;
import com.example.apppizzeria2.Models.ProductoModel;
import com.example.apppizzeria2.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductoMenuListAdapter extends BaseAdapter {
    private Context context;
    private List<ProductoModel> productos;
    private LayoutInflater inflater;
    private ProductoDAO productoDAO;
    private List<Object> carrito;

    public ProductoMenuListAdapter(Context context, List<ProductoModel> productos, List<Object> carrito) {
        this.context = context;
        this.productos = productos;
        this.inflater = LayoutInflater.from(context);
        this.productoDAO = new ProductoDAO(context);
        this.carrito = carrito;
    }

    @Override
    public int getCount() {
        return productos.size();
    }

    @Override
    public Object getItem(int position) {
        return productos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_productomenu, parent, false);
            holder = new ViewHolder();
            holder.etnombre_producto = convertView.findViewById(R.id.nombre_producto);
            holder.etdescripcion_producto = convertView.findViewById(R.id.descripcion_producto);
            holder.etprecio_producto = convertView.findViewById(R.id.precio_producto);
            holder.btAgregarProducto = convertView.findViewById(R.id.buttonAgregarProducto);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ProductoModel producto = productos.get(position);

        holder.etnombre_producto.setText(producto.getNombre());
        holder.etdescripcion_producto.setText(producto.getDescripcion());
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("es", "CO"));
        String precioFormateado = format.format(producto.getPrecio());
        holder.etprecio_producto.setText(precioFormateado);

        holder.btAgregarProducto.setOnClickListener(v -> {
            if (producto.getStock() > 0) {
                producto.setStock(producto.getStock() - 1);
                productoDAO.actualizarProducto(producto.getId(), producto.getNombre(), producto.getDescripcion(), producto.getPrecio(), producto.getStock());
                CarritoSingleton.getInstance().addItem(producto);
                Toast.makeText(context, "Producto agregado al carrito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Stock agotado", Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    static class ViewHolder {
        TextView etnombre_producto;
        TextView etdescripcion_producto;
        TextView etprecio_producto;
        Button btAgregarProducto;
    }
}