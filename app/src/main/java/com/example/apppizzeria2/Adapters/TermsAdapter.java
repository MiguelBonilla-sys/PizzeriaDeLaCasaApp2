package com.example.apppizzeria2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class TermsAdapter extends ArrayAdapter<String> {

    public TermsAdapter(Context context, List<String> terms) {
        super(context, 0, terms);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        }
        // Get the data item for this position
        String term = getItem(position);
        // Lookup view for data population
        TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
        // Populate the data into the template view using the data object
        textView.setText(term);
        // Return the completed view to render on screen
        return convertView;
    }
}
