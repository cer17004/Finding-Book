package com.example.findingbook.filterViews.streetsView;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.findingbook.InputNewAddress;
import com.example.findingbook.R;
import com.example.findingbook.dataLayer.DataLayerAccess;
import com.example.findingbook.dataLayer.OurAddress;
import com.example.findingbook.filterViews.addressesView.AddressesView;

import java.util.ArrayList;
import java.util.List;

import static com.example.findingbook.filterViews.citiesView.CitiesView.CITY_NAME;
import static com.example.findingbook.filterViews.citiesView.CitiesView.STREET_NAME;

public class StreetsView extends AppCompatActivity implements StreetsRVAdapter.ItemClickListener {

    private static final String TAG = "StreetsView";

    RecyclerView streetsRV;
    StreetsRVAdapter streetsAdapter;
    List<OurAddress> streetsList;
    List<String> stringStreetsList;
    List<String> filteredStringStreetsList;

    private String cityString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_streets_view);

        Intent intent = getIntent();
        cityString = intent.getStringExtra(CITY_NAME);

        setTitle(cityString);

        FloatingActionButton fab = findViewById(R.id.streets_fab);
        Intent intentNewAddress = new Intent(this, InputNewAddress.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                startActivity(intentNewAddress);
            }
        });
        stringStreetsList = new ArrayList<String>();

        DataLayerAccess dbAccess = new DataLayerAccess(this);
        dbAccess.open();
        streetsList = dbAccess.getAddresses(cityString);

        for (int i = 0; i < streetsList.size(); i++) {
            String address1 = streetsList.get(i).getAddress1();
            String[] splitAddress1 = address1.split(" ");
            stringStreetsList.add(address1.substring(address1.indexOf(splitAddress1[0]) + splitAddress1[0].length() + 1));
        }

        filteredStringStreetsList = new ArrayList<String>();

        for (int i = 0; i < stringStreetsList.size(); i++) {
            if (!(filteredStringStreetsList.contains(stringStreetsList.get(i)))) {
                filteredStringStreetsList.add(stringStreetsList.get(i));
            }
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvStreets);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        streetsAdapter = new StreetsRVAdapter(this, filteredStringStreetsList);
        streetsAdapter.setClickListener(this);
        recyclerView.setAdapter(streetsAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + streetsAdapter.getItem(position) + " on row number " + position, Toast.LENGTH_LONG).show();

        // Open AddressesView activity and pass CITY and STREET
        Intent intent = new Intent(this, AddressesView.class);

        intent.putExtra(CITY_NAME, cityString);
        intent.putExtra(STREET_NAME, streetsAdapter.getItem(position));

        startActivity(intent);

    }
}
