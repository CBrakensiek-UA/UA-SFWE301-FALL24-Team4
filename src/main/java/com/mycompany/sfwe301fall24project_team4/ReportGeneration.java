package com.mycompany.sfwe301fall24project_team4;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * This class represents the Report Generation Subsystem. This subsystem manages
 * the various logs and prints out reports based on them.
 */
public class ReportGeneration {

    private static final ExternalFile transactionLog = new ExternalFile("TransactionLog.csv");

    private static final ExternalFile financialReport = new ExternalFile("FinancialReport.txt");
    private static final ExternalFile inventoryReport = new ExternalFile("InventoryReport.txt");
    private static final ExternalFile transactionReport = new ExternalFile("TransactionReport.txt");
    private static final ExternalFile criticalStockReport = new ExternalFile("CriticalStockLevelsReport.txt");
    private static final ExternalFile expirationReport = new ExternalFile("ExpirationDataReport.txt");
    private static final ExternalFile purchaseReport = new ExternalFile("PurchaseReport.txt");
    private static final ExternalFile userActivityReport = new ExternalFile("UserActivityReport.txt");

    /**
     * Logs a user login event.
     *
     * @param user The PharmacyStaff who logged in.
     * @param time The time of login.
     */
    public static void logLogin(PharmacyStaff user, LocalDate time) {
        String activity = String.join(",",
                user.getName(),
                String.valueOf(user.getID()),
                time.toString(),
                "logged in"
        );

        transactionLog.readFromFile();
        transactionLog.addContent(activity);
        transactionLog.writeToFile();
    }

    /**
     * Logs a user logout event.
     *
     * @param user The PharmacyStaff who logged out.
     * @param time The time of logout.
     */
    public static void logLogout(PharmacyStaff user, LocalDate time) {
        String activity = String.join(",",
                user.getName(),
                String.valueOf(user.getID()),
                time.toString(),
                "logged out"
        );

        transactionLog.readFromFile();
        transactionLog.addContent(activity);
        transactionLog.writeToFile();
    }

    /**
     * Logs an inventory adjustment event.
     *
     * @param user        The PharmacyStaff who performed the adjustment.
     * @param item        The InventoryItem adjusted.
     * @param oldQuantity The old quantity before adjustment.
     * @param newQuantity The new quantity after adjustment.
     * @param reason      The reason for adjustment.
     */
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

    /**
     * Logs an inventory audit event.
     *
     * @param user        The PharmacyStaff who performed the audit.
     * @param item        The InventoryItem audited.
     * @param oldQuantity The old quantity before audit.
     * @param newQuantity The new quantity after audit.
     * @param reason      The reason for audit.
     */
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

    /**
     * Logs a prescription filling event.
     *
     * @param user        The PharmacyStaff who filled the prescription.
     * @param prescription The Prescription that was filled.
     * @param oldQuantity The old quantity before filling.
     * @param newQuantity The new quantity after filling.
     */
    public static void logPrescriptionFilling(
            PharmacyStaff user,
            Prescription prescription,
            int oldQuantity,
            int newQuantity
    ) {
        String activity = String.join(",",
                user.getName(),
                String.valueOf(user.getID()),
                LocalDate.now().toString(),
                "Prescription Filled",
                String.valueOf(prescription.getID()),
                String.valueOf(prescription.getMedicationID()),
                String.valueOf(prescription.getAmount()),
                "Old Quantity: " + oldQuantity,
                "New Quantity: " + newQuantity
        );

        transactionLog.readFromFile();
        transactionLog.addContent(activity);
        transactionLog.writeToFile();
    }

    /**
     * Logs a purchase order event.
     *
     * @param user         The PharmacyStaff who made the purchase.
     * @param purchaseOrder The Purchase order created.
     * @param item         The InventoryItem purchased.
     * @param quantity     The quantity purchased.
     */
    public static void logPurchaseOrder(
            PharmacyStaff user,
            Purchase purchaseOrder,
            InventoryItem item,
            int quantity
    ) {
        String activity = String.join(",",
                user.getName(),
                String.valueOf(user.getID()),
                purchaseOrder.getPurchaseDate().toString(),
                "Purchase from supplier",
                String.valueOf(purchaseOrder.getPurchaseID()),
                String.valueOf(item.getID()),
                String.valueOf(quantity),
                String.valueOf(purchaseOrder.getSupplier().getID())
        );

        transactionLog.readFromFile();
        transactionLog.addContent(activity);
        transactionLog.writeToFile();
    }

