package com.example.findingbook.filterViews.apartmentsView;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.findingbook.InputNewAddress;
import com.example.findingbook.R;
import com.example.findingbook.SingleAddressView;
import com.example.findingbook.dataLayer.DataLayerAccess;
import com.example.findingbook.dataLayer.OurAddress;

import java.util.ArrayList;
import java.util.List;

import static com.example.findingbook.filterViews.citiesView.CitiesView.CITY_NAME;
import static com.example.findingbook.filterViews.citiesView.CitiesView.SINGLE_ADDRESS_ID;
import static com.example.findingbook.filterViews.citiesView.CitiesView.SINGLE_ADDRESS_NAME;
import static com.example.findingbook.filterViews.citiesView.CitiesView.SINGLE_APARTMENT_NUMBER;
import static com.example.findingbook.filterViews.citiesView.CitiesView.STREET_NAME;

public class ApartmentsView extends AppCompatActivity implements ApartmentsRVAdapter.ItemClickListener {

    private static final String TAG = "ApartmentsView";

    RecyclerView apartmentsRV;
    ApartmentsRVAdapter apartmentsAdapter;
    List<OurAddress> addressesList;
    List<OurAddress> apartmentsList;

    private String cityString;
    private String streetString;
    private String apartmentAddress1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_view);

        Intent intent = getIntent();
        cityString = intent.getStringExtra(CITY_NAME);
        streetString = intent.getStringExtra(STREET_NAME);
        apartmentAddress1 = intent.getStringExtra(SINGLE_ADDRESS_NAME);
        apartmentsList = new ArrayList<OurAddress>();

        setTitle(apartmentAddress1);

        FloatingActionButton fab = findViewById(R.id.apartments_fab);
        Intent intentNewAddress = new Intent(this, InputNewAddress.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(intentNewAddress); }
        });

        DataLayerAccess dbAccess = new DataLayerAccess(this);
        dbAccess.open();
        addressesList = dbAccess.getAddresses(cityString);


        for (int i = 0; i < addressesList.size(); i++) {
            if (addressesList.get(i).getAddress1().equalsIgnoreCase(apartmentAddress1)) {
                apartmentsList.add(addressesList.get(i));
            }
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvApartments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        apartmentsAdapter = new ApartmentsRVAdapter(this, apartmentsList);
        apartmentsAdapter.setClickListener(this);
        recyclerView.setAdapter(apartmentsAdapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intentSingleAddress = new Intent(this, SingleAddressView.class);
        intentSingleAddress.putExtra(SINGLE_ADDRESS_NAME, apartmentAddress1);
        intentSingleAddress.putExtra(SINGLE_APARTMENT_NUMBER, apartmentsAdapter.getItem(position).getAddress2());
        intentSingleAddress.putExtra(SINGLE_ADDRESS_ID, apartmentsAdapter.getItem(position).getAddress_id());
        startActivity(intentSingleAddress);
    }
}
