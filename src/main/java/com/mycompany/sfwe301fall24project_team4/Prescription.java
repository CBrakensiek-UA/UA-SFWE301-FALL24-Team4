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
    private int patientID;
    private int medicationID;
    private int amount;
    
    public Prescription(){
        
    }
    
    public Prescription(int ID, int patientID, int medicationID, int amount){
        this.ID = ID;
        this.patientID = patientID;
        this.medicationID = medicationID;
        this.amount = amount;
    }
    
}
