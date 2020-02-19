package com.example.findingbook.dataLayer;

import java.util.List;

// @Entity(tableName = "address_table")
public class OurAddress {

    // @PrimaryKey(autoGenerate = true)
    // @ColumnInfo(name = "address_id")
    private int address_id;

    // @ColumnInfo(name = "address1")
    private String address1;
    private String address2;

    // @ColumnInfo(name = "addressCity")
    private String addressCity;

    // @ColumnInfo(name = "addressState")
    private String addressState;

    // @ColumnInfo(name = "addressZipcode")
    private int addressZipcode;

    // @ColumnInfo(name = "latitude")
    private double latitude;

    // @ColumnInfo(name = "longitude")
    private double longitude;

    // @ColumnInfo(name = "people")
    private List<Person> people;

    // @ColumnInfo(name = "contact_attempts")
    private String contactAttempts;

    // Constructors

    // @Ignore
    public OurAddress() {
        this.address_id      = -1;
        this.address1        = "";
        this.address2        = "";
        this.addressCity     = "";
        this.addressState    = "";
        this.addressZipcode  = 0;
        this.latitude        = 0;
        this.longitude       = 0;
        this.people          = null;
        this.contactAttempts = null;
    }

    public OurAddress(int address_id, String address1, String address2, String addressCity, String addressState,
                      int addressZipcode, double latitude, double longitude, List<Person> people, String contactAttempts){
        this.address_id      = address_id;
        this.address1        = address1;
        this.address2        = address2;
        this.addressCity     = addressCity;
        this.addressState    = addressState;
        this.addressZipcode  = addressZipcode;
        this.latitude        = latitude;
        this.longitude       = longitude;
        this.people          = people;
        this.contactAttempts = contactAttempts;
    }

    public OurAddress(String address1,
                      String address2,
                      String addressCity,
                      String addressState,
                      int addressZipcode){
        this.address_id      = -1;
        this.address1        = address1;
        this.address2        = address2;
        this.addressCity     = addressCity;
        this.addressState    = addressState;
        this.addressZipcode  = addressZipcode;
        this.latitude        = 0;
        this.longitude       = 0;
        this.people          = null;
        this.contactAttempts = null;
    }

    // Getters

    public int getAddress_id() { return this.address_id; }

    public double getLatitude() { return this.latitude; }

    public double getLongitude() { return this.longitude; }

    public List<Person> getPeople() { return this.people; }

    public String getContactAttempts() { return this.contactAttempts; }

    public String getCity() { return this.addressCity; }

    public String getState() { return this.addressState; }

    public int getZipcode() { return this.addressZipcode; }

    public String getAddress1() { return this.address1; }

    public String getAddress2() { return this.address2; }

    public String getAddress() {
        return this.address1 + ", " + this.address2 + ", " + this.addressCity + ", " +
                this.addressState + " " + this.addressZipcode;
    }

    // Setters

    public void setAddress_id(int address_id) { this.address_id = address_id; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public void setPeople(List<Person> people) { this.people = people; }

    public void setContactAttempts(String contactAttempts) { this.contactAttempts = contactAttempts; }

    public void setAddress1(String address1) { this.address1 = address1; }

    public void setAddress2(String address2) { this.address2 = address2; }

    public void setCity(String city) { this.addressCity = city; }

    public void setState(String state) { this.addressState = state; }

    public void setZipcode(int zipcode) { this.addressZipcode = zipcode; }

    // Other Methods

    public Boolean isAvailable() { return true; }

    public Boolean isAvailableOnDay() { return true; }

}
