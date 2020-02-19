package com.example.findingbook;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.findingbook.dataLayer.DataLayerAccess;
import com.example.findingbook.dataLayer.OurAddress;

public class InputNewAddress extends AppCompatActivity {

    public static String capitalizeEachWord(String str) {
        if (str == null || str.isEmpty())
            return "";
        String words[] = str.split("\\s");
        String capitalizedWords="";
        for (String w : words) {
            String first = w.substring(0, 1);
            String afterfirst = w.substring(1);
            capitalizedWords += first.toUpperCase() + afterfirst + " ";
        }
        return capitalizedWords.trim();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_new_address);

        if(getIntent().getExtras() != null) {
            if(getIntent().getStringExtra("ADDRESS_1") != null) {
                String address1 = getIntent().getStringExtra("ADDRESS_1");
                EditText editText = (EditText) findViewById(R.id.editTextAddress1);
                editText.setText(address1, TextView.BufferType.EDITABLE);
            }
            if(getIntent().getStringExtra("CITY") != null) {
                String city = getIntent().getStringExtra("CITY");
                EditText editText = (EditText) findViewById(R.id.editTextCity);
                editText.setText(city, TextView.BufferType.EDITABLE);
            }
            if(getIntent().getStringExtra("STATE") != null) {
                String state = getIntent().getStringExtra("STATE");
                EditText editText = (EditText) findViewById(R.id.editTextState);
                editText.setText(state, TextView.BufferType.EDITABLE);
            }
            if(getIntent().getStringExtra("POSTAL_CODE") != null) {
                String zipCode = getIntent().getStringExtra("POSTAL_CODE");
                EditText editText = (EditText) findViewById(R.id.editTextZip);
                editText.setText(zipCode, TextView.BufferType.EDITABLE);
            }
        }


    }

    public void onCancelButton(View button) {
        // Do something in response to button
        this.finish();
    }

    public void onSaveButton(View button) {
        // Do something in response to button
        EditText editTextAddress1 = (EditText) findViewById(R.id.editTextAddress1);
        EditText editTextAddress2 = (EditText) findViewById(R.id.editTextAddress2);
        EditText editTextCity = (EditText) findViewById(R.id.editTextCity);
        EditText editTextState = (EditText) findViewById(R.id.editTextState);
        EditText editTextZip = (EditText) findViewById(R.id.editTextZip);

        OurAddress newAddress = new OurAddress();

        newAddress.setAddress1(capitalizeEachWord(editTextAddress1.getText().toString()));
        if (newAddress.getAddress1().isEmpty())
        {
            Snackbar.make(button, "Please enter an address", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return;
        }
        newAddress.setAddress2(capitalizeEachWord(editTextAddress2.getText().toString()));
        newAddress.setCity(capitalizeEachWord(editTextCity.getText().toString()));
        newAddress.setState(editTextState.getText().toString().toUpperCase());

        try {
            newAddress.setZipcode(Integer.parseInt(editTextZip.getText().toString()));
        }
        catch (NumberFormatException nfe) {
            Snackbar.make(button, "Invalid Zipcode", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            return;
        }

        DataLayerAccess dbAccess = new DataLayerAccess(this);
        dbAccess.open();
        dbAccess.createAddress(newAddress);

        this.finish();
    }


}
