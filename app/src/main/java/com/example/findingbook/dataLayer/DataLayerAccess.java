package com.example.findingbook.dataLayer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/******************************************************************************************
 * DataLayerAccess Object
 * How to use:
 * > Create an instance of the class and pass the constructor "this" context.
 * > Call DataLayerAccess.open() to open a connection to the database.
 * > Call desired methods to access database.
 * > Call DataLayerAccess.close() to close the connection to the database.
 ******************************************************************************************/
public class DataLayerAccess {

    DBHandler dbHandler;
    private final Context context;
    private SQLiteDatabase db;

    public DataLayerAccess(Context c) {
        this.context = c;
    }

    /******************************************************************************************
     * addTestData() Method
     * > Adds some test data to the database. Useful when you need a db with data in it.
     ******************************************************************************************/
    public void addTestData() {
        this.createAddress(new OurAddress("175 W 5th S", "apt 101", "Rexburg", "Idaho", 83440));
        this.createAddress(new OurAddress("151 W 4th S", "", "Rexburg", "Idaho", 83440));
        this.createAddress(new OurAddress("65 S 1st W", "", "Rexburg", "Idaho", 83440));
        this.createAddress(new OurAddress("234 testing lane", "", "Blackfoot", "Idaho", 83221));
        this.createAddress(new OurAddress("235 testing lane", "1", "Blackfoot", "Idaho", 83221));
        this.createAddress(new OurAddress("345 testing lane", "", "Idaho Falls", "Idaho", 83401));
        this.createAddress(new OurAddress("346 testing lane", "1", "Idaho Falls", "Idaho", 83401));
    }

    /******************************************************************************************
     * open() Method
     * > Opens a connection to the database.
     ******************************************************************************************/
    public DataLayerAccess open() {
        dbHandler = new DBHandler(context);
        db = dbHandler.getWritableDatabase();
        return this;
    }

    /******************************************************************************************
     * close() Method
     * > Closes the connection to the database.
     ******************************************************************************************/
    public void close() {
        dbHandler.close();

    }

    /******************************************************************************************
     * createAddress() Method
     * > Creates a new entry in the database for an address object.
     ******************************************************************************************/
    public long createAddress(OurAddress address) {
        ContentValues cv = new ContentValues();

        cv.put(DBHandler.ADRS_COLUMN_ADRS_ONE, address.getAddress1());
        cv.put(DBHandler.ADRS_COLUMN_ADRS_TWO, address.getAddress2());
        cv.put(DBHandler.ADRS_COLUMN_CITY, address.getCity());
        cv.put(DBHandler.ADRS_COLUMN_STATE, address.getState());
        cv.put(DBHandler.ADRS_COLUMN_ZIPCODE, address.getZipcode());
        cv.put(DBHandler.ADRS_COLUMN_LATITUDE, address.getLatitude());
        cv.put(DBHandler.ADRS_COLUMN_LONGITUDE, address.getLongitude());

        return db.insert(DBHandler.TABLE_ADDRESSES, null, cv);
    }

    /******************************************************************************************
     * createPerson() Method
     * > Creates a new entry in the database for a person object.
     ******************************************************************************************/
    public long createPerson(Person person) {
        ContentValues cv = new ContentValues();

        cv.put(DBHandler.PRSN_COLUMN_FIRST_NAME, person.getFirstName());
        cv.put(DBHandler.PRSN_COLUMN_LAST_NAME, person.getLastName());
        cv.put(DBHandler.PRSN_COLUMN_PHONE_NUMBER, person.getPhone_number());
        cv.put(DBHandler.PRSN_COLUMN_FACEBOOK, person.getFacebook());
        cv.put(DBHandler.PRSN_COLUMN_EMAIL, person.getEmail());
        cv.put(DBHandler.PRSN_COLUMN_MOVED_TO_AREABOOK, person.getMoved_to_areabook());
        cv.put(DBHandler.PRSN_COLUMN_STATUS, person.getStatus());

        return db.insert(DBHandler.TABLE_PERSONS, null, cv);
    }

