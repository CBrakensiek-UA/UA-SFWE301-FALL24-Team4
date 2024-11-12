/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;

/**
 * This class represents the Report Generation Subsystem.
 * This subsystem manages the various logs and prints out reports based on them.
 */
public class ReportGeneration {
    private Log transactionLog;
    
    public ReportGeneration(){
        transactionLog = new Log("TransactionLog.csv");
    }
    
    public void logLogin(PharmacyStaff user, String time){
        String activity = user.getName() + "," + user.getID() + "," + time + ",logged in";
        //transactionLog.printContents();
        transactionLog.readFromFile();
        //transactionLog.printContents();
        transactionLog.addContent(activity);
        //transactionLog.printContents();
        transactionLog.writeToFile();
        //transactionLog.printContents();
    }
    
    public void logLogout(PharmacyStaff user, String time){
        String activity = user.getName() + "," + user.getID() + "," + time + ",logged out";
        transactionLog.readFromFile();
        transactionLog.addContent(activity);
        transactionLog.writeToFile();
    }
    
    public void testingFunction(){
        // EVENTUALLY REMOVE THIS
        transactionLog.printContents();
    }
}
