package com.example.carpoolapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {

    private List<String> driverList;

    public DriverAdapter(List<String> driverList) {
        this.driverList = driverList;
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item layout for each driver
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_item, parent, false);
        return new DriverViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        // Bind the data to the TextView
        holder.driverName.setText(driverList.get(position));

        // Set up the button click listener
        holder.btnAcceptRide.setOnClickListener(v -> {
            String driverName = driverList.get(position);
            Toast.makeText(v.getContext(), "You accepted " + driverName, Toast.LENGTH_SHORT).show();
            // Add logic for accepting the ride here
        });
    }

    @Override
    public int getItemCount() {
        return driverList.size();
    }

    public static class DriverViewHolder extends RecyclerView.ViewHolder {
        TextView driverName;
        Button btnAcceptRide;

        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the TextView and Button from the layout
            driverName = itemView.findViewById(R.id.driverName);
            btnAcceptRide = itemView.findViewById(R.id.btnAcceptRide);
        }
    }
}
