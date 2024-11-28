package com.mycompany.sfwe301fall24project_team4;

/**
 *
 */
public class Prescription {
    private int ID;
    private Patient patient;
    private int medicationID;
    private int amount;
    private boolean isFilled; // To track if the prescription is filled

    public Prescription() {

    }

    public Prescription(int ID, Patient patient, int medicationID, int amount) {
        this.ID = ID;
        this.patient = patient;
        this.medicationID = medicationID;
        this.amount = amount;
        this.isFilled = false;
    }

    public int getID() {
        return ID;
    }

    public Patient getPatient() {
        return patient;
    }

    public int getMedicationID() {
        return medicationID;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setFilled(boolean filled) {
        isFilled = filled;
    }

    /**
     * Processes the prescription by decreasing the inventory.
     * Logs the transaction.
     *
     * @return true if the prescription was successfully filled, false otherwise.
     */
    public boolean fillPrescription() {
        InventoryItem item = InventoryControl.getItemFromID(this.medicationID);
        if (item == null) {
            System.out.println("Medication ID not found in inventory.");
            return false;
        }

        if (item.getQuantity() < this.amount) {
            System.out.println("Insufficient stock to fill the prescription.");
            return false;
        }

        int oldQuantity = item.getQuantity();
        item.setQuantity(oldQuantity - this.amount);
        this.isFilled = true;

        // Log the prescription filling
        ReportGeneration.logPrescriptionFilling(
                SFWE301Fall24Project_Team4.currentUser,
                this,
                oldQuantity,
                item.getQuantity()
        );

        System.out.println("Prescription filled successfully.");
        return true;
    }
}
