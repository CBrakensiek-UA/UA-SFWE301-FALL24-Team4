/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;

/**
 *
 * @author school
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
    
    public Supplier(){
        
    }
    
    public Supplier(String name, int ID, String address, String city, String state, int zipcode, String contactFirstName, String contactLastName, String contactEmail, int contactPhone){
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
}
