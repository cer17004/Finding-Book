package com.example.findingbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.findingbook.dataLayer.DataLayerAccess;
import com.example.findingbook.dataLayer.Person;

import java.util.ArrayList;
import java.util.List;

import static com.example.findingbook.filterViews.citiesView.CitiesView.SINGLE_ADDRESS_ID;
import static com.example.findingbook.filterViews.citiesView.CitiesView.SINGLE_ADDRESS_NAME;
import static com.example.findingbook.filterViews.citiesView.CitiesView.SINGLE_APARTMENT_NUMBER;

public class SingleAddressView extends AppCompatActivity {

    private static final String TAG = "SingleAddressView";

    RecyclerView singleAddressRV;
    SingleAddressRVAdapter singleAddressAdapter;
    List<Person> peopleList;
    List<Person> filteredPeopleList;
    int addressID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_address);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent previousIntent = getIntent();
        addressID = previousIntent.getIntExtra(SINGLE_ADDRESS_ID, -1);

        if (previousIntent.getStringExtra(SINGLE_APARTMENT_NUMBER) == null) {
            setTitle(previousIntent.getStringExtra(SINGLE_ADDRESS_NAME));
        } else {
            setTitle(previousIntent.getStringExtra(SINGLE_APARTMENT_NUMBER));
        }

        peopleList = new ArrayList<>();
        filteredPeopleList = new ArrayList<>();
        singleAddressRV = (RecyclerView) findViewById(R.id.rvSingleAddress);

        DataLayerAccess dbAccess = new DataLayerAccess(this);
        dbAccess.open();

        peopleList = dbAccess.getPeople(addressID);

        for (int i = 0; i < peopleList.size(); i++) {
            if (peopleList.get(i).getAddress_id() == addressID) {
                filteredPeopleList.add(peopleList.get(i));
            }
        }
//        Log.e(TAG, "onCreate22: " + peopleList.toString() );

        singleAddressAdapter = new SingleAddressRVAdapter(this, peopleList );
        singleAddressRV.setLayoutManager(new LinearLayoutManager(this));
        singleAddressRV.setAdapter(singleAddressAdapter);

        Intent intentNewPerson = new Intent(this, InputNewPerson.class);
        intentNewPerson.putExtra("addressID", addressID);
        Log.e(TAG, "onCreate: addressID of this address is :" + addressID);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intentNewPerson);
            }
        });
    }
}
