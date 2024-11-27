/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;

import java.util.ArrayList;
import java.time.*;
import java.time.temporal.ChronoField;

/**
 * This class represents the Report Generation Subsystem. This subsystem manages
 * the various logs and prints out reports based on them.
 */
public class ReportGeneration {

    private static ExternalFile transactionLog = new ExternalFile("TransactionLog.csv");
    
    private static ExternalFile financialReport = new ExternalFile("FinancialReport.txt");
    private static ExternalFile inventoryReport = new ExternalFile("InventoryReport.txt");
    private static ExternalFile transactionReport = new ExternalFile("TransactionReport.txt");

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
    
    public static void generateFinancialReport(int year, int month){
        // create the inventory report for a specific month
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, (int)start.range(ChronoField.DAY_OF_MONTH).getMaximum());
        
        generateInventoryReport(start, end);
    }
    
    public static void generateFinancialReport(LocalDate start, LocalDate end){
        transactionLog.readFromFile();
        // FINISH
        
    }
    
    public static void generateInventoryReport(int year, int month){
        // create the inventory report for a specific month
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, (int)start.range(ChronoField.DAY_OF_MONTH).getMaximum());
        
        generateInventoryReport(start, end);
    }
    
    public static void generateInventoryReport(LocalDate start, LocalDate end){
        transactionLog.readFromFile();
        // FINISH
        
    }
    
    public static void generateTransactionReport(int year, int month){
        // create the inventory report for a specific month
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, (int)start.range(ChronoField.DAY_OF_MONTH).getMaximum());
        
        generateTransactionReport(start, end);
    }
    
    public static void generateTransactionReport(LocalDate start, LocalDate end){
        transactionLog.readFromFile();
        ArrayList<String> transactions = transactionLog.getContents();
        transactionReport.clearContent();
        transactionReport.addContent("Transaction Report from " + start + " to " + end + " (generated " + LocalDate.now() + ")");
        transactionReport.addContent("");
        
        for(String strTransaction : transactions){
            String[] transaction = strTransaction.split(",");
            
            if(!LocalDate.parse(transaction[2]).isBefore(start) && !LocalDate.parse(transaction[2]).isAfter(end)){ // is in date range?
                if(transaction[3].equals("logged in")){
                    transactionReport.addContent("On " + transaction[2] + ", " + transaction[0] + " (ID: " 
                            + transaction[1] + ") logged into the system.");
                }
                else if(transaction[3].equals("logged out")){
                    transactionReport.addContent("On " + transaction[2] + ", " + transaction[0] + " (ID: " 
                            + transaction[1] + ") logged out of the system.");
                }
                else if(transaction[3].equals("Inventory Adjustment")){
                    transactionReport.addContent("On " + transaction[2] + ", " + transaction[0] + " (ID: " 
                            + transaction[1] + ") performed a manual inventory adjustment, changing the quantity of "
                            + transaction[4] + " (ID: " + transaction[5] + ") from "
                            + transaction[6].substring(14) + " to " + transaction[7].substring(14) 
                            + " for the following reason: '" + transaction[8].substring(8) + "'.");
                }
                else if(transaction[3].equals("Inventory Audit")){
                    transactionReport.addContent("On " + transaction[2] + ", " + transaction[0] + " (ID: " 
                            + transaction[1] + ") performed an inventory audit, changing the quantity of "
                            + transaction[4] + " (ID: " + transaction[5] + ") from "
                            + transaction[6].substring(14) + " to " + transaction[7].substring(14) 
                            + " for the following reason: '" + transaction[8].substring(8) + "'.");
                }
                else if(transaction[3].equals("Purchase from supplier")){
                    // PURCHASING LOGGING NOT IMPLEMENTED YET
                    transactionReport.addContent("On " + transaction[2] + ", " + transaction[0] + " (ID: " 
                            + transaction[1] + ") made a purchase (ID: " + transaction[3]
                            + ") from supplier " + transaction[4] + " (ID: " + transaction[5] + ").");
                }
            }
        }
        transactionReport.writeToFile();
        
    }
    
}
