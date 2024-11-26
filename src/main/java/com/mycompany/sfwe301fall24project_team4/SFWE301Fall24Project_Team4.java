/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.sfwe301fall24project_team4;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * This class contains main, which, since not the other PMS subsystems are not
 * implemented, will be run to test the functionality of InventoryControl and
 * ReportGeneration.
 */
public class SFWE301Fall24Project_Team4 {

    public static PharmacyStaff currentUser = new PharmacyStaff("Bill", 1, "Pharmacy Manager");

    public static void fillInventory() {
        InventoryItem item1 = new InventoryItem("Skittles", 24344, 5, 399, LocalDate.of(2024, 3, 20));
        InventoryItem item2 = new InventoryItem("Clock", 88888, 2, 599, LocalDate.of(9999, 12, 31));
        InventoryItem item3 = new Medication("Med1", 24333, 5, 399, 2, LocalDate.of(2024, 11, 30));
        InventoryItem item4 = new InventoryItem("Aspirin", 12345, 100, 299, LocalDate.of(2025, 6, 15));
        InventoryControl.addItem(item1);
        InventoryControl.addItem(item2);
        InventoryControl.addItem(item3);
        InventoryControl.addItem(item4);
    }

    public static void displayOptions() {
        System.out.println("Type the number of the option you want to execute (0 to exit): ");
        System.out.println("  1) Test logging login");
        System.out.println("  2) Test logging logout");
        System.out.println("  3) Perform inventory audit");
        System.out.println("  4) Adjust inventory");
        System.out.print("Option: ");
    }

    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        // Create testing variables
        fillInventory();

        Patient patient = new Patient();
        patient.setName("Tim");

        PharmacyStaff testUser1 = new PharmacyStaff("Test Name", 1, "Pharmacy Manager");

        System.out.println("Created testing data.\n");
        System.out.println("Welcome to the Pharamacy Management System (Inventory Control and Report Generation)!");

        int choice = -1;
        while (choice != 0) {
            InventoryControl.automaticChecks();
            displayOptions();
            choice = scnr.nextInt();
            switch (choice) {
                case 0:
                    break;
                case 1:
                    ReportGeneration.logLogin(testUser1, LocalDate.of(2016, 11, 13));
                    System.out.println("Logged login.");
                    break;
                case 2:
                    ReportGeneration.logLogout(testUser1, LocalDate.of(2016, 11, 14));
                    System.out.println("Logged logout.");
                    break;
                case 3:
                    InventoryControl.performInventoryAudit();
                    break;
                case 4:
                    InventoryControl.adjustInventory();
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }
        }

    }
}