    /******************************************************************************************
     * getAddresses() Method
     * > Returns all the addresses in the database.
     ******************************************************************************************/
    public List<OurAddress> getAddresses() {

        String[] columns = new String[]{
                DBHandler.ADRS_COLUMN_ID,
                DBHandler.ADRS_COLUMN_ADRS_ONE,
                DBHandler.ADRS_COLUMN_ADRS_TWO,
                DBHandler.ADRS_COLUMN_CITY,
                DBHandler.ADRS_COLUMN_STATE,
                DBHandler.ADRS_COLUMN_ZIPCODE,
                DBHandler.ADRS_COLUMN_LATITUDE,
                DBHandler.ADRS_COLUMN_LONGITUDE
        };

        Cursor c = db.query(DBHandler.TABLE_ADDRESSES, columns, null, null,
                null, null, null);

        List<OurAddress> addresses = new ArrayList<OurAddress>();
        OurAddress tmpAddress = new OurAddress();

        int iAddressId = c.getColumnIndex(DBHandler.ADRS_COLUMN_ID);
        int iAddressOne = c.getColumnIndex(DBHandler.ADRS_COLUMN_ADRS_ONE);
        int iAddressTwo = c.getColumnIndex(DBHandler.ADRS_COLUMN_ADRS_TWO);
        int iCity = c.getColumnIndex(DBHandler.ADRS_COLUMN_CITY);
        int iState = c.getColumnIndex(DBHandler.ADRS_COLUMN_STATE);
        int iZipcode = c.getColumnIndex(DBHandler.ADRS_COLUMN_ZIPCODE);
        int iLatitude = c.getColumnIndex(DBHandler.ADRS_COLUMN_LATITUDE);
        int iLongitude = c.getColumnIndex(DBHandler.ADRS_COLUMN_LONGITUDE);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            addresses.add(new OurAddress(
                    c.getInt(iAddressId),
                    c.getString(iAddressOne),
                    c.getString(iAddressTwo),
                    c.getString(iCity),
                    c.getString(iState),
                    c.getInt(iZipcode),
                    c.getDouble(iLatitude),
                    c.getDouble(iLongitude),
                    null,
                    null
            ));
        }

