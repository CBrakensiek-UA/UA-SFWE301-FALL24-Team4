package com.mycompany.sfwe301fall24project_team4;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
    private static final ExternalFile salesTrendReport = new ExternalFile("SalesTrendAnalysisReport.txt");
    private static final ExternalFile prescriptionFulfillmentReport = new ExternalFile("PrescriptionFulfillmentReport.txt");
    private static final ExternalFile turnoverReport = new ExternalFile("InventoryTurnoverRateReport.txt");
    private static final ExternalFile financialMonthlyReport = new ExternalFile("FinancialMonthlyReport.txt");

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
     * Generates a Sales Trend Analysis Report.
     * Analyzes total sales per month over a specified period.
     *
     * @param start The start date for the analysis.
     * @param end   The end date for the analysis.
     */
    public static void generateSalesTrendAnalysisReport(LocalDate start, LocalDate end) {
        transactionLog.readFromFile();
        ArrayList<String> transactions = transactionLog.getContents();
        salesTrendReport.clearContent();
        salesTrendReport.addContent("Sales Trend Analysis Report from " + start + " to " + end + " (Generated on " + LocalDate.now() + ")");
        salesTrendReport.addContent("");

        // Map to hold YearMonth and corresponding total sales
        Map<YearMonth, Double> salesData = new HashMap<>();

        for (String strTransaction : transactions) {
            String[] transaction = strTransaction.split(",");

            // Expected format for purchase transactions:
            // userName,userID,date,eventType,purchaseID,itemID,quantity,supplierID

            if (transaction.length < 8) {
                continue; // Skip malformed entries
            }

            try {
                LocalDate transactionDate = LocalDate.parse(transaction[2]);
                if (!transactionDate.isBefore(start) && !transactionDate.isAfter(end)) { // Within date range
                    if (transaction[3].equals("Purchase from supplier")) {
                        int itemID = Integer.parseInt(transaction[5]);
                        int quantity = Integer.parseInt(transaction[6]);
                        InventoryItem item = InventoryControl.getItemFromID(itemID);
                        if (item != null) {
                            double salesAmount = item.getCost() * quantity;
                            YearMonth ym = YearMonth.from(transactionDate);
                            salesData.put(ym, salesData.getOrDefault(ym, 0.0) + salesAmount);
                        }
                    }
                }
            } catch (Exception e) {
                // Skip entries with parsing issues
                continue;
            }
        }

        // Sort the data by YearMonth
        ArrayList<YearMonth> sortedMonths = new ArrayList<>(salesData.keySet());
        sortedMonths.sort(null);

        // Add sales data to the report
        salesTrendReport.addContent(String.format("%-15s %-15s", "Month", "Total Sales ($)"));
        salesTrendReport.addContent("-----------------------------------");
        for (YearMonth ym : sortedMonths) {
            salesTrendReport.addContent(String.format("%-15s %-15.2f", ym, salesData.get(ym)));
        }

        // Calculate Growth Rate (Year-over-Year)
        salesTrendReport.addContent("");
        salesTrendReport.addContent("Year-over-Year Growth Rates:");
        salesTrendReport.addContent("-----------------------------------");
        Map<Integer, Double> yearlySales = new HashMap<>();
        for (Map.Entry<YearMonth, Double> entry : salesData.entrySet()) {
            int year = entry.getKey().getYear();
            yearlySales.put(year, yearlySales.getOrDefault(year, 0.0) + entry.getValue());
        }

        ArrayList<Integer> sortedYears = new ArrayList<>(yearlySales.keySet());
        sortedYears.sort(null);

        salesTrendReport.addContent(String.format("%-10s %-20s", "Year", "Total Sales ($)"));
        for (int i = 0; i < sortedYears.size(); i++) {
            int year = sortedYears.get(i);
            double totalSales = yearlySales.get(year);
            salesTrendReport.addContent(String.format("%-10d %-20.2f", year, totalSales));
            if (i > 0) {
                int prevYear = sortedYears.get(i - 1);
                double prevSales = yearlySales.get(prevYear);
                double growthRate = ((totalSales - prevSales) / prevSales) * 100;
                salesTrendReport.addContent(String.format("Growth Rate from %d to %d: %.2f%%", prevYear, year, growthRate));
            }
        }

        salesTrendReport.writeToFile();

        System.out.println("Sales Trend Analysis Report generated successfully.");
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
     * Generates an Inventory Valuation Report.
     * Calculates the total value of the inventory at current stock levels.
     */
    public static void generateInventoryValuationReport() {
        inventoryReport.readFromFile(); // Assuming inventory data is read from a file or managed in memory
        ArrayList<InventoryItem> inventoryItems = InventoryControl.inventory; // Access the inventory list
        
        double totalValuation = 0.0;
        inventoryReport.clearContent();
        inventoryReport.addContent("Inventory Valuation Report (Generated on " + LocalDate.now() + ")");
        inventoryReport.addContent("");
        inventoryReport.addContent(String.format("%-20s %-10s %-10s %-15s", "Medication", "ID", "Quantity", "Total Value ($)"));
        inventoryReport.addContent("-------------------------------------------------------------------");
        
        for (InventoryItem item : inventoryItems) {
            double itemTotalValue = item.getQuantity() * item.getCost();
            totalValuation += itemTotalValue;
            inventoryReport.addContent(String.format("%-20s %-10d %-10d %-15.2f", 
                item.getName(), item.getID(), item.getQuantity(), itemTotalValue));
        }
        
        inventoryReport.addContent("");
        inventoryReport.addContent(String.format("Total Inventory Valuation: $%.2f", totalValuation));
        
        inventoryReport.writeToFile();
        
        System.out.println("Inventory Valuation Report generated successfully.");
    }
    
    /**
     * Generates a **Monthly Purchase Report**.
     *
     * @param year  The year of the report.
     * @param month The month of the report.
     */
    public static void generateMonthlyPurchaseReport(int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = LocalDate.of(year, month, start.lengthOfMonth());

        generatePurchaseReport(start, end);
    }

    /**
     * Generates a **Custom Time Range Purchase Report**.
     *
     * @param start The start date of the report.
     * @param end   The end date of the report.
     */
    public static void generateCustomTimeRangePurchaseReport(LocalDate start, LocalDate end) {
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

    /**
     * Generates a Prescription Fulfillment Report.
     * Provides metrics such as average processing time for filling prescriptions.
     *
     * @param start The start date for the analysis.
     * @param end   The end date for the analysis.
     */
    public static void generatePrescriptionFulfillmentReport(LocalDate start, LocalDate end) {
        transactionLog.readFromFile();
        ArrayList<String> transactions = transactionLog.getContents();
        prescriptionFulfillmentReport.clearContent();
        prescriptionFulfillmentReport.addContent("Prescription Fulfillment Report from " + start + " to " + end + " (Generated on " + LocalDate.now() + ")");
        prescriptionFulfillmentReport.addContent("");

        int totalPrescriptions = 0;
        long totalProcessingTime = 0; // in days

        for (String strTransaction : transactions) {
            String[] transaction = strTransaction.split(",");

            // Expected format for prescription fulfillment transactions:
            // userName,userID,date,eventType,prescriptionID,medicationID,quantity,isFilled,filledTime

            if (transaction.length < 9) {
                continue; // Skip malformed entries
            }

            try {
                LocalDate transactionDate = LocalDate.parse(transaction[2]);
                LocalDate filledDate = LocalDate.parse(transaction[8]); // Assuming filledTime is stored as YYYY-MM-DD

                if (!transactionDate.isBefore(start) && !transactionDate.isAfter(end) &&
                    transaction[3].equals("Prescription Filled")) {
                    long daysToFill = java.time.temporal.ChronoUnit.DAYS.between(transactionDate, filledDate);
                    totalPrescriptions++;
                    totalProcessingTime += daysToFill;
                }
            } catch (Exception e) {
                // Skip entries with parsing issues
                continue;
            }
        }

        prescriptionFulfillmentReport.addContent("Total Prescriptions Filled: " + totalPrescriptions);
        if (totalPrescriptions > 0) {
            double averageProcessingTime = (double) totalProcessingTime / totalPrescriptions;
            prescriptionFulfillmentReport.addContent(String.format("Average Processing Time: %.2f days", averageProcessingTime));
        } else {
            prescriptionFulfillmentReport.addContent("No prescriptions were filled in the specified period.");
        }

        prescriptionFulfillmentReport.writeToFile();

        System.out.println("Prescription Fulfillment Report generated successfully.");
    }

    /**
     * Generates an Inventory Turnover Rate Report.
     * Calculates the frequency of stock depletion for each medication over a specified period.
     *
     * @param start The start date for the analysis.
     * @param end   The end date for the analysis.
     */
    public static void generateTurnoverRateReport(LocalDate start, LocalDate end) {
        transactionLog.readFromFile();
        ArrayList<String> transactions = transactionLog.getContents();
        ExternalFile turnoverReport = new ExternalFile("InventoryTurnoverRateReport.txt");
        turnoverReport.clearContent();
        turnoverReport.addContent("Inventory Turnover Rate Report from " + start + " to " + end + " (Generated on " + LocalDate.now() + ")");
        turnoverReport.addContent("");

        // Map to hold itemID and number of times its stock was depleted
        Map<Integer, Integer> turnoverData = new HashMap<>();

        for (String strTransaction : transactions) {
            String[] transaction = strTransaction.split(",");

            // Expected format for inventory depletion:
            // eventType can be "Inventory Adjustment", "Shipment Received", "Prescription Filled", etc.

            if (transaction.length < 4) {
                continue; // Skip malformed entries
            }

            try {
                LocalDate transactionDate = LocalDate.parse(transaction[2]);
                if (!transactionDate.isBefore(start) && !transactionDate.isAfter(end)) { // Within date range
                    String eventType = transaction[3];
                    if (eventType.equals("Prescription Filled") || eventType.equals("Inventory Adjustment")) {
                        int itemID = Integer.parseInt(transaction[5]); // Assuming itemID is at index 5
                        // Check if stock was depleted after this transaction
                        InventoryItem item = InventoryControl.getItemFromID(itemID);
                        if (item != null && item.getQuantity() <= item.getReorderThreshold()) {
                            turnoverData.put(itemID, turnoverData.getOrDefault(itemID, 0) + 1);
                        }
                    }
                }
            } catch (Exception e) {
                // Skip entries with parsing issues
                continue;
            }
        }

        // Add turnover data to the report
        turnoverReport.addContent(String.format("%-20s %-20s", "Medication", "Turnover Rate"));
        turnoverReport.addContent("-----------------------------------------------");
        for (Map.Entry<Integer, Integer> entry : turnoverData.entrySet()) {
            InventoryItem item = InventoryControl.getItemFromID(entry.getKey());
            if (item != null) {
                turnoverReport.addContent(String.format("%-20s %-20d", item.getName(), entry.getValue()));
            }
        }

        turnoverReport.writeToFile();

        System.out.println("Inventory Turnover Rate Report generated successfully.");
    }

    /**
     * Logs a transaction entry.
     *
     * @param logEntry The transaction details as a comma-separated string.
     */
    public static void logTransaction(String logEntry) {
        transactionLog.addContent(logEntry);
        transactionLog.writeToFile();
    }
    
    /**
     * Generates a Monthly Financial Report.
     * Includes Total Revenue, Expense Summaries, Profit Margins, and Customer Transactions.
     *
     * @param year  The year for the report.
     * @param month The month for the report (1-12).
     */
    public static void generateMonthlyFinancialReport(int year, int month) {
        transactionLog.readFromFile();
        ArrayList<String> transactions = transactionLog.getContents();
        financialMonthlyReport.clearContent();
        financialMonthlyReport.addContent("Monthly Financial Report for " + YearMonth.of(year, month) + " (Generated on " + LocalDate.now() + ")");
        financialMonthlyReport.addContent("");

        double totalRevenuePrescription = 0.0;
        double totalRevenueNonPrescription = 0.0;
        double totalExpenses = 0.0;
        int transactionsCash = 0;
        int transactionsCard = 0;
        int transactionsInsurance = 0;

        for (String strTransaction : transactions) {
            String[] transaction = strTransaction.split(",");

            if (transaction.length < 9) {
                continue; // Skip malformed entries
            }

            try {
                LocalDate transactionDate = LocalDate.parse(transaction[2]);
                if (transactionDate.getYear() == year && transactionDate.getMonthValue() == month) {
                    String eventType = transaction[3].trim();
                    String paymentType = transaction[7].trim();
                    double amount = Double.parseDouble(transaction[8].trim());

                    // Count payment types
                    switch (paymentType) {
                        case "Cash":
                            transactionsCash++;
                            break;
                        case "Card":
                            transactionsCard++;
                            break;
                        case "Insurance":
                            transactionsInsurance++;
                            break;
                        default:
                            // Handle other payment types or ignore
                            break;
                    }

                    if (eventType.equalsIgnoreCase("Prescription Filled")) {
                        totalRevenuePrescription += amount;
                    } else if (eventType.equalsIgnoreCase("Non-Prescription Sale")) {
                        totalRevenueNonPrescription += amount;
                    } else if (eventType.equalsIgnoreCase("Inventory Purchase")) {
                        totalExpenses += amount;
                    }
                }
            } catch (Exception e) {
                // Skip entries with parsing issues
                continue;
            }
        }

        double totalRevenue = totalRevenuePrescription + totalRevenueNonPrescription;
        double profitMargins = totalRevenue - totalExpenses;

        // Writing the report
        financialMonthlyReport.addContent("Total Revenue:");
        financialMonthlyReport.addContent(String.format("  Prescription Sales: $%.2f", totalRevenuePrescription));
        financialMonthlyReport.addContent(String.format("  Non-Prescription Sales: $%.2f", totalRevenueNonPrescription));
        financialMonthlyReport.addContent(String.format("  Total Revenue: $%.2f", totalRevenue));
        financialMonthlyReport.addContent("");

        financialMonthlyReport.addContent("Expense Summaries:");
        financialMonthlyReport.addContent(String.format("  Inventory Purchases: $%.2f", totalExpenses));
        financialMonthlyReport.addContent(String.format("  Total Expenses: $%.2f", totalExpenses));
        financialMonthlyReport.addContent("");

        financialMonthlyReport.addContent(String.format("Profit Margins: $%.2f", profitMargins));
        financialMonthlyReport.addContent("");

        financialMonthlyReport.addContent("Customer Transactions by Payment Type:");
        financialMonthlyReport.addContent(String.format("  Cash Transactions: %d", transactionsCash));
        financialMonthlyReport.addContent(String.format("  Card Transactions: %d", transactionsCard));
        financialMonthlyReport.addContent(String.format("  Insurance Transactions: %d", transactionsInsurance));
        financialMonthlyReport.addContent(String.format("  Total Transactions: %d", transactionsCash + transactionsCard + transactionsInsurance));
        financialMonthlyReport.addContent("");

        financialMonthlyReport.writeToFile();

        System.out.println("Monthly Financial Report for " + YearMonth.of(year, month) + " generated successfully.");
    }
}
