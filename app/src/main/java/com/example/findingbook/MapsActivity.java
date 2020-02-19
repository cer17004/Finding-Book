package com.example.findingbook;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.findingbook.dataLayer.DataLayerAccess;
import com.example.findingbook.dataLayer.OurAddress;
import com.example.findingbook.filterViews.citiesView.CitiesView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

//import android.location.LocationListener;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback,
        NavigationView.OnNavigationItemSelectedListener
        ,GoogleMap.OnMarkerClickListener
        ,GoogleApiClient.ConnectionCallbacks
        ,GoogleApiClient.OnConnectionFailedListener
        ,LocationListener {

    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private DrawerLayout drawer;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private Marker lastLocationClicked;
    private List<Marker> MapMarkers;
    private List<OurAddress> addresses;
    private static final int Request_User_Location_Code = 99;
    private static final int POPVIEW_ACTIVITY_REQUEST_CODE = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        //Asks for Permission to access user location
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            checkUserLocationPermission();
        }

        //Creates the Toolbar for the main bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);

        //Adds the menu to the toolbar & implements drawer action
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer != null) {
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                    toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }

        //Sets up the layout for menu
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {

            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }

        //Adds to the interaction with the map
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);

        //Places marker on a map where user held
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng point) {
                MarkerOptions options = new MarkerOptions()
                        .position(point);
                Marker mMarker = mMap.addMarker(options);

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(point.latitude, point.longitude, 1);
                    if (addresses != null && addresses.size() > 0){
                        System.out.println("Address: " + addresses.get(0).toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawer != null) {
            switch (item.getItemId()) {
                case android.R.id.home:
                    drawer.openDrawer(GravityCompat.START);
                    return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        //If an item is selected in the Navigation Bar
        switch(menuItem.getItemId()) {

            //Drops all known addresses on the Map
            case R.id.nav_allPins:

                //Access database stores addresses
                DataLayerAccess dbAccess = new DataLayerAccess(MapsActivity.this);
                dbAccess.open();
                addresses = dbAccess.getAddresses();

                //Finds the coordinates of the address for the map
                Geocoder geocoder = new Geocoder(MapsActivity.this);
                List<Address> addressList = null;


                for (int i = 0; i < addresses.size(); i++) {
                    try {
                        addressList = geocoder.getFromLocationName(addresses.get(i).getAddress(), 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //If location is found placed
                    if (addressList != null && addressList.size() != 0) {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng));
                    }
                }
                dbAccess.close();
                break;

            case R.id.nav_citiesView:
                Intent intent = new Intent(this, CitiesView.class);
                startActivity(intent);
        }
        return true;
    }



    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    public void onMapSearch(View view) {
        //Obtains the address from edit view
        EditText entered_location = (EditText) findViewById(R.id.location_Entered);
        String location = entered_location.getText().toString();
        List<Address> addressList = null;

        //Determines the Address from the input
        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Placed a marker to address found
            if(addressList != null && addressList.size() != 0){
                Address address = addressList.get(0);
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title("Entered Location"));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        }
    }

    //Creates Popup View waits for feeback
    public boolean onMarkerClick(final Marker marker){
        lastLocationClicked = marker;
        Intent intent = new Intent(this, PopupView.class);
        startActivityForResult(intent,POPVIEW_ACTIVITY_REQUEST_CODE);
        return false;
    }

    //Interprets feedback from Popup View
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == POPVIEW_ACTIVITY_REQUEST_CODE){

            //Deleting Marker button selected
            if(resultCode == RESULT_OK){
                lastLocationClicked.remove();
            }

            //Finding the address of the user and switches to new activity
            if(resultCode == RESULT_FIRST_USER){
                LatLng latlng = lastLocationClicked.getPosition();
                List<Address> addresses;
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());

                try {
                    addresses = geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1);
                    if (addresses != null) {
                        Intent intent = new Intent(this, InputNewAddress.class);
                        if (addresses.get(0).getAddressLine(0) != null) {
                            String address = addresses.get(0).getFeatureName() + " " + addresses.get(0).getThoroughfare();
                            intent.putExtra("ADDRESS_1", address);
                        }
                        if (addresses.get(0).getLocality() != null) {
                            String city = addresses.get(0).getLocality();
                            intent.putExtra("CITY", city);
                        }
                        if (addresses.get(0).getAdminArea() != null) {
                            String state = addresses.get(0).getAdminArea();
                            intent.putExtra("STATE", state);
                        }
                        if (addresses.get(0).getPostalCode() != null) {
                            String postalCode = addresses.get(0).getPostalCode();
                            intent.putExtra("POSTAL_CODE", postalCode);
                        }

                        startActivity(intent);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    //All the functions below are to determine the user's location

    protected synchronized void buildGoogleApiClient(){
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();

    }

    public boolean checkUserLocationPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }else{
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return false;
        }else{
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case Request_User_Location_Code:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        if(googleApiClient == null){
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else{
                    System.out.println("Permission Denied");
                }
                return;
        }
    }
    @Override
    public void onLocationChanged(Location location) {

        lastLocation = location;
        if (currentUserLocationMarker != null){
            currentUserLocationMarker.remove();
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Users Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        currentUserLocationMarker = mMap.addMarker(markerOptions);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));

        if(googleApiClient != null){
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }

    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {}
}
