package view;

import controller.BankingController;
import model.*;
import util.DatabaseConnection;

import java.util.List;
import java.util.Scanner;

/**
 * Simple Main Application - Works without Spring Boot
 * Run this directly to test your banking system
 */
public class SimpleBankingApp {
    private static BankingController controller;
    private static Scanner scanner;

    public static void main(String[] args) {
        controller = new BankingController();
        scanner = new Scanner(System.in);

        // Test database connection
        if (DatabaseConnection.testConnection()) {
            System.out.println("✓ Database connected successfully!");
        } else {
            System.out.println("✗ Database connection failed!");
            return;
        }

        // Initialize with sample data
        initializeSampleData();

        // Start the application
        displayWelcome();
        mainMenu();
    }

    private static void displayWelcome() {
        System.out.println("\n╔══════════════════════════════════════╗");
        System.out.println("║   BANKING SYSTEM - CSE202 Project   ║");
        System.out.println("║      OOAD with Java Assignment       ║");
        System.out.println("╚══════════════════════════════════════╝\n");
    }

    private static void initializeSampleData() {
        System.out.println("Initializing sample data...");
        
        // Create 5 sample customers if database is empty
        List<Customer> customers = controller.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("Creating sample customers...");
            
            controller.registerCustomer("John", "Doe", "123 Main St, Gaborone", "71234567", "john.doe@email.com");
            controller.registerCustomer("Jane", "Smith", "456 Park Ave, Gaborone", "72345678", "jane.smith@email.com");
            controller.registerCustomer("Mike", "Johnson", "789 Oak Rd, Francistown", "73456789", "mike.j@email.com");
            controller.registerCustomer("Sarah", "Williams", "321 Pine St, Maun", "74567890", "sarah.w@email.com");
            controller.registerCustomer("David", "Brown", "654 Elm St, Gaborone", "75678901", "david.b@email.com");
            
            System.out.println("✓ Sample customers created!");
            
            // Create sample accounts
            controller.openSavingsAccount(1, "Main Branch");
            controller.openInvestmentAccount(2, "Main Branch", 1000.00);
            controller.openChequeAccount(3, "Francistown Branch", "ABC Company", "Gaborone Industrial");
            
            // Make some deposits
            List<Account> accounts = controller.getAllAccounts();
            if (!accounts.isEmpty()) {
                controller.deposit(accounts.get(0).getAccountNumber(), 500.00);
                if (accounts.size() > 1) {
                    controller.deposit(accounts.get(1).getAccountNumber(), 2000.00);
                }
            }
            
            System.out.println("✓ Sample accounts created with transactions!");
        }
        System.out.println("✓ System ready!\n");
    }

    private static void mainMenu() {
        while (true) {
            System.out.println("\n╔═══════════ MAIN MENU ════════════╗");
            System.out.println("║ 1. Customer Management           ║");
            System.out.println("║ 2. Account Management            ║");
            System.out.println("║ 3. Transactions                  ║");
            System.out.println("║ 4. Reports & Viewing             ║");
            System.out.println("║ 5. Interest Calculation          ║");
            System.out.println("║ 0. Exit                          ║");
            System.out.println("╚══════════════════════════════════╝");
            
            int choice = getIntInput("\nEnter choice: ");
            
            switch (choice) {
                case 1:
                    customerMenu();
                    break;
                case 2:
                    accountMenu();
                    break;
                case 3:
                    transactionMenu();
                    break;
                case 4:
                    reportsMenu();
                    break;
                case 5:
                    interestMenu();
                    break;
                case 0:
                    System.out.println("\n✓ Thank you for using Banking System!");
                    DatabaseConnection.closeConnection();
                    return;
                default:
                    System.out.println("✗ Invalid choice!");
            }
        }
    }

    private static void customerMenu() {
        System.out.println("\n--- CUSTOMER MANAGEMENT ---");
        System.out.println("1. Register New Customer");
        System.out.println("2. View All Customers");
        System.out.println("3. Search Customer");
        System.out.println("4. View Customer Accounts");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("\nChoice: ");
        
        switch (choice) {
            case 1:
                registerCustomer();
                break;
            case 2:
                viewAllCustomers();
                break;
            case 3:
                searchCustomer();
                break;
            case 4:
                viewCustomerAccounts();
                break;
        }
    }

    private static void accountMenu() {
        System.out.println("\n--- ACCOUNT MANAGEMENT ---");
        System.out.println("1. Open Savings Account (0.05% monthly interest)");
        System.out.println("2. Open Investment Account (5% monthly interest, min BWP 500)");
        System.out.println("3. Open Cheque Account (for employed customers)");
        System.out.println("4. View Account Details");
        System.out.println("5. View All Accounts");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("\nChoice: ");
        
        switch (choice) {
            case 1:
                openSavingsAccount();
                break;
            case 2:
                openInvestmentAccount();
                break;
            case 3:
                openChequeAccount();
                break;
            case 4:
                viewAccountDetails();
                break;
            case 5:
                viewAllAccounts();
                break;
        }
    }

    private static void transactionMenu() {
        System.out.println("\n--- TRANSACTIONS ---");
        System.out.println("1. Deposit Funds");
        System.out.println("2. Withdraw Funds");
        System.out.println("3. View Transaction History");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("\nChoice: ");
        
        switch (choice) {
            case 1:
                depositFunds();
                break;
            case 2:
                withdrawFunds();
                break;
            case 3:
                viewTransactionHistory();
                break;
        }
    }

    private static void reportsMenu() {
        System.out.println("\n--- REPORTS & VIEWING ---");
        System.out.println("1. View All Customers");
        System.out.println("2. View All Accounts");
        System.out.println("3. View All Transactions");
        System.out.println("4. Customer Account Summary");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("\nChoice: ");
        
        switch (choice) {
            case 1:
                viewAllCustomers();
                break;
            case 2:
                viewAllAccounts();
                break;
            case 3:
                viewAllTransactions();
                break;
            case 4:
                viewCustomerAccounts();
                break;
        }
    }

    private static void interestMenu() {
        System.out.println("\n--- INTEREST CALCULATION ---");
        System.out.println("1. Calculate Interest for Specific Account");
        System.out.println("2. Calculate Interest for ALL Accounts");
        System.out.println("0. Back to Main Menu");
        
        int choice = getIntInput("\nChoice: ");
        
        switch (choice) {
            case 1:
                calculateInterestSingle();
                break;
            case 2:
                calculateInterestAll();
                break;
        }
    }

    // Implementation methods
    private static void registerCustomer() {
        System.out.println("\n=== REGISTER NEW CUSTOMER ===");
        System.out.print("First Name: ");
        String firstname = scanner.nextLine();
        System.out.print("Surname: ");
        String surname = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        if (controller.registerCustomer(firstname, surname, address, phone, email)) {
            System.out.println("✓ Customer registered successfully!");
        } else {
            System.out.println("✗ Failed to register customer");
        }
    }

    private static void viewAllCustomers() {
        System.out.println("\n=== ALL CUSTOMERS ===");
        List<Customer> customers = controller.getAllCustomers();
        
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        
        System.out.println(String.format("%-5s %-15s %-15s %-20s %-15s", 
            "ID", "First Name", "Surname", "Phone", "Email"));
        System.out.println("─".repeat(80));
        
        for (Customer c : customers) {
            System.out.println(String.format("%-5d %-15s %-15s %-20s %-15s",
                c.getCustomerId(), c.getFirstname(), c.getSurname(), 
                c.getPhone(), c.getEmail()));
        }
    }

    private static void searchCustomer() {
        System.out.print("\nEnter search term (name): ");
        String searchTerm = scanner.nextLine();
        
        List<Customer> customers = controller.searchCustomers(searchTerm);
        
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
            return;
        }
        
        System.out.println("\n=== SEARCH RESULTS ===");
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    private static void viewCustomerAccounts() {
        int customerId = getIntInput("\nEnter Customer ID: ");
        Customer customer = controller.getCustomer(customerId);
        
        if (customer == null) {
            System.out.println("✗ Customer not found!");
            return;
        }
        
        System.out.println("\n=== CUSTOMER: " + customer.getFullName() + " ===");
        List<Account> accounts = controller.getCustomerAccounts(customerId);
        
        if (accounts.isEmpty()) {
            System.out.println("No accounts found for this customer.");
            return;
        }
        
        for (Account a : accounts) {
            System.out.println("\n" + a.getAccountDetails());
        }
    }

    private static void openSavingsAccount() {
        int customerId = getIntInput("\nEnter Customer ID: ");
        System.out.print("Branch: ");
        String branch = scanner.nextLine();
        
        String accountNumber = controller.openSavingsAccount(customerId, branch);
        if (accountNumber != null) {
            System.out.println("✓ Savings Account opened! Account Number: " + accountNumber);
        }
    }

    private static void openInvestmentAccount() {
        int customerId = getIntInput("\nEnter Customer ID: ");
        System.out.print("Branch: ");
        String branch = scanner.nextLine();
        double initialDeposit = getDoubleInput("Initial Deposit (min BWP 500): ");
        
        String accountNumber = controller.openInvestmentAccount(customerId, branch, initialDeposit);
        if (accountNumber != null) {
            System.out.println("✓ Investment Account opened! Account Number: " + accountNumber);
        }
    }

    private static void openChequeAccount() {
        int customerId = getIntInput("\nEnter Customer ID: ");
        System.out.print("Branch: ");
        String branch = scanner.nextLine();
        System.out.print("Employer Name: ");
        String employerName = scanner.nextLine();
        System.out.print("Employer Address: ");
        String employerAddress = scanner.nextLine();
        
        String accountNumber = controller.openChequeAccount(customerId, branch, employerName, employerAddress);
        if (accountNumber != null) {
            System.out.println("✓ Cheque Account opened! Account Number: " + accountNumber);
        }
    }

    private static void viewAccountDetails() {
        System.out.print("\nEnter Account Number: ");
        String accountNumber = scanner.nextLine();
        
        Account account = controller.getAccount(accountNumber);
        if (account == null) {
            System.out.println("✗ Account not found!");
            return;
        }
        
        System.out.println("\n" + account.getAccountDetails());
        
        System.out.println("\n--- Recent Transactions ---");
        List<Transaction> transactions = controller.getAccountTransactions(accountNumber);
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    private static void viewAllAccounts() {
        System.out.println("\n=== ALL ACCOUNTS ===");
        List<Account> accounts = controller.getAllAccounts();
        
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        
        for (Account a : accounts) {
            System.out.println(a);
        }
    }

    private static void depositFunds() {
        System.out.print("\nAccount Number: ");
        String accountNumber = scanner.nextLine();
        double amount = getDoubleInput("Amount (BWP): ");
        
        controller.deposit(accountNumber, amount);
    }

    private static void withdrawFunds() {
        System.out.print("\nAccount Number: ");
        String accountNumber = scanner.nextLine();
        double amount = getDoubleInput("Amount (BWP): ");
        
        controller.withdraw(accountNumber, amount);
    }

    private static void viewTransactionHistory() {
        System.out.print("\nAccount Number (or press Enter for all): ");
        String accountNumber = scanner.nextLine();
        
        List<Transaction> transactions;
        if (accountNumber.isEmpty()) {
            transactions = controller.getAllTransactions();
        } else {
            transactions = controller.getAccountTransactions(accountNumber);
        }
        
        System.out.println("\n=== TRANSACTION HISTORY ===");
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    private static void viewAllTransactions() {
        System.out.println("\n=== ALL TRANSACTIONS ===");
        List<Transaction> transactions = controller.getAllTransactions();
        
        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    private static void calculateInterestSingle() {
        System.out.print("\nAccount Number: ");
        String accountNumber = scanner.nextLine();
        
        controller.calculateAndPayInterest(accountNumber);
    }

    private static void calculateInterestAll() {
        System.out.println("\nCalculating interest for all eligible accounts...");
        if (controller.payInterestToAllAccounts()) {
            System.out.println("✓ Interest paid to all eligible accounts!");
        } else {
            System.out.println("✗ Error calculating interest");
        }
    }

    private static int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Invalid input. " + prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private static double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            scanner.next();
            System.out.print("Invalid input. " + prompt);
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }
}