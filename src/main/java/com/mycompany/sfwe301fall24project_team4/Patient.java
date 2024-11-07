/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;

/**
 *
 */
public class Patient extends User {
    private String birthDate;
    private String address;
    private String phoneNumber;
    private String email;
    private String insuranceInfo;
    
    public Patient(){
        
    }
    
    public Patient(String name, int ID, String birthDate, String address, String phoneNumber, String email, String insuranceInfo){
        this.name = name;
        this.ID = ID;
        this.birthDate = birthDate;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.insuranceInfo = insuranceInfo;
    }
    
    
    
    public void setBirthDate(String birthDate){
        this.birthDate = birthDate;
    }
    
    public void setAddress(String address){
        this.address = address;
    }
    
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setInsuranceInfo(String insuranceInfo){
        this.insuranceInfo = insuranceInfo;
    }
    
    
    
    public String getBirthDate(){
        return birthDate;
    }
    
    public String getAddress(){
        return address;
    }
    
    public String getPhoneNumber(){
        return phoneNumber;
    }
    
    public String getEmail(){
        return email;
    }
    
    public String getInsuranceInfo(){
        return insuranceInfo;
    }
    
}
