package com.example.apppizzeria2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.apppizzeria2.R;
import com.example.apppizzeria2.Models.OrderModel;

import java.util.List;

public class HistorialComprasListAdapter extends BaseAdapter {
    private Context context;
    private List<OrderModel> orders;

    public HistorialComprasListAdapter(Context context, List<OrderModel> orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_history, parent, false);
        }

        OrderModel order = orders.get(position);

        TextView tvOrderLocation = convertView.findViewById(R.id.tvOrderLocation);
        TextView tvOrderTotalPrice = convertView.findViewById(R.id.tvOrderTotalPrice);
        TextView tvOrderItems = convertView.findViewById(R.id.tvOrderItems);

        tvOrderLocation.setText(order.getLocation());
        tvOrderTotalPrice.setText(order.getTotalPrice());
        tvOrderItems.setText(order.getItemsAsString());

        return convertView;
    }
}