    /**
     * Logs a shipment receipt event.
     *
     * @param user             The PharmacyStaff who received the shipment.
     * @param item             The InventoryItem received.
     * @param quantityReceived The quantity received.
     * @param shipmentDate     The date of shipment receipt.
     */
    public static void logShipmentReceipt(
            PharmacyStaff user,
            InventoryItem item,
            int quantityReceived,
            LocalDate shipmentDate
    ) {
        String activity = String.join(",",
                user.getName(),
                String.valueOf(user.getID()),
                shipmentDate.toString(),
                "Shipment Received",
                String.valueOf(item.getID()),
                String.valueOf(quantityReceived)
        );

        transactionLog.readFromFile();
        transactionLog.addContent(activity);
        transactionLog.writeToFile();
    }
    
    /**
     * Logs a shipment receipt event.
     *
     * @param user             The Patient who returned the item.
     * @param item             The InventoryItem received.
     * @param quantityReceived The quantity received.
     * @param returnDate       The date when the item(s) were returned.
     */
    public static void logReturn(
            Patient user,
            InventoryItem item,
            int quantityReceived,
            LocalDate returnDate
    ) {
        String activity = String.join(",",
                user.getName(),
                String.valueOf(user.getID()),
                returnDate.toString(),
                "Item(s) returned",
                String.valueOf(item.getID()),
                String.valueOf(quantityReceived)
        );

        transactionLog.readFromFile();
        transactionLog.addContent(activity);
        transactionLog.writeToFile();
    }

    /**
     * Generates a financial report for a specific month.
     *
     * @param year  The year of the report.
     * @param month The month of the report.
     */
    public static void generateFinancialReport(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, start.lengthOfMonth());

