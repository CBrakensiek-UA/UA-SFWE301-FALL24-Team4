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
    private int cost; // Units: cents (not dollars and cents) to avoid floating point weirdness
    private LocalDate expirationDate;

    public InventoryItem() {

    }

    public InventoryItem(String name, int ID, int quantity, int cost, LocalDate expirationDate) {
        this.name = name;
        this.ID = ID;
        this.quantity = quantity;
        this.cost = cost;
        this.expirationDate = expirationDate;
    }

    @Override
    public String toString() {
        return name + " (ID: " + ID + ")";
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    public boolean isExpiredWithin30Days() {
        return !isExpired() && LocalDate.now().isAfter(expirationDate.minusDays(30));
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return ID;
    }
}
