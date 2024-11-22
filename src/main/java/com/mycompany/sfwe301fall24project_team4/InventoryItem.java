/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;
import java.time.LocalDate;

/**
 *
 */
public class InventoryItem {
    private String name;
    private int ID;
    private int quantity;
    private int cost;
    private LocalDate expirationDate;
    
    
    public InventoryItem(){
        
    }
    
    public InventoryItem(String name, int ID, int quanitity, int cost, LocalDate expirationDate){
        this.name = name;
        this.ID = ID;
        this.quantity = quantity;
        this.cost = cost;
        this.expirationDate = expirationDate;
    }
}
