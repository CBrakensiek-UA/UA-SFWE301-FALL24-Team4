/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sfwe301fall24project_team4;

/**
 * This class contains main, which, since not the other PMS subsystems are not implemented,
 * will be run to test the functionality of InventoryControl and ReportGeneration.
 */
public class SFWE301Fall24Project_Team4 {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Patient patient = new Patient();
        patient.setName("Tim");
        
        ReportGeneration reportGen = new ReportGeneration();
        PharmacyStaff staff;
        
        
        staff = new PharmacyStaff("Test1 Name", 1, "Pharmacy Manager");
        
        reportGen.logLogin(staff, "11/12/16");
        reportGen.logLogout(staff, "11/13/16");
        
        
    }
}
