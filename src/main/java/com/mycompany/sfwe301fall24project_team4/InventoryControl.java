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
    public static PharmacyStaff currentUser = PMS.currentUser;

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
     * Retrieves a prescription by its ID.
     *
     * @param id The ID of the prescription.
     * @return The Prescription if found, otherwise null.
     */
    public static Prescription getPrescriptionByID(int id) {
        for (Prescription prescription : prescriptions) {
            if (prescription.getID() == id) {
                return prescription;
            }
        }
        return null;
    }

    /**
     * Logs a transaction.
     *
     * @param logEntry The transaction details as a comma-separated string.
     */
    public static void logTransaction(String logEntry) {
        ReportGeneration.logTransaction(logEntry);
    }

    /**
     * Performs automatic inventory checks, including expiration, stock levels,
     * and automatic reordering.
     */
    public static void automaticChecks() {
        System.out.println("Performing automatic inventory checks...");

        // Check for expired items
        if (PMS.currentUser.getRole().equals("Pharmacy Manager")) { // Only pharmacy manager gets notification
            for (InventoryItem item : InventoryControl.inventory) {
                if (item.isExpired()) {
                    System.out.println(item + " has expired.");
                }
            }
        }

        // Check for soon-to-expire items
        if (PMS.currentUser.getRole().equals("Pharmacy Manager")) { // Only pharmacy manager gets notification
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
        if (PMS.suppliers.isEmpty()) {
            System.out.println("No suppliers available to generate purchase order.");
            return;
        }

        Supplier supplier = PMS.suppliers.get(0);

        Purchase purchaseOrder = new Purchase();
        purchaseOrder.getItems().add(item.getID());
        purchaseOrder.setSupplier(supplier);
        purchaseOrder.setPurchaseDate(LocalDate.now());

        // Calculate total cost
        purchaseOrder.calculateTotalCost();

        // Log the purchase order
        ReportGeneration.logPurchaseOrder(
                PMS.currentUser,
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
    Scanner scnr = new Scanner(System.in);
        System.out.print("Enter Prescription ID to fill: ");
        int prescriptionID = scnr.nextInt();
        Prescription prescription = getPrescriptionByID(prescriptionID);
        if (prescription == null) {
            System.out.println("Prescription not found.");
            return;
        }
        if (prescription.isFilled()) {
            System.out.println("Prescription is already filled.");
            return;
        }

        InventoryItem item = getItemFromID(prescription.getMedicationID());
        if (item == null) {
            System.out.println("Medication not found in inventory.");
            return;
        }
        if (item.getQuantity() < prescription.getAmount()) {
            System.out.println("Insufficient stock to fill the prescription.");
            return;
        }

        // Deduct the quantity
        item.setQuantity(item.getQuantity() - prescription.getAmount());

        // Mark prescription as filled
        LocalDate filledDate = LocalDate.now();
        prescription.setFilled(true, filledDate);

        // Log the transaction
        String logEntry = currentUser.getName() + "," + currentUser.getID() + "," + LocalDate.now()
                + "," + "Prescription Filled" + "," + prescription.getID() + "," + prescription.getMedicationID()
                + "," + prescription.getAmount() + "," + prescription.isFilled() + "," + filledDate;
        logTransaction(logEntry);

        System.out.println("Prescription filled successfully.");
    }

    /**
     * Performs an inventory audit by comparing system quantities with physical counts.
     */
    public static void performInventoryAudit() {
        // Ensure only authorized users can perform audit
        if (!PMS.currentUser.getRole().equals("Pharmacy Manager")) {
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
                        PMS.currentUser,
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
     * Adjusts inventory for a specific item and logs the adjustment.
     */
    public static void adjustInventory() {
        Scanner scnr = new Scanner(System.in);
        System.out.print("Enter the Item ID to adjust: ");
        int itemID = scnr.nextInt();
        InventoryItem item = getItemFromID(itemID);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        System.out.print("Enter the quantity to adjust (use negative numbers to decrease): ");
        int quantityAdjustment = scnr.nextInt();
        item.setQuantity(item.getQuantity() + quantityAdjustment);

        // Log the adjustment
        String logEntry = currentUser.getName() + "," + currentUser.getID() + "," + LocalDate.now()
                + "," + "Inventory Adjustment" + "," + "N/A" + "," + itemID + "," + quantityAdjustment + "," + "N/A";
        logTransaction(logEntry);

        System.out.println("Inventory adjusted successfully.");
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
        if (!PMS.currentUser.getRole().equals("Pharmacy Manager")
                && !PMS.currentUser.getRole().equals("Pharmacist")) {
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
        Supplier supplier = PMS.getSupplierByID(supplierId);
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
                PMS.currentUser,
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
