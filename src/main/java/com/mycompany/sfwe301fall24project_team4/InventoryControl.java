/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.sfwe301fall24project_team4;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 */
public class InventoryControl {

    public static ArrayList<InventoryItem> inventory = new ArrayList<>();

    public static void addItem(InventoryItem item) {
        inventory.add(item);
    }

    public static void automaticChecks() {
        System.out.println("Perfoming automatic inventory checks...");

        // Check 1: anything expired?
        if (SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacy Manager")) { // Only pharmacy manager gets notification
            for (InventoryItem item : InventoryControl.inventory) {
                if (item.isExpired()) {
                    System.out.println(item + " has expired.");
                }
            }
        }

        // Check 2: anything expiring within 30 days?
        if (SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacy Manager")) { // Only pharmacy manager gets notification
            for (InventoryItem item : InventoryControl.inventory) {
                if (item.isExpiredWithin30Days()) {
                    System.out.println(item + " will expire within 30 days.");
                }
            }
        }

        // Check 3: automatic reordering, stock level alerts
        System.out.println("Automatic inventory checks completed.\n");
    }

    public static void adjustInventory() {
        // Ensure only authorized users can adjust inventory
        if (!SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacy Manager")) {
            System.out.println("Access denied. Only Pharmacy Managers can adjust inventory.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the ID of the item to adjust:");
        int itemId = scanner.nextInt();

        InventoryItem itemToAdjust = null;
        for (InventoryItem item : inventory) {
            if (item.getID() == itemId) {
                itemToAdjust = item;
                break;
            }
        }

        if (itemToAdjust == null) {
            System.out.println("Item not found in inventory.");
            return;
        }

        System.out.println("Current quantity of " + itemToAdjust.getName() + ": " + itemToAdjust.getQuantity());
        System.out.println("Enter the new quantity:");
        int newQuantity = scanner.nextInt();

        System.out.println("Enter the reason for adjustment:");
        scanner.nextLine(); // Consume newline
        String reason = scanner.nextLine();

        int oldQuantity = itemToAdjust.getQuantity();
        itemToAdjust.setQuantity(newQuantity);

        System.out.println("Inventory updated successfully.");

        // Log the adjustment
        ReportGeneration.logInventoryAdjustment(
                SFWE301Fall24Project_Team4.currentUser,
                itemToAdjust,
                oldQuantity,
                newQuantity,
                reason
        );
    }

    public static void performInventoryAudit() {
        // Ensure only authorized users can perform audit
        if (!SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacy Manager")) {
            System.out.println("Access denied. Only Pharmacy Managers can perform inventory audits.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        for (InventoryItem item : inventory) {
            System.out.println("Item: " + item.getName() + " (ID: " + item.getID() + ")");
            System.out.println("System Quantity: " + item.getQuantity());
            System.out.print("Enter physical count: ");
            int physicalCount = scanner.nextInt();

            if (physicalCount != item.getQuantity()) {
                int oldQuantity = item.getQuantity();
                item.setQuantity(physicalCount);

                System.out.println("Discrepancy found and updated.");

                // Log the adjustment
                ReportGeneration.logInventoryAudit(
                        SFWE301Fall24Project_Team4.currentUser,
                        item,
                        oldQuantity,
                        physicalCount,
                        "Audit discrepancy"
                );
            } else {
                System.out.println("No discrepancy found.");
            }
        }
    }
}
