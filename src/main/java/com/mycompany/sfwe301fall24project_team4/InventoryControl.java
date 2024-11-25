/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;
import java.util.ArrayList;

/**
 *
 */
public class InventoryControl {
    public static ArrayList<InventoryItem> inventory = new ArrayList<>();
    
    public static void addItem(InventoryItem item){
        inventory.add(item);
    }
    
    public static void automaticChecks(){
        System.out.println("Perfoming automatic inventory checks...");
        
        // Check 1: anything expired?
        if(SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacy Manager")){ // Only pharmacy manager gets notification
            for(InventoryItem item : InventoryControl.inventory){
                if(item.isExpired()){
                    System.out.println(item + " has expired.");
                }
            }
        }
        
        // Check 2: anything expiring within 30 days?
        if(SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacy Manager")){ // Only pharmacy manager gets notification
            for(InventoryItem item : InventoryControl.inventory){
                if(item.isExpiredWithin30Days()){
                    System.out.println(item + " will expire within 30 days.");
                }
            }
        }
        
        // Check 3: automatic reordering, stock level alerts
        
        System.out.println("Automatic inventory checks completed.\n");
    }
    
}
