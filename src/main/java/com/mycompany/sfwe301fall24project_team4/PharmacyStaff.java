/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;

/**
 *
 */
public class PharmacyStaff extends User {
    private String role;
    
    public PharmacyStaff(){
        
    }
    
    public PharmacyStaff(String name, int ID, String role){
        this.name = name;
        this.ID = ID;
        this.role = role;
    }
    
    public void setRole(String role){
        this.role = role;
    }
    
    public String getRole(){
        return role;
    }
}
