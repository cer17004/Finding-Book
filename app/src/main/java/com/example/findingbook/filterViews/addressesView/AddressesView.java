package com.example.findingbook.filterViews.addressesView;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.findingbook.InputNewAddress;
import com.example.findingbook.R;
import com.example.findingbook.SingleAddressView;
import com.example.findingbook.dataLayer.DataLayerAccess;
import com.example.findingbook.dataLayer.OurAddress;
import com.example.findingbook.filterViews.apartmentsView.ApartmentsView;

import java.util.ArrayList;
import java.util.List;

import static com.example.findingbook.filterViews.citiesView.CitiesView.CITY_NAME;
import static com.example.findingbook.filterViews.citiesView.CitiesView.SINGLE_ADDRESS_ID;
import static com.example.findingbook.filterViews.citiesView.CitiesView.SINGLE_ADDRESS_NAME;
import static com.example.findingbook.filterViews.citiesView.CitiesView.STREET_NAME;

public class AddressesView extends AppCompatActivity implements AddressesRVAdapter.ItemClickListener {

    private static final String TAG = "AddressesView";

    RecyclerView addressesRV;
    AddressesRVAdapter addressesAdapter;
    List<OurAddress> addressesList;
    List<String> stringStreetsList;
    List<OurAddress> filteredAddressesList;

    private String cityString;
    private String streetString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresses_view);

        Intent intent = getIntent();
        cityString = intent.getStringExtra(CITY_NAME);
        streetString = intent.getStringExtra(STREET_NAME);

        setTitle(streetString);
        filteredAddressesList = new ArrayList<OurAddress>();

        FloatingActionButton fab = findViewById(R.id.addresses_fab);
        Intent intentNewAddress = new Intent(this, InputNewAddress.class);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { startActivity(intentNewAddress); }
        });

        DataLayerAccess dbAccess = new DataLayerAccess(this);
        dbAccess.open();
        addressesList = dbAccess.getAddresses(cityString);

        stringStreetsList = new ArrayList<String>();

        for (int i = 0; i < addressesList.size(); i++) {
            String address1 = addressesList.get(i).getAddress1();
            String[] splitAddress1 = address1.split(" ");
            stringStreetsList.add(address1.substring(address1.indexOf(splitAddress1[0])
                        + splitAddress1[0].length() + 1));
        }

        for (int i = 0; i < addressesList.size(); i++) {
//            if (stringStreetsList.get(i).equalsIgnoreCase(streetString) && addressesList.get(i).getAddress2() != null) {
//                if (!(filteredAddressesList.contains(addressesList.get(i)))) {
//                    filteredAddressesList.add(addressesList.get(i));
//                }
//            }
            if (stringStreetsList.get(i).equalsIgnoreCase(streetString)) {
                filteredAddressesList.add(addressesList.get(i));
            }
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvAddresses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addressesAdapter = new AddressesRVAdapter(this, filteredAddressesList);
        addressesAdapter.setClickListener(this);
        recyclerView.setAdapter(addressesAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " +
                addressesAdapter.getItem(position).getAddress1() + " on row number " + position,
                Toast.LENGTH_SHORT).show();

        // Open AddressesView activity and pass CITY and STREET
        if (addressesAdapter.getItem(position).getAddress2() == null ||
                addressesAdapter.getItem(position).getAddress2().isEmpty()) {
            Intent intentSingleAddress = new Intent(this, SingleAddressView.class);
            intentSingleAddress.putExtra(CITY_NAME, cityString);
            intentSingleAddress.putExtra(SINGLE_ADDRESS_NAME, addressesAdapter.getItem(position).getAddress1());
            intentSingleAddress.putExtra(SINGLE_ADDRESS_ID, addressesAdapter.getItem(position).getAddress_id());
            startActivity(intentSingleAddress);
        }
        else {
            Intent intentApartmentView = new Intent(this, ApartmentsView.class);
            intentApartmentView.putExtra(CITY_NAME, cityString);
            intentApartmentView.putExtra(STREET_NAME, streetString);
            intentApartmentView.putExtra(SINGLE_ADDRESS_NAME, addressesAdapter.getItem(position).getAddress1());
            startActivity(intentApartmentView);
        }
    }

}