        generateFinancialReport(start, end);
    }
    /**
     * Generates a financial report for a specific date range.
     *
     * @param start The start date.
     * @param end   The end date.
     */
    public static void generateFinancialReport(LocalDate start, LocalDate end) {
        // Placeholder implementation
        // Implement based on financial data tracking
        System.out.println("Financial Report Generation is not yet implemented.");
    }

    /**
     * Generates an inventory report for a specific month.
     *
     * @param year  The year of the report.
     * @param month The month of the report.
     */
    public static void generateInventoryReport(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, start.lengthOfMonth());

        generateInventoryReport(start, end);
    }

    /**
     * Generates an inventory report for a specific date range.
     *
     * @param start The start date.
     * @param end   The end date.
     */
    public static void generateInventoryReport(LocalDate start, LocalDate end) {
        transactionLog.readFromFile();
        ArrayList<String> transactions = transactionLog.getContents();
        inventoryReport.clearContent();
        inventoryReport.addContent("Inventory Report from " + start + " to " + end + " (generated " + LocalDate.now() + ")");
        inventoryReport.addContent("");

        for (String strTransaction : transactions) {
            String[] transaction = strTransaction.split(",");

            if (transaction.length < 9) {
                // Skip malformed entries
                continue;
            }

            try {
                LocalDate transactionDate = LocalDate.parse(transaction[2]);
                if (!transactionDate.isBefore(start) && !transactionDate.isAfter(end)) { // is in date range?
                    if (transaction[3].equals("Inventory Adjustment") || transaction[3].equals("Inventory Audit")) {
                        inventoryReport.addContent("On " + transactionDate + ", " + transaction[0] + " (ID: "
                                + transaction[1] + ") performed a " + transaction[3] + " on " + transaction[4] + " (ID: "
                                + transaction[5] + "). " + transaction[6] + ", " + transaction[7] + ". Reason: '"
                                + transaction[8].substring(8) + "'.");
                    }
                }
            } catch (Exception e) {
                // Skip entries with parsing issues
                continue;
            }
        }
        inventoryReport.writeToFile();
        System.out.println("Inventory Report generated successfully.");
    }

    /**
     * Generates a transaction report for a specific month.
     *
     * @param year  The year of the report.
     * @param month The month of the report.
     */
    public static void generateTransactionReport(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, start.lengthOfMonth());

        generateTransactionReport(start, end);
    }

    /**
     * Generates a transaction report for a specific date range.
     *
     * @param start The start date.
     * @param end   The end date.
     */
    public static void generateTransactionReport(LocalDate start, LocalDate end) {
        transactionLog.readFromFile();
        ArrayList<String> transactions = transactionLog.getContents();
        transactionReport.clearContent();
        transactionReport.addContent("Transaction Report from " + start + " to " + end + " (generated " + LocalDate.now() + ")");
        transactionReport.addContent("");

        for (String strTransaction : transactions) {
            String[] transaction = strTransaction.split(",");

            if (transaction.length < 6) {
                // Skip malformed entries
                continue;
            }

            try {
                LocalDate transactionDate = LocalDate.parse(transaction[2]);
                if (!transactionDate.isBefore(start) && !transactionDate.isAfter(end)) { // is in date range?
                    switch (transaction[3]) {
                        case "logged in":
                            transactionReport.addContent("On " + transactionDate + ", " + transaction[0] + " (ID: "
                                    + transaction[1] + ") logged into the system.");
                            break;
                        case "logged out":
                            transactionReport.addContent("On " + transactionDate + ", " + transaction[0] + " (ID: "
                                    + transaction[1] + ") logged out of the system.");
                            break;
                        case "Inventory Adjustment":
                        case "Inventory Audit":
                            if (transaction.length >= 9) {
                                transactionReport.addContent("On " + transactionDate + ", " + transaction[0] + " (ID: "
                                        + transaction[1] + ") performed a " + transaction[3] + " on " + transaction[4] + " (ID: "
                                        + transaction[5] + "). " + transaction[6] + ", " + transaction[7] + ". Reason: '"
                                        + transaction[8].substring(8) + "'.");
                            }
                            break;
                        case "Purchase from supplier":
                            if (transaction.length >= 8) {
                                InventoryItem item = InventoryControl.getItemFromID(Integer.parseInt(transaction[5]));
                                Supplier supplier = PMS.getSupplierByID(Integer.parseInt(transaction[7]));
                                String supplierName = (supplier != null) ? supplier.getName() : "Unknown Supplier";
                                String itemName = (item != null) ? item.getName() : "Unknown Item";
                                transactionReport.addContent("On " + transactionDate + ", " + transaction[0] + " (ID: "
                                        + transaction[1] + ") made a purchase (ID: " + transaction[4]
                                        + ") for " + transaction[6] + " units of " + itemName + " (ID: " + transaction[5]
                                        + ") from supplier " + supplierName + " (ID: " + transaction[7] + ").");
                            }
                            break;
                        case "Prescription Filled":
                            if (transaction.length >= 7) {
                                InventoryItem item = InventoryControl.getItemFromID(Integer.parseInt(transaction[5]));
                                String itemName = (item != null) ? item.getName() : "Unknown Item";
                                transactionReport.addContent("On " + transactionDate + ", " + transaction[0] + " (ID: "
                                        + transaction[1] + ") filled Prescription " + transaction[4] + ", dispensing "
                                        + transaction[6] + " units of " + itemName + " (ID: " + transaction[5] + ").");
                            }
                            break;
                        case "Shipment Received":
                            if (transaction.length >= 6) {
                                InventoryItem item = InventoryControl.getItemFromID(Integer.parseInt(transaction[4]));
                                String itemName = (item != null) ? item.getName() : "Unknown Item";
                                transactionReport.addContent("On " + transactionDate + ", " + transaction[0] + " (ID: "
                                        + transaction[1] + ") received a shipment of " + transaction[5] + " units of "
                                        + itemName + " (ID: " + transaction[4] + ").");
                            }
                            break;
                        default:
                            // Handle other transaction types if any
                            break;
                    }
                }
            } catch (Exception e) {
                // Skip entries with parsing issues
                continue;
            }
        }
        transactionReport.writeToFile();

        System.out.println("Transaction Report generated successfully.");
    }

    /**
     * Generates a critical stock levels report.
     */
    public static void generateCriticalStockLevelsReport() {
        criticalStockReport.clearContent();
        criticalStockReport.addContent("Critical Stock Levels Report (Generated on " + LocalDate.now() + ")");
        criticalStockReport.addContent("");

        for (InventoryItem item : InventoryControl.inventory) {
            if (item.getQuantity() <= item.getCriticalThreshold()) {
                criticalStockReport.addContent(item.getName() + " (ID: " + item.getID() + ") - Quantity: " + item.getQuantity());
            }
        }

        criticalStockReport.writeToFile();
        System.out.println("Critical Stock Levels Report generated successfully.");
    }

    /**
     * Generates an expiration data report for items expiring within a specified number of days.
     *
     * @param days The number of days to check for expiration.
     */
    public static void generateExpirationDataReport(int days) {
        expirationReport.clearContent();
        expirationReport.addContent("Expiration Data Report (Items expiring within " + days + " days) (Generated on " + LocalDate.now() + ")");
        expirationReport.addContent("");

        LocalDate thresholdDate = LocalDate.now().plusDays(days);

        for (InventoryItem item : InventoryControl.inventory) {
            if (!item.isExpired() && item.getExpirationDate().isBefore(thresholdDate)) {
                expirationReport.addContent(item.getName() + " (ID: " + item.getID() + ") - Expires on: " + item.getExpirationDate());
            }
        }

        expirationReport.writeToFile();
        System.out.println("Expiration Data Report generated successfully.");
    }

    /**
     * Generates an inventory valuation report.
     */
    public static void generateInventoryValuationReport() {
        int totalValuation = InventoryControl.calculateInventoryValuation();
        financialReport.clearContent();
        financialReport.addContent("Inventory Valuation Report (Generated on " + LocalDate.now() + ")");
        financialReport.addContent("");
        financialReport.addContent("Total Inventory Valuation: $" + (totalValuation / 100.0));
        financialReport.writeToFile();
        System.out.println("Inventory Valuation Report generated successfully.");
    }

    /**
     * Generates a purchase report for a specific month.
     *
     * @param year  The year of the report.
     * @param month The month of the report.
     */
    public static void generatePurchaseReport(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, start.lengthOfMonth());

        generatePurchaseReport(start, end);
    }

    /**
     * Generates a purchase report for a specific date range.
     *
     * @param start The start date.
     * @param end   The end date.
     */
    public static void generatePurchaseReport(LocalDate start, LocalDate end) {
        transactionLog.readFromFile();
        ArrayList<String> transactions = transactionLog.getContents();
        purchaseReport.clearContent();
        purchaseReport.addContent("Purchase Report from " + start + " to " + end + " (generated " + LocalDate.now() + ")");
        purchaseReport.addContent("");

        for (String strTransaction : transactions) {
            String[] transaction = strTransaction.split(",");

            // Ensure the transaction has enough fields to prevent ArrayIndexOutOfBoundsException
            if (transaction.length < 8) {
                // Skip malformed entries
                continue;
            }

            try {
                LocalDate transactionDate = LocalDate.parse(transaction[2]);
                if (!transactionDate.isBefore(start) && !transactionDate.isAfter(end)) { // is in date range?
                    if (transaction[3].equals("Purchase from supplier")) {
                        // Expected format:
                        // userName,userID,date,eventType,purchaseID,itemID,quantity,supplierID
                        if (transaction.length >= 8) {
                            InventoryItem item = InventoryControl.getItemFromID(Integer.parseInt(transaction[5]));
                            Supplier supplier = PMS.getSupplierByID(Integer.parseInt(transaction[7]));
                            String supplierName = (supplier != null) ? supplier.getName() : "Unknown Supplier";
                            String itemName = (item != null) ? item.getName() : "Unknown Item";
                            purchaseReport.addContent("On " + transactionDate + ", " + transaction[0] + " (ID: "
                                    + transaction[1] + ") made a purchase (ID: " + transaction[4]
                                    + ") for " + transaction[6] + " units of " + itemName + " (ID: " + transaction[5]
                                    + ") from supplier " + supplierName + " (ID: " + transaction[7] + ").");
                        }
                    }
                }
            } catch (Exception e) {
                // Skip entries with parsing issues
                continue;
            }
        }
        purchaseReport.writeToFile();

        System.out.println("Purchase Report generated successfully.");
    }
    
    
     /**
     * Generates an activity report for a specific user
     *
     * @param id The user id
     */
    public static void generateUserActivityReport(int id) {
        transactionLog.readFromFile();
        ArrayList<String> transactions = transactionLog.getContents();
        userActivityReport.clearContent();
        userActivityReport.addContent("User Activity Report for user ID " + id + " (generated " + LocalDate.now() + ")");
        userActivityReport.addContent("");

        for (String strTransaction : transactions) {
            String[] transaction = strTransaction.split(",");

            // Ensure the transaction has enough fields to prevent ArrayIndexOutOfBoundsException
            if (transaction.length < 4) {
                // Skip malformed entries
                continue;
            }

            try {
                if (Integer.parseInt(transaction[1]) == id) { // is this the right user?
                    switch (transaction[3]) {
                        case "logged in":
                            userActivityReport.addContent("On " + transaction[2] + ", " + transaction[0] + " (ID: "
                                    + transaction[1] + ") logged into the system.");
                            break;
                        case "logged out":
                            userActivityReport.addContent("On " + transaction[2] + ", " + transaction[0] + " (ID: "
                                    + transaction[1] + ") logged out of the system.");
                            break;
                        case "Inventory Adjustment":
                        case "Inventory Audit":
                            if (transaction.length >= 9) {
                                userActivityReport.addContent("On " + transaction[2] + ", " + transaction[0] + " (ID: "
                                        + transaction[1] + ") performed a " + transaction[3] + " on " + transaction[4] + " (ID: "
                                        + transaction[5] + "). " + transaction[6] + ", " + transaction[7] + ". Reason: '"
                                        + transaction[8].substring(8) + "'.");
                            }
                            break;
                        case "Purchase from supplier":
                            if (transaction.length >= 8) {
                                InventoryItem item = InventoryControl.getItemFromID(Integer.parseInt(transaction[5]));
                                Supplier supplier = PMS.getSupplierByID(Integer.parseInt(transaction[7]));
                                String supplierName = (supplier != null) ? supplier.getName() : "Unknown Supplier";
                                String itemName = (item != null) ? item.getName() : "Unknown Item";
                                userActivityReport.addContent("On " + transaction[2] + ", " + transaction[0] + " (ID: "
                                        + transaction[1] + ") made a purchase (ID: " + transaction[4]
                                        + ") for " + transaction[6] + " units of " + itemName + " (ID: " + transaction[5]
                                        + ") from supplier " + supplierName + " (ID: " + transaction[7] + ").");
                            }
                            break;
                        case "Prescription Filled":
                            if (transaction.length >= 7) {
                                InventoryItem item = InventoryControl.getItemFromID(Integer.parseInt(transaction[5]));
                                String itemName = (item != null) ? item.getName() : "Unknown Item";
                                userActivityReport.addContent("On " + transaction[2] + ", " + transaction[0] + " (ID: "
                                        + transaction[1] + ") filled Prescription " + transaction[4] + ", dispensing "
                                        + transaction[6] + " units of " + itemName + " (ID: " + transaction[5] + ").");
                            }
                            break;
                        case "Shipment Received":
                            if (transaction.length >= 6) {
                                InventoryItem item = InventoryControl.getItemFromID(Integer.parseInt(transaction[4]));
                                String itemName = (item != null) ? item.getName() : "Unknown Item";
                                userActivityReport.addContent("On " + transaction[2] + ", " + transaction[0] + " (ID: "
                                        + transaction[1] + ") received a shipment of " + transaction[5] + " units of "
                                        + itemName + " (ID: " + transaction[4] + ").");
                            }
                            break;
                        default:
                            // Handle other transaction types if any
                            break;
                    }
                }
            } catch (Exception e) {
                // Skip entries with parsing issues
                continue;
            }
        }
        userActivityReport.writeToFile();

        System.out.println("User Activity Report generated successfully.");
    }
    
}
