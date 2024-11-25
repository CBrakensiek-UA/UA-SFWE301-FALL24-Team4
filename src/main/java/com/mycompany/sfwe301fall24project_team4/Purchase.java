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
    // Should we have two kinds of purchases, maybe in different classes?
    // (1) A purchase the patient makes from the pharmacy, (2) A purchase the pharmacy makes from a supplier
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
