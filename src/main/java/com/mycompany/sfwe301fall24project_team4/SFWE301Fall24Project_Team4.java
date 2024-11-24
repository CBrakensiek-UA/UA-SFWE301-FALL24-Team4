/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.sfwe301fall24project_team4;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * This class contains main, which, since not the other PMS subsystems are not implemented,
 * will be run to test the functionality of InventoryControl and ReportGeneration.
 */
public class SFWE301Fall24Project_Team4 {
    
    private static void fillInventory(){
        InventoryItem item1 = new InventoryItem("Skittles", 243, 5, 399, LocalDate.of(2017, 3, 20));
        InventoryItem item2 = new InventoryItem("Clock", 888, 2, 599, LocalDate.of(9999, 12, 31));
        InventoryItem item3 = new Medication("Med1", 243, 5, 399, 2, LocalDate.of(2017, 3, 20));
        
        InventoryControl.addItem(item1);
        InventoryControl.addItem(item2);
        InventoryControl.addItem(item3);
    }
    
    private static void displayOptions(){
        System.out.println("Type the number of the option you want to execute (0 to exit): ");
        System.out.println("  1) Test logging login");
        System.out.println("  2) Test logging logout");
        
        System.out.print("Option: ");
    }
    
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);
        
        // Create testing variables
        fillInventory();
        
        Patient patient = new Patient();
        patient.setName("Tim");
        
        PharmacyStaff staff = new PharmacyStaff("Test1 Name", 1, "Pharmacy Manager");
        
        System.out.println("Created testing variables.\n");
        System.out.println("Welcome to the Pharamacy Management System (Inventory Control and Report Generation)!");
        
        int choice = -1;
        while(choice != 0){
            displayOptions();
            choice = scnr.nextInt();
            switch(choice){
                case 0:
                    break;
                case 1:
                    ReportGeneration.logLogin(staff, LocalDate.of(2016, 11, 13));
                    System.out.println("Successful login");
                    break;
                case 2:
                    ReportGeneration.logLogout(staff, LocalDate.of(2016, 11, 13));
                    System.out.println("Successful logout");
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }
        
        
        
    }
}
