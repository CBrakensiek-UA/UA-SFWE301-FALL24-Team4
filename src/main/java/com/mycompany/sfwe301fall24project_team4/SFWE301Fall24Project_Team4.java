/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sfwe301fall24project_team4;
import java.time.LocalDate;

/**
 * This class contains main, which, since not the other PMS subsystems are not implemented,
 * will be run to test the functionality of InventoryControl and ReportGeneration.
 */
public class SFWE301Fall24Project_Team4 {

    public static void main(String[] args) {
        System.out.println("Hello World!");
        Patient patient = new Patient();
        patient.setName("Tim");
        
        PharmacyStaff staff; 
        
        staff = new PharmacyStaff("Test1 Name", 1, "Pharmacy Manager");
        
        ReportGeneration.logLogin(staff, LocalDate.of(2016, 11, 13));
        ReportGeneration.logLogout(staff, LocalDate.of(2016, 11, 13));
        
        
    }
}
