package com.mycompany.sfwe301fall24project_team4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 */
public class InventoryControl {

    public static ArrayList<InventoryItem> inventory = new ArrayList<>();
    public static ArrayList<Prescription> prescriptions = new ArrayList<>();

    /**
     * Retrieves an inventory item based on its ID.
     *
     * @param id The ID of the inventory item.
     * @return The InventoryItem if found, otherwise null.
     */
    public static InventoryItem getItemFromID(int id) {
        for (InventoryItem item : inventory) {
            if (item.getID() == id) {
                return item;
            }
        }
        return null;
    }

    /**
     * Adds an inventory item to the inventory list.
     *
     * @param item The InventoryItem to add.
     */
    public static void addItem(InventoryItem item) {
        inventory.add(item);
    }

    /**
     * Adds a prescription to the prescriptions list.
     *
     * @param prescription The Prescription to add.
     */
    public static void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    /**
     * Performs automatic inventory checks, including expiration, stock levels,
     * and automatic reordering.
     */
    public static void automaticChecks() {
        System.out.println("Performing automatic inventory checks...");

        // Check for expired items
        if (SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacy Manager")) { // Only pharmacy manager gets notification
            for (InventoryItem item : InventoryControl.inventory) {
                if (item.isExpired()) {
                    System.out.println(item + " has expired.");
                }
            }
        }

        // Check for soon-to-expire items
        if (SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacy Manager")) { // Only pharmacy manager gets notification
            for (InventoryItem item : InventoryControl.inventory) {
                if (item.isExpiredWithin30Days()) {
                    System.out.println(item + " will expire within 30 days.");
                }
            }
        }

        // Check for stock level alerts and automatic reordering
        for (InventoryItem item : inventory) {
            if (item.getQuantity() <= item.getReorderThreshold()) {
                System.out.println("Stock level for " + item.getName() + " (ID: " + item.getID() + ") is below reorder threshold (" + item.getReorderThreshold() + "). Generating purchase order...");
                generatePurchaseOrder(item, 50); // Example: reorder 50 units
            }

            if (item.getQuantity() <= item.getCriticalThreshold()) {
                System.out.println("ALERT: Stock level for " + item.getName() + " (ID: " + item.getID() + ") is critically low (" + item.getQuantity() + " units remaining). Immediate action required.");
                // Optionally, send notifications or take further actions
            }
        }

        System.out.println("Automatic inventory checks completed.\n");
    }

    /**
     * Generates a purchase order for a given inventory item.
     *
     * @param item     The inventory item to reorder.
     * @param quantity The quantity to reorder.
     */
    private static void generatePurchaseOrder(InventoryItem item, int quantity) {
        // For simplicity, select the first supplier
        if (SFWE301Fall24Project_Team4.suppliers.isEmpty()) {
            System.out.println("No suppliers available to generate purchase order.");
            return;
        }

        Supplier supplier = SFWE301Fall24Project_Team4.suppliers.get(0);

        Purchase purchaseOrder = new Purchase();
        purchaseOrder.getItems().add(item.getID());
        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setPurchaseDate(LocalDate.now());

        // Calculate total cost
        purchaseOrder.calculateTotalCost();

        // Log the purchase order
        ReportGeneration.logPurchaseOrder(
                SFWE301Fall24Project_Team4.currentUser,
                purchaseOrder,
                item,
                quantity
        );

        System.out.println("Purchase order generated for " + quantity + " units of " + item.getName() + " from supplier " + supplier.getName() + ".");
    }

    /**
     * Fills a prescription based on its ID.
     */
    public static void fillPrescription() {
        // Ensure only authorized users can fill prescriptions
        if (!SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacist")
                && !SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacy Manager")) {
            System.out.println("Access denied. Only Pharmacists or Pharmacy Managers can fill prescriptions.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Prescription ID to fill:");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Prescription ID must be an integer.");
            scanner.next(); // Clear invalid input
            return;
        }
        int prescriptionID = scanner.nextInt();

        Prescription prescriptionToFill = null;
        for (Prescription prescription : prescriptions) {
            if (prescription.getID() == prescriptionID && !prescription.isFilled()) {
                prescriptionToFill = prescription;
                break;
            }
        }

        if (prescriptionToFill == null) {
            System.out.println("Prescription not found or already filled.");
            return;
        }

        boolean success = prescriptionToFill.fillPrescription();
        if (success) {
            System.out.println("Prescription " + prescriptionID + " has been filled.");
        } else {
            System.out.println("Failed to fill prescription " + prescriptionID + ".");
        }
    }

