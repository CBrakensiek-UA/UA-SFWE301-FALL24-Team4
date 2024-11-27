/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;
import java.time.LocalDate;

/**
 *
 */
public class Medication extends InventoryItem{
    private int batchNumber;
    private int lotNumber;
    
    public Medication(){
        
    }
    
    public Medication(String name, int ID, int quantity, int cost, LocalDate expirationDate, int batchNumber, int lotNumber){
        super(name, ID, quantity, cost, expirationDate);
        this.batchNumber = batchNumber;
        this.lotNumber = lotNumber;
    }
    
}
