package com.example.findingbook.dataLayer;

//@Entity(tableName = "person_table")
public class Person {

    // @PrimaryKey(autoGenerate = true)
    // @ColumnInfo(name = "person_id")
    private int person_id;

    // @ColumnInfo(name = "address_id")
    private int address_id;

    // @ColumnInfo(name = "first_name")
    private String first_name;

    // @ColumnInfo(name = "last_name")
    private String last_name;

    // @ColumnInfo(name = "phone_number")
    private String phone_number;

    // @ColumnInfo(name = "facebook")
    private String facebook;

    // @ColumnInfo(name = "email")
    private String email;

    // @ColumnInfo(name = "moved_to_areabook")
    private Boolean moved_to_areabook;

    private int status;

    // Constructors

    public Person(int address_id) { this.address_id = address_id; }

    public Person(int person_id, int address_id, String first_name,
                  String last_name, String phone_number, String facebook, String email,
                  Boolean moved_to_areabook, int status) {
        this.person_id         = person_id;
        this.address_id        = address_id;
        this.first_name        = first_name;
        this.last_name         = last_name;
        this.phone_number      = phone_number;
        this.facebook          = facebook;
        this.email             = email;
        this.moved_to_areabook = moved_to_areabook;
        this.status            = status;
    }

    // Getters

    public int getPerson_id()     { return this.person_id; }

    public int getAddress_id() { return this.address_id; }

    public String getFirstName()  { return this.first_name; }

    public String getLastName()   { return this.last_name; }

    public String getPhone_number()  { return this.phone_number; }

    public String getEmail()      { return this.email; }

    public String getFacebook()      { return this.facebook; }

    public Boolean getMoved_to_areabook()  { return this.moved_to_areabook; }

    public int getStatus()  { return this.status; }

    // Setters

    public void setPerson_id(int person_id) { this.person_id = person_id;   }

    public void setAddress_id(int address_id) { this.address_id = address_id; }

    public void setFirst_name(String first_name) { this.first_name = first_name; }

    public void setLast_name(String last_name) { this.last_name = last_name;   }

    public void setPhone_number(String phone_number) { this.phone_number = phone_number; }

    public void setEmail(String email) { this.email = email; }

    public void setFacebook(String facebook) { this.facebook = facebook; }

    public void setMoved_to_areabook(Boolean moved_to_areabook) { this.moved_to_areabook = moved_to_areabook; }

    public void setStatus(int status) { this.status = status; }

    // Other Methods

    public Boolean isAvailable() { return true; }

    public Boolean isAvailableOnDay(String dayOfTheWeek) { return true; }


}
