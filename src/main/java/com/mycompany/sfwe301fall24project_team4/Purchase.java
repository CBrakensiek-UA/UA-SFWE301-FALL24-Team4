/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;
import java.util.ArrayList;

/**
 *
 */
public class Purchase {
    private ArrayList<Integer> items; // InventoryItem ID's
    private ArrayList<Integer> prescriptions; // Prescription ID's
    
    public Purchase(){
        items = new ArrayList<>();
        prescriptions = new ArrayList();
    }
    
    public int getCost(){
        // still need to implement
        return 0;
    }
}
