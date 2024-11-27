/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;

/**
 *
 */
public class Prescription {
    private int ID;
    private Patient patient;
    private int medicationID;
    private int amount;
    
    public Prescription(){
        
    }
    
    public Prescription(int ID, Patient patient, int medicationID, int amount){
        this.ID = ID;
        this.patient = patient;
        this.medicationID = medicationID;
        this.amount = amount;
    }
    
}
