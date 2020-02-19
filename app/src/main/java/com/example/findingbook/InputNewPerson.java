package com.example.findingbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import com.example.findingbook.dataLayer.DataLayerAccess;
import com.example.findingbook.dataLayer.Person;

public class InputNewPerson extends AppCompatActivity {

    private static final String TAG = "InputNewPerson";
    private int addressID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_new_person);

        Intent intent = getIntent();
        addressID = intent.getIntExtra("addressID", -1);
    }

    public void onCancelButton(View button) {
        // Do something in response to button
        this.finish();
    }

    public void onSaveButton(View button) {
        // Do something in response to button

        EditText editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        EditText editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        EditText editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        EditText editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        EditText editTextFacebook = (EditText) findViewById(R.id.editTextFacebook);

        RadioButton member, personBeingTaught, personNotBeingTaught, personWithInterest,
        softAndClosed, hardAndClosed, uncontacted;

        member = (RadioButton) findViewById(R.id.RBMember);
        personBeingTaught = (RadioButton) findViewById(R.id.RBInvestigator);
        personNotBeingTaught = (RadioButton) findViewById(R.id.RBNotBeingTaught);
        personWithInterest = (RadioButton) findViewById(R.id.RBHasInterest);
        softAndClosed = (RadioButton) findViewById(R.id.RBSoftClosed);
        hardAndClosed = (RadioButton) findViewById(R.id.RBHardClosed);
        uncontacted = (RadioButton) findViewById(R.id.RBUncontacted);

        int status = -1;
        if (member.isChecked())
            status = 0;
        else if (personBeingTaught.isChecked())
            status = 1;
        else if (personNotBeingTaught.isChecked())
            status = 2;
        else if (personWithInterest.isChecked())
            status = 3;
        else if (softAndClosed.isChecked())
            status = 4;
        else if (hardAndClosed.isChecked())
            status = 5;
        else if (uncontacted.isChecked())
            status = 6;

        Switch addedToAreaBook = (Switch) findViewById(R.id.switchAddedToAreaBook);

        Person newPerson = new Person(addressID);

        Log.e(TAG, "onSaveButton: Address ID being saved is: " + addressID);
        newPerson.setAddress_id(addressID);
        newPerson.setFirst_name(editTextFirstName.getText().toString());
        newPerson.setLast_name(editTextLastName.getText().toString());
        newPerson.setPhone_number(editTextPhoneNumber.getText().toString());
        newPerson.setEmail(editTextEmail.getText().toString());
        newPerson.setFacebook(editTextFacebook.getText().toString());
        newPerson.setMoved_to_areabook(addedToAreaBook.isChecked());
        newPerson.setStatus(status);

        DataLayerAccess dbAccess = new DataLayerAccess(this);
        dbAccess.open();

        Log.e(TAG, "onSaveButton: Creating new person at addressID: " + addressID);
        dbAccess.createPerson(newPerson);
        
        this.finish();
    }

}