    /**
     * Performs an inventory audit by comparing system quantities with physical counts.
     */
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
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Quantity must be an integer.");
                scanner.next(); // Clear invalid input
                continue;
            }
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

    /**
     * Allows authorized users to adjust inventory levels manually.
     */
    public static void adjustInventory() {
        // Ensure only authorized users can adjust inventory
        if (!SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacy Manager")
                && !SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacist")) {
            System.out.println("Access denied. Only Pharmacy Managers or Pharmacists can adjust inventory.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Inventory Item ID to adjust:");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Item ID must be an integer.");
            scanner.next(); // Clear invalid input
            return;
        }
        int itemId = scanner.nextInt();

        InventoryItem item = getItemFromID(itemId);
        if (item == null) {
            System.out.println("Inventory Item not found.");
            return;
        }

        System.out.println("Current Quantity: " + item.getQuantity());
        System.out.print("Enter new quantity: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Quantity must be an integer.");
            scanner.next(); // Clear invalid input
            return;
        }
        int newQuantity = scanner.nextInt();

        int oldQuantity = item.getQuantity();
        item.setQuantity(newQuantity);

        System.out.println("Inventory adjusted successfully.");

        // Log the adjustment
        System.out.print("Enter the reason for adjustment: ");
        scanner.nextLine(); // Consume newline
        String reason = scanner.nextLine();

        ReportGeneration.logInventoryAdjustment(
                SFWE301Fall24Project_Team4.currentUser,
                item,
                oldQuantity,
                newQuantity,
                reason
        );
    }

    /**
     * Allows users to scan an inventory item by its ID.
     *
     * @param scanner The Scanner object for input.
     * @return The scanned item ID, or -1 if invalid.
     */
    public static int scanItemID(Scanner scanner) {
        System.out.print("Enter the Inventory Item ID to scan: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Item ID must be an integer.");
            scanner.next(); // Clear invalid input
            return -1;
        }
        int itemId = scanner.nextInt();

        InventoryItem item = getItemFromID(itemId);
        if (item == null) {
            System.out.println("Inventory Item not found.");
            return -1;
        }

        System.out.println("Scanned Item: " + item.getName() + " (ID: " + item.getID() + ")");
        return itemId;
    }

    /**
     * Calculates the total valuation of the inventory based on current stock levels and unit costs.
     *
     * @return The total inventory valuation in cents.
     */
    public static int calculateInventoryValuation() {
        int totalValuation = 0;
        for (InventoryItem item : inventory) {
            totalValuation += item.getQuantity() * item.getCost();
        }
        return totalValuation;
    }

    /**
     * Allows authorized users to record the receipt of new inventory shipments.
     */
    public static void receiveShipment() {
        // Ensure only authorized users can receive shipments
        if (!SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacy Manager")
                && !SFWE301Fall24Project_Team4.currentUser.getRole().equals("Pharmacist")) {
            System.out.println("Access denied. Only Pharmacy Managers or Pharmacists can receive shipments.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Inventory Item ID received:");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Item ID must be an integer.");
            scanner.next(); // Clear invalid input
            return;
        }
        int itemId = scanner.nextInt();

        InventoryItem item = getItemFromID(itemId);
        if (item == null) {
            System.out.println("Inventory Item not found.");
            return;
        }

        System.out.println("Enter the quantity received:");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Quantity must be an integer.");
            scanner.next(); // Clear invalid input
            return;
        }
        int quantityReceived = scanner.nextInt();

        System.out.println("Enter the supplier ID:");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Supplier ID must be an integer.");
            scanner.next(); // Clear invalid input
            return;
        }
        int supplierId = scanner.nextInt();
        Supplier supplier = SFWE301Fall24Project_Team4.getSupplierByID(supplierId);
        if (supplier == null) {
            System.out.println("Supplier not found. Please ensure the supplier ID is correct.");
            return;
        }

        System.out.println("Enter the shipment date (YYYY-MM-DD):");
        String shipmentDateStr = scanner.next();
        LocalDate shipmentDate;
        try {
            shipmentDate = LocalDate.parse(shipmentDateStr);
        } catch (Exception e) {
            System.out.println("Invalid date format.");
            return;
        }

        int oldQuantity = item.getQuantity();
        item.setQuantity(oldQuantity + quantityReceived);

        System.out.println("Shipment received and inventory updated successfully.");

        // Log the shipment receipt
        ReportGeneration.logShipmentReceipt(
                SFWE301Fall24Project_Team4.currentUser,
                item,
                quantityReceived,
                shipmentDate
        );
    }
    
    /**
     * Handle returned items, logging the change and updating the inventory.
     * @param user             The Patient who returned the item.
     */
    public static void returnItem(Patient user) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Inventory Item ID returned:");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Item ID must be an integer.");
            scanner.next(); // Clear invalid input
            return;
        }
        int itemId = scanner.nextInt();

        InventoryItem item = getItemFromID(itemId);
        if (item == null) {
            System.out.println("Inventory Item not found.");
            return;
        }

        System.out.println("Enter the quantity returned:");
        if (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Quantity must be an integer.");
            scanner.next(); // Clear invalid input
            return;
        }
        int quantityReturned = scanner.nextInt();
        
        System.out.println("Enter the return date (YYYY-MM-DD):");
        String returnDateStr = scanner.next();
        LocalDate returnDate;
        try {
            returnDate = LocalDate.parse(returnDateStr);
        } catch (Exception e) {
            System.out.println("Invalid date format.");
            return;
        }
        
        int oldQuantity = item.getQuantity();
        item.setQuantity(oldQuantity + quantityReturned);

        System.out.println("Item(s) returned successfully.");

        // Log the shipment receipt
        ReportGeneration.logReturn(
                user,
                item,
                quantityReturned,
                returnDate
        );
    }
}
