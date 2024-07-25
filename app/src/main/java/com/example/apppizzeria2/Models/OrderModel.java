package com.example.apppizzeria2.Models;

import java.util.List;
import java.util.Map;

public class OrderModel {
    private String location;
    private String totalPrice;
    private List<Map<String, Object>> items;

    public OrderModel() {
        // Default constructor required for calls to DataSnapshot.getValue(Order.class)
    }

    public OrderModel(String location, String totalPrice, List<Map<String, Object>> items) {
        this.location = location;
        this.totalPrice = totalPrice;
        this.items = items;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<Map<String, Object>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }

    public String getItemsAsString() {
        StringBuilder itemsString = new StringBuilder();
        for (Map<String, Object> item : items) {
            itemsString.append(item.get("name")).append(" x").append(item.get("quantity")).append("\n");
        }
        return itemsString.toString();
    }
}