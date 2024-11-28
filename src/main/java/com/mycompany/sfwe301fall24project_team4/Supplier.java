package com.mycompany.sfwe301fall24project_team4;

/**
 *
 */
public class Supplier {
    private String name;
    private int ID;
    private String address;
    private String city;
    private String state;
    private int zipcode;
    private String contactFirstName;
    private String contactLastName;
    private String contactEmail;
    private int contactPhone;

    public Supplier() {

    }

    public Supplier(String name, int ID, String address, String city, String state, int zipcode, String contactFirstName, String contactLastName, String contactEmail, int contactPhone) {
        this.name = name;
        this.ID = ID;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.contactFirstName = contactFirstName;
        this.contactLastName = contactLastName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public int getZipcode() {
        return zipcode;
    }

    public String getContactFirstName() {
        return contactFirstName;
    }

    public String getContactLastName() {
        return contactLastName;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public int getContactPhone() {
        return contactPhone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public void setContactFirstName(String contactFirstName) {
        this.contactFirstName = contactFirstName;
    }

    public void setContactLastName(String contactLastName) {
        this.contactLastName = contactLastName;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public void setContactPhone(int contactPhone) {
        this.contactPhone = contactPhone;
    }
}
