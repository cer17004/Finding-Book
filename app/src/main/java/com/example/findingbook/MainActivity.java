package com.example.findingbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.findingbook.filterViews.citiesView.CitiesView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onMapView(View mapButton) {
        Intent mIntent = new Intent(this, MapsActivity.class);
        startActivity(mIntent);
    }

    /**
     * Called when the user taps the Send button
     */
    public void onCityView(View button) {
        // Do something in response to button
        Log.d("MainActivity", "Right before intent");
        Intent intent = new Intent(this, CitiesView.class);
        Log.d("MainActivity", "Right after intent");
        startActivity(intent);
        Log.d("MainActivity", "Right after intent start");
    }
}
