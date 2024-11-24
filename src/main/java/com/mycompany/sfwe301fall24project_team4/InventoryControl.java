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
    private static ArrayList<InventoryItem> inventory = new ArrayList<>();
    
    public static void addItem(InventoryItem item){
        inventory.add(item);
    }
}
