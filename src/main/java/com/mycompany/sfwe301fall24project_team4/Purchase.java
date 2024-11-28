package com.mycompany.sfwe301fall24project_team4;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 */
public class Purchase {
    // Represents a purchase order from a supplier
    private static int purchaseCounter = 1000; // Starting ID for purchases
    private final int purchaseID;
    private final ArrayList<Integer> items; // InventoryItem ID's
    private final ArrayList<Integer> prescriptions; // Prescription ID's
    private int totalCost;
    private Supplier supplier; // Associate with a supplier
    private LocalDate purchaseDate;

    public Purchase() {
        this.purchaseID = purchaseCounter++;
        this.items = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
        this.totalCost = 0;
        this.purchaseDate = LocalDate.now();
    }

    public int getPurchaseID() {
        return purchaseID;
    }

    public ArrayList<Integer> getItems() {
        return items;
    }

    public ArrayList<Integer> getPrescriptions() {
        return prescriptions;
    }

    public int getTotalCost() {
        return totalCost;
    }

    public void calculateTotalCost() {
        for (Integer itemId : items) {
            InventoryItem item = InventoryControl.getItemFromID(itemId);
            if (item != null) {
                this.totalCost += item.getCost();
            }
        }
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
