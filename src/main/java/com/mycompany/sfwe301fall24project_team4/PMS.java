package com.mycompany.sfwe301fall24project_team4;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class contains main, which, since not the other PMS subsystems are not
 * implemented, will be run to test the functionality of InventoryControl and
 * ReportGeneration.
 */
public class PMS {

    public static PharmacyStaff currentUser = new PharmacyStaff("Bill", 1, "Pharmacy Manager");
    public static ArrayList<Supplier> suppliers = new ArrayList<>();

    /**
     * Adds a supplier to the suppliers list.
     *
     * @param supplier The Supplier to add.
     */
    public static void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
    }

    /**
     * Retrieves a supplier based on its ID.
     *
     * @param id The ID of the supplier.
     * @return The Supplier if found, otherwise null.
     */
    public static Supplier getSupplierByID(int id) {
        for (Supplier supplier : suppliers) {
            if (supplier.getID() == id) {
                return supplier;
            }
        }
        return null;
    }

    /**
     * Initializes sample suppliers.
     */
    public static void initializeSuppliers() {
        Supplier supplier1 = new Supplier("HealthSuppliers Inc.", 5001, "456 Supply St", "Metropolis", "NY", 10001, "Alice", "Smith", "alice@healthsuppliers.com", 5551234);
        Supplier supplier2 = new Supplier("MediSupply Co.", 5002, "789 Commerce Ave", "Gotham", "NJ", 07001, "Bob", "Johnson", "bob@medisupply.com", 5555678);
        addSupplier(supplier1);
        addSupplier(supplier2);
    }

    /**
     * Fills the inventory with sample data.
     */
    public static void fillInventory() {
        InventoryItem item1 = new InventoryItem("Skittles", 24344, 5, 399, LocalDate.of(2024, 3, 20), "Department A", 5, 2);
        InventoryItem item2 = new InventoryItem("Clock", 88888, 2, 599, LocalDate.of(9999, 12, 31), "Department B", 1, 1);
        InventoryItem item3 = new Medication("Med1", 24333, 5, 399, LocalDate.of(2024, 11, 30), "Department C", 11111, 22222, 111, 222);
        InventoryItem item4 = new InventoryItem("Aspirin", 12345, 100, 299, LocalDate.of(2025, 6, 15), "Department D", 20, 10);
        InventoryControl.addItem(item1);
        InventoryControl.addItem(item2);
        InventoryControl.addItem(item3);
        InventoryControl.addItem(item4);
    }

    /**
     * Adds sample prescriptions to the system.
     */
    public static void addSamplePrescriptions() {
        Patient patient1 = new Patient("Tim", 1001, "1990-05-20", "123 Main St", "555-1234", "tim@example.com", "InsureCo");
        Prescription prescription1 = new Prescription(2001, patient1, 12345, 2); // Aspirin
        Prescription prescription2 = new Prescription(2002, patient1, 24344, 1); // Skittles
        InventoryControl.addPrescription(prescription1);
        InventoryControl.addPrescription(prescription2);
    }

    /**
     * Displays the main menu options.
     */
    public static void displayOptions() {
        System.out.println("Type the number of the option you want to execute (0 to exit): ");
        System.out.println("  1) Test logging login");
        System.out.println("  2) Test logging logout");
        System.out.println("  3) Perform inventory audit");
        System.out.println("  4) Adjust inventory");
        System.out.println("  5) Scan inventory item");
        System.out.println("  6) Fill prescription");
        System.out.println("  7) Receive shipment");
        System.out.println("  8) Return item");
        System.out.println(" 20) Generate financial report");
        System.out.println(" 21) Generate inventory report");
        System.out.println(" 22) Generate transaction report");
        System.out.println(" 23) Generate critical stock levels report");
        System.out.println(" 24) Generate expiration data report");
        System.out.println(" 25) Generate inventory valuation Report");
        System.out.println(" 26) Generate user activity report");
        System.out.println(" 27) Generate purchase report");
        System.out.println(" 28) Generate sales trend analysis report");
        System.out.println(" 29) Generate prescription fulfillment report");
        System.out.println(" 30) Generate inventory turnover rate report"); // New Option
        System.out.print("Option: ");
    }

    /**
     * The main method to run the Pharmacy Management System.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in);

        // Create testing variables
        fillInventory();
        addSamplePrescriptions();
        initializeSuppliers(); // Initialize suppliers

        // Create a test user
        PharmacyStaff testUser1 = new PharmacyStaff("Test Name", 1, "Pharmacy Manager");
        Patient testUser2 = new Patient("Tom", 555);

        System.out.println("Created testing data.\n");
        System.out.println("Welcome to the Pharmacy Management System (Inventory Control and Report Generation)!");
        
        InventoryControl.automaticChecks();
        
        int choice = -1;
        int subchoice = -1;
        while (choice != 0) {
            displayOptions();
            if (!scnr.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number corresponding to the options.");
                scnr.next(); // Clear invalid input
                continue;
            }
            choice = scnr.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Exiting the system. Goodbye!");
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
                case 5:
                    int scanID = InventoryControl.scanItemID(scnr);
                    if (scanID != -1) {
                        System.out.println("Scanned item ID: " + scanID);
                    }
                    break;
                case 6:
                    InventoryControl.fillPrescription();
                    break;
                case 7:
                    InventoryControl.receiveShipment();
                    break;
                case 8:
                    InventoryControl.returnItem(testUser2);
                    break;
                case 20:
                    // Financial Report Generation has been removed or relocated
                    System.out.println("Financial Report Generation has been moved to separate functionalities or is not yet implemented.");
                    break;
                case 21:
                    // Implement Inventory Report Generation
                    System.out.println("Choose a suboption:");
                    System.out.println("  1) Monthly inventory report");
                    System.out.println("  2) Custom timerange inventory report");
                    System.out.print("Option: ");
                    if (!scnr.hasNextInt()) {
                        System.out.println("Invalid input. Returning to main menu.");
                        scnr.next(); // Clear invalid input
                        break;
                    }
                    subchoice = scnr.nextInt();

                    switch (subchoice) {
                        case 1:
                            System.out.print("Enter year: ");
                            if (!scnr.hasNextInt()) {
                                System.out.println("Invalid input. Year must be an integer.");
                                scnr.next(); // Clear invalid input
                                break;
                            }
                            int invYear = scnr.nextInt();
                            System.out.print("Enter month (1-12): ");
                            if (!scnr.hasNextInt()) {
                                System.out.println("Invalid input. Month must be an integer between 1 and 12.");
                                scnr.next(); // Clear invalid input
                                break;
                            }
                            int invMonth = scnr.nextInt();
                            if (invMonth < 1 || invMonth > 12) {
                                System.out.println("Invalid month. Please enter a value between 1 and 12.");
                                break;
                            }
                            ReportGeneration.generateInventoryReport(invYear, invMonth);
                            break;
                        case 2:
                            System.out.print("Enter start date (YYYY-MM-DD): ");
                            String invStart = scnr.next();
                            System.out.print("Enter end date (YYYY-MM-DD): ");
                            String invEnd = scnr.next();
                            try {
                                LocalDate start = LocalDate.parse(invStart);
                                LocalDate end = LocalDate.parse(invEnd);
                                ReportGeneration.generateInventoryReport(start, end);
                            } catch (Exception e) {
                                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                            }
                            break;
                        default:
                            System.out.println("Invalid suboption.");
                            break;
                    }
                    break;
                case 22:
                    // Implement Transaction Report Generation
                    System.out.println("Choose a suboption:");
                    System.out.println("  1) Monthly transaction report");
                    System.out.println("  2) Custom timerange transaction report");
                    System.out.print("Option: ");
                    if (!scnr.hasNextInt()) {
                        System.out.println("Invalid input. Returning to main menu.");
                        scnr.next(); // Clear invalid input
                        break;
                    }
                    subchoice = scnr.nextInt();

                    switch (subchoice) {
                        case 1:
                            System.out.print("Enter year: ");
                            if (!scnr.hasNextInt()) {
                                System.out.println("Invalid input. Year must be an integer.");
                                scnr.next(); // Clear invalid input
                                break;
                            }
                            int txnYear = scnr.nextInt();
                            System.out.print("Enter month (1-12): ");
                            if (!scnr.hasNextInt()) {
                                System.out.println("Invalid input. Month must be an integer between 1 and 12.");
                                scnr.next(); // Clear invalid input
                                break;
                            }
                            int txnMonth = scnr.nextInt();
                            if (txnMonth < 1 || txnMonth > 12) {
                                System.out.println("Invalid month. Please enter a value between 1 and 12.");
                                break;
                            }
                            ReportGeneration.generateTransactionReport(txnYear, txnMonth);
                            break;
                        case 2:
                            System.out.print("Enter start date (YYYY-MM-DD): ");
                            String txnStart = scnr.next();
                            System.out.print("Enter end date (YYYY-MM-DD): ");
                            String txnEnd = scnr.next();
                            try {
                                LocalDate start = LocalDate.parse(txnStart);
                                LocalDate end = LocalDate.parse(txnEnd);
                                ReportGeneration.generateTransactionReport(start, end);
                            } catch (Exception e) {
                                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                            }
                            break;
                        default:
                            System.out.println("Invalid suboption.");
                            break;
                    }
                    break;
                case 23:
                    ReportGeneration.generateCriticalStockLevelsReport();
                    break;
                case 24:
                    System.out.print("Enter number of days for expiration data (e.g., 30): ");
                    if (!scnr.hasNextInt()) {
                        System.out.println("Invalid input. Number of days must be an integer.");
                        scnr.next(); // Clear invalid input
                        break;
                    }
                    int days = scnr.nextInt();
                    if (days < 0) {
                        System.out.println("Invalid number of days. Please enter a non-negative integer.");
                        break;
                    }
                    ReportGeneration.generateExpirationDataReport(days);
                    break;
                case 25:
                    ReportGeneration.generateInventoryValuationReport();
                    break;
                case 26:
                    System.out.print("Enter the user ID: ");
                    if (!scnr.hasNextInt()) {
                        System.out.println("Invalid input. User ID must be an integer.");
                        scnr.next(); // Clear invalid input
                        break;
                    }
                    int id = scnr.nextInt();
                    ReportGeneration.generateUserActivityReport(id);
                    break;
                case 27:
                    System.out.println("Choose a suboption:");
                    System.out.println("  1) Monthly purchase report");
                    System.out.println("  2) Custom timerange purchase report");
                    System.out.print("Option: ");
                    if (!scnr.hasNextInt()) {
                        System.out.println("Invalid input. Returning to main menu.");
                        scnr.next(); // Clear invalid input
                        break;
                    }
                    int purchaseSubchoice = scnr.nextInt();

                    switch (purchaseSubchoice) {
                        case 1:
                            System.out.print("Enter year: ");
                            if (!scnr.hasNextInt()) {
                                System.out.println("Invalid input. Year must be an integer.");
                                scnr.next(); // Clear invalid input
                                break;
                            }
                            int pYear = scnr.nextInt();
                            System.out.print("Enter month (1-12): ");
                            if (!scnr.hasNextInt()) {
                                System.out.println("Invalid input. Month must be an integer between 1 and 12.");
                                scnr.next(); // Clear invalid input
                                break;
                            }
                            int pMonth = scnr.nextInt();
                            if (pMonth < 1 || pMonth > 12) {
                                System.out.println("Invalid month. Please enter a value between 1 and 12.");
                                break;
                            }
                            ReportGeneration.generateMonthlyPurchaseReport(pYear, pMonth);
                            break;
                        case 2:
                            System.out.print("Enter start date (YYYY-MM-DD): ");
                            String pStart = scnr.next();
                            System.out.print("Enter end date (YYYY-MM-DD): ");
                            String pEnd = scnr.next();
                            try {
                                LocalDate start = LocalDate.parse(pStart);
                                LocalDate end = LocalDate.parse(pEnd);
                                ReportGeneration.generateCustomTimeRangePurchaseReport(start, end);
                            } catch (Exception e) {
                                System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                            }
                            break;
                        default:
                            System.out.println("Invalid suboption.");
                            break;
                    }
                    break;
                case 28:
                    // Generate Sales Trend Analysis Report
                    System.out.print("Enter start date for sales trend analysis (YYYY-MM-DD): ");
                    String trendStart = scnr.next();
                    System.out.print("Enter end date for sales trend analysis (YYYY-MM-DD): ");
                    String trendEnd = scnr.next();
                    try {
                        LocalDate startDate = LocalDate.parse(trendStart);
                        LocalDate endDate = LocalDate.parse(trendEnd);
                        ReportGeneration.generateSalesTrendAnalysisReport(startDate, endDate);
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                    }
                    break;
                case 29:
                    // Generate Prescription Fulfillment Report
                    System.out.print("Enter start date for prescription fulfillment analysis (YYYY-MM-DD): ");
                    String fulfillStart = scnr.next();
                    System.out.print("Enter end date for prescription fulfillment analysis (YYYY-MM-DD): ");
                    String fulfillEnd = scnr.next();
                    try {
                        LocalDate startDate = LocalDate.parse(fulfillStart);
                        LocalDate endDate = LocalDate.parse(fulfillEnd);
                        ReportGeneration.generatePrescriptionFulfillmentReport(startDate, endDate);
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                    }
                    break;
                case 30:
                    // Generate Inventory Turnover Rate Report
                    System.out.print("Enter start date for inventory turnover analysis (YYYY-MM-DD): ");
                    String turnoverStart = scnr.next();
                    System.out.print("Enter end date for inventory turnover analysis (YYYY-MM-DD): ");
                    String turnoverEnd = scnr.next();
                    try {
                        LocalDate startDate = LocalDate.parse(turnoverStart);
                        LocalDate endDate = LocalDate.parse(turnoverEnd);
                        ReportGeneration.generateTurnoverRateReport(startDate, endDate);
                    } catch (Exception e) {
                        System.out.println("Invalid date format. Please use YYYY-MM-DD.");
                    }
                    break;
                default:
                    System.out.println("Invalid option.");
                    break;
            }

            System.out.println();
        }

    }
}