        return addresses;
    }

    /******************************************************************************************
     * getCities() Method
     * > Returns all the cities in the database.
     ******************************************************************************************/
    public List<String> getCities() {
        List<String> cities = new ArrayList<>();
        List<OurAddress> addresses = getAddresses();

        for (int i = 0; i < addresses.size(); i++) {
            if (!(cities.contains(addresses.get(i).getCity()))) {
                cities.add(addresses.get(i).getCity());
            }
        }

        return cities;
    }

    /******************************************************************************************
     * getAddress() Method
     * > Returns a specific address from the database.
     ******************************************************************************************/
    public OurAddress getAddress(String qryKey, String qryValue) {
        String[] columns = new String[]{
                DBHandler.ADRS_COLUMN_ID,
                DBHandler.ADRS_COLUMN_ADRS_ONE,
                DBHandler.ADRS_COLUMN_ADRS_TWO,
                DBHandler.ADRS_COLUMN_CITY,
                DBHandler.ADRS_COLUMN_STATE,
                DBHandler.ADRS_COLUMN_ZIPCODE,
                DBHandler.ADRS_COLUMN_LATITUDE,
                DBHandler.ADRS_COLUMN_LONGITUDE
        };

        Cursor c = db.query(DBHandler.TABLE_ADDRESSES,
                columns,
                qryKey + " = \"" + qryValue + "\"",
                null,
                null,
                null,
                null
        );

        if (c != null) {
            c.moveToFirst();

            OurAddress address = new OurAddress(
                    c.getInt(c.getColumnIndex(DBHandler.ADRS_COLUMN_ID)),
                    c.getString(c.getColumnIndex(DBHandler.ADRS_COLUMN_ADRS_ONE)),
                    c.getString(c.getColumnIndex(DBHandler.ADRS_COLUMN_ADRS_TWO)),
                    c.getString(c.getColumnIndex(DBHandler.ADRS_COLUMN_CITY)),
                    c.getString(c.getColumnIndex(DBHandler.ADRS_COLUMN_STATE)),
                    c.getInt(c.getColumnIndex(DBHandler.ADRS_COLUMN_ZIPCODE)),
                    c.getDouble(c.getColumnIndex(DBHandler.ADRS_COLUMN_LATITUDE)),
                    c.getDouble(c.getColumnIndex(DBHandler.ADRS_COLUMN_LONGITUDE)),
                    null,
                    null
            );

            return address;
        }

        return null;
    }

    /******************************************************************************************
     * getAddresses() Method
     * > Returns all the addresses in the database.
     * > @param: cityFilter, the city the addresses will be from.
     ******************************************************************************************/
    public List<OurAddress> getAddresses(String qryKey, String qryValue) {

        String[] columns = new String[]{
                DBHandler.ADRS_COLUMN_ID,
                DBHandler.ADRS_COLUMN_ADRS_ONE,
                DBHandler.ADRS_COLUMN_ADRS_TWO,
                DBHandler.ADRS_COLUMN_CITY,
                DBHandler.ADRS_COLUMN_STATE,
                DBHandler.ADRS_COLUMN_ZIPCODE,
                DBHandler.ADRS_COLUMN_LATITUDE,
                DBHandler.ADRS_COLUMN_LONGITUDE
        };

        Cursor c = db.query(
                DBHandler.TABLE_ADDRESSES,
                columns,
                qryKey + " = \"" + qryValue + "\"",
                null,
                null,
                null,
                null
        );

        List<OurAddress> addresses = new ArrayList<OurAddress>();
        OurAddress tmpAddress = new OurAddress();

        int iAddressId = c.getColumnIndex(DBHandler.ADRS_COLUMN_ID);
        int iAddressOne = c.getColumnIndex(DBHandler.ADRS_COLUMN_ADRS_ONE);
        int iAddressTwo = c.getColumnIndex(DBHandler.ADRS_COLUMN_ADRS_TWO);
        int iCity = c.getColumnIndex(DBHandler.ADRS_COLUMN_CITY);
        int iState = c.getColumnIndex(DBHandler.ADRS_COLUMN_STATE);
        int iZipcode = c.getColumnIndex(DBHandler.ADRS_COLUMN_ZIPCODE);
        int iLatitude = c.getColumnIndex(DBHandler.ADRS_COLUMN_LATITUDE);
        int iLongitude = c.getColumnIndex(DBHandler.ADRS_COLUMN_LONGITUDE);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            addresses.add(new OurAddress(
                    c.getInt(iAddressId),
                    c.getString(iAddressOne),
                    c.getString(iAddressTwo),
                    c.getString(iCity),
                    c.getString(iState),
                    c.getInt(iZipcode),
                    c.getDouble(iLatitude),
                    c.getDouble(iLongitude),
                    null,
                    null
            ));
        }

        return addresses;
    }

    /******************************************************************************************
     * getAddresses() Method
     * > Returns all the addresses in the database.
     * > @param: cityFilter, the city the addresses will be from.
     ******************************************************************************************/
    public List<OurAddress> getAddresses(String cityFilter) {

        return this.getAddresses(DBHandler.ADRS_COLUMN_CITY, cityFilter);
    }

    /******************************************************************************************
     * getPeople() Method
     * > Returns a list of person objects from the database.
     ******************************************************************************************/
    public List<Person> getPeople(int addressID) {
        return this.getPeople(DBHandler.PRSN_COLUMN_ADDRESS_ID, addressID);
    }

    /******************************************************************************************
     * getPeople() Method
     * > Returns specific persons from the database based on a key-value condition.
     ******************************************************************************************/
    public List<Person> getPeople(String qryKey, int qryValue) {

        String[] columns = new String[]{
                DBHandler.PRSN_COLUMN_ID,
                DBHandler.PRSN_COLUMN_ADDRESS_ID,
                DBHandler.PRSN_COLUMN_FIRST_NAME,
                DBHandler.PRSN_COLUMN_LAST_NAME,
                DBHandler.PRSN_COLUMN_PHONE_NUMBER,
                DBHandler.PRSN_COLUMN_FACEBOOK,
                DBHandler.PRSN_COLUMN_EMAIL,
                DBHandler.PRSN_COLUMN_MOVED_TO_AREABOOK,
                DBHandler.PRSN_COLUMN_STATUS
        };

        Log.e("blah", "yolfe: " + qryKey + qryValue);
        String[] args = new String[] { Integer.toString(qryValue)};
        Cursor c = db.query(DBHandler.TABLE_PERSONS,
                columns,
                null,//qryKey + " = 0",
                null,//args,
                null,
                null,
                null
        );

        List<Person> people = new ArrayList<Person>();

        int iPersonId        = c.getColumnIndex(DBHandler.PRSN_COLUMN_ID);
        int iAddressId       = c.getColumnIndex(DBHandler.PRSN_COLUMN_ADDRESS_ID);
        int iFirstName       = c.getColumnIndex(DBHandler.PRSN_COLUMN_FIRST_NAME);
        int iLastName        = c.getColumnIndex(DBHandler.PRSN_COLUMN_LAST_NAME);
        int iPhone           = c.getColumnIndex(DBHandler.PRSN_COLUMN_PHONE_NUMBER);
        int iFacebook        = c.getColumnIndex(DBHandler.PRSN_COLUMN_FACEBOOK);
        int iEmail           = c.getColumnIndex(DBHandler.PRSN_COLUMN_EMAIL);
        int iMovedToAreabook = c.getColumnIndex(DBHandler.PRSN_COLUMN_MOVED_TO_AREABOOK);
        int iStatus          = c.getColumnIndex(DBHandler.PRSN_COLUMN_STATUS);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Log.e("blah", "yolfe: " + c.getInt(iAddressId));
            people.add(new Person(
                    c.getInt(iPersonId),
                    c.getInt(iAddressId),
                    c.getString(iFirstName),
                    c.getString(iLastName),
                    c.getString(iPhone),
                    c.getString(iFacebook),
                    c.getString(iEmail),
                    c.getInt(iMovedToAreabook) > 0,
                    c.getInt(iStatus)
            ));

        }

        return people;
    }


}
