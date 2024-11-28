package com.mycompany.sfwe301fall24project_team4;

import java.time.LocalDate;

/**
 *
 */
public class Medication extends InventoryItem {
    private int batchNumber;
    private int lotNumber;

    public Medication() {

    }

    public Medication(String name, int ID, int quantity, int cost, LocalDate expirationDate, int reorderThreshold, int criticalThreshold, int batchNumber, int lotNumber) {
        super(name, ID, quantity, cost, expirationDate, reorderThreshold, criticalThreshold);
        this.batchNumber = batchNumber;
        this.lotNumber = lotNumber;
    }

    public Medication(String name, int ID, int quantity, int cost, LocalDate expirationDate, int batchNumber, int lotNumber) {
        super(name, ID, quantity, cost, expirationDate, 10, 5); // Default thresholds for Medication
        this.batchNumber = batchNumber;
        this.lotNumber = lotNumber;
    }

    // Getters and Setters for batchNumber and lotNumber

    public int getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(int batchNumber) {
        this.batchNumber = batchNumber;
    }

    public int getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(int lotNumber) {
        this.lotNumber = lotNumber;
    }

    @Override
    public String toString() {
        return super.toString() + " [Batch: " + batchNumber + ", Lot: " + lotNumber + "]";
    }
}
