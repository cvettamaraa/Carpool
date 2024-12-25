package com.example.carpoolapp;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DriverListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_list);

        // Find the RecyclerView in the layout
        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Set up the layout manager for the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a DatabaseHelper instance
        DatabaseHelper dbHelper = new DatabaseHelper(this);

        // Fetch all driver details from the database
        List<String> driverList = dbHelper.getAllDriverDetails();

        // Set up the adapter with the driver list
        DriverAdapter adapter = new DriverAdapter(driverList);

        // Attach the adapter to the RecyclerView
        recyclerView.setAdapter(adapter);
    }
}
