package com.example.findingbook.filterViews.citiesView;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.findingbook.dataLayer.DataLayerAccess;
import com.example.findingbook.InputNewAddress;
import com.example.findingbook.R;
import com.example.findingbook.filterViews.streetsView.StreetsView;

import java.util.List;

public class CitiesView extends AppCompatActivity implements CitiesRVAdapter.ItemClickListener {

    RecyclerView citiesRV;
    CitiesRVAdapter citiesAdapter;
    List<String> citiesList;

    public static final String CITY_NAME = "com.example.findingbook.CITY_NAME";
    public static final String STREET_NAME = "com.example.findingbook.STREET_NAME";
    public static final String SINGLE_ADDRESS_NAME = "com.example.findingbook.SINGLE_ADDRESS_NAME";
    public static final String SINGLE_APARTMENT_NUMBER = "com.example.findingbook.SINGLE_APARTMENT_NUMBER";
    public static final String SINGLE_ADDRESS_ID = "com.example.findingbook.SINGLE_ADDRESS_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities_view);

        FloatingActionButton fab = findViewById(R.id.cities_fab);
        Intent intentNewAddress = new Intent(this, InputNewAddress.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                startActivity(intentNewAddress);
            }
        });

        DataLayerAccess dbAccess = new DataLayerAccess(this);
        dbAccess.open();
        citiesList = dbAccess.getCities();

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvCities);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        citiesAdapter = new CitiesRVAdapter(this, citiesList);
        citiesAdapter.setClickListener(this);
        recyclerView.setAdapter(citiesAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + citiesAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();

        // Open StreetsView activity and pass city
        Intent intent = new Intent(this, StreetsView.class);
        intent.putExtra(CITY_NAME, citiesAdapter.getItem(position));
        startActivity(intent);
    }
}
