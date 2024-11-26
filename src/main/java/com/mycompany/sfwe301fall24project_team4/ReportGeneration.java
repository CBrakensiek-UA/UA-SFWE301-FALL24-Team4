/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;

import java.time.LocalDate;

/**
 * This class represents the Report Generation Subsystem. This subsystem manages
 * the various logs and prints out reports based on them.
 */
public class ReportGeneration {

    private static Log transactionLog = new Log("TransactionLog.csv");

    public static void logLogin(PharmacyStaff user, LocalDate time) {
        String activity = user.getName() + "," + user.getID() + "," + time + ",logged in";
        transactionLog.readFromFile();
        transactionLog.addContent(activity);
        transactionLog.writeToFile();
    }

    public static void logLogout(PharmacyStaff user, LocalDate time) {
        String activity = user.getName() + "," + user.getID() + "," + time + ",logged out";
        transactionLog.readFromFile();
        transactionLog.addContent(activity);
        transactionLog.writeToFile();
    }

    public static void testingFunction() {
        // EVENTUALLY REMOVE THIS
        transactionLog.printContents();
    }

    public static void logInventoryAdjustment(
            PharmacyStaff user,
            InventoryItem item,
            int oldQuantity,
            int newQuantity,
            String reason
    ) {
        String activity = String.join(",",
                user.getName(),
                String.valueOf(user.getID()),
                LocalDate.now().toString(),
                "Inventory Adjustment",
                item.getName(),
                String.valueOf(item.getID()),
                "Old Quantity: " + oldQuantity,
                "New Quantity: " + newQuantity,
                "Reason: " + reason
        );

        transactionLog.readFromFile();
        transactionLog.addContent(activity);
        transactionLog.writeToFile();
    }

    public static void logInventoryAudit(
            PharmacyStaff user,
            InventoryItem item,
            int oldQuantity,
            int newQuantity,
            String reason
    ) {
        String activity = String.join(",",
                user.getName(),
                String.valueOf(user.getID()),
                LocalDate.now().toString(),
                "Inventory Audit",
                item.getName(),
                String.valueOf(item.getID()),
                "Old Quantity: " + oldQuantity,
                "New Quantity: " + newQuantity,
                "Reason: " + reason
        );

        transactionLog.readFromFile();
        transactionLog.addContent(activity);
        transactionLog.writeToFile();
    }
}
