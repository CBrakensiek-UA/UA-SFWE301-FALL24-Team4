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
    private int reorderThreshold; // New attribute
    private int criticalThreshold; // New attribute
    private String location;

    public InventoryItem() {

    }

    public InventoryItem(String name, int ID, int quantity, int cost, LocalDate expirationDate, String location, int reorderThreshold, int criticalThreshold) {
        this.name = name;
        this.ID = ID;
        this.quantity = quantity;
        this.cost = cost;
        this.expirationDate = expirationDate;
        this.location = location;
        this.reorderThreshold = reorderThreshold;
        this.criticalThreshold = criticalThreshold;
    }

    public InventoryItem(String name, int ID, int quantity, int cost, LocalDate expirationDate, String location, int reorderThreshold) {
        this(name, ID, quantity, cost, expirationDate, location, reorderThreshold, 5); // Default critical threshold
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

    public int getReorderThreshold() {
        return reorderThreshold;
    }

    public void setReorderThreshold(int reorderThreshold) {
        this.reorderThreshold = reorderThreshold;
    }

    public int getCriticalThreshold() {
        return criticalThreshold;
    }

    public void setCriticalThreshold(int criticalThreshold) {
        this.criticalThreshold = criticalThreshold;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    public String getLocation(){
        return location;
    }
    
    public void setLocation(String location){
        this.location = location;
    }
}
