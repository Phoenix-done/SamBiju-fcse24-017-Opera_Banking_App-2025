package view;

import controller.BankingController;
import model.*;
import java.util.List;
import java.util.Scanner;

public class ConsoleView {
    private BankingController controller;
    private Scanner scanner;

    public ConsoleView() {
        this.controller = new BankingController();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("========================================");
        System.out.println("   WELCOME TO BANKING SYSTEM");
        System.out.println("========================================");

        while (true) {
            displayMainMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    registerNewCustomer();
                    break;
                case 2:
                    openAccount();
                    break;
                case 3:
                    depositFunds();
                    break;
                case 4:
                    withdrawFunds();
                    break;
                case 5:
                    calculateInterest();
                    break;
                case 6:
                    viewAccountDetails();
                    break;
                case 7:
                    viewAllCustomers();
                    break;
                case 8:
                    viewAllAccounts();
                    break;
                case 9:
                    viewTransactionHistory();
                    break;
                case 0:
                    System.out.println("Thank you for using Banking System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("MAIN MENU");
        System.out.println("========================================");
        System.out.println("1. Register New Customer");
        System.out.println("2. Open Account");
        System.out.println("3. Deposit Funds");
        System.out.println("4. Withdraw Funds");
        System.out.println("5. Calculate Interest");
        System.out.println("6. View Account Details");
        System.out.println("7. View All Customers");
        System.out.println("8. View All Accounts");
        System.out.println("9. View Transaction History");
        System.out.println("0. Exit");
        System.out.println("========================================");
    }

    private void registerNewCustomer() {
        System.out.println("\n--- Register New Customer ---");
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
            System.out.println("Customer registered successfully!");
        } else {
            System.out.println("Failed to register customer.");
        }
    }

    private void openAccount() {
        System.out.println("\n--- Open Account ---");
        int customerId = getIntInput("Enter Customer ID: ");
        
        System.out.println("Select Account Type:");
        System.out.println("1. Savings Account");
        System.out.println("2. Investment Account");
        System.out.println("3. Cheque Account");
        int accountType = getIntInput("Choice: ");

        System.out.print("Branch: ");
        String branch = scanner.nextLine();

        String accountNumber = null;

        switch (accountType) {
            case 1:
                accountNumber = controller.openSavingsAccount(customerId, branch);
                break;
            case 2:
                double initialDeposit = getDoubleInput("Initial Deposit (min BWP 500): ");
                accountNumber = controller.openInvestmentAccount(customerId, branch, initialDeposit);
                break;
            case 3:
                System.out.print("Employer Name: ");
                String employerName = scanner.nextLine();
                System.out.print("Employer Address: ");
                String employerAddress = scanner.nextLine();
                accountNumber = controller.openChequeAccount(customerId, branch, employerName, employerAddress);
                break;
        }

        if (accountNumber != null) {
            System.out.println("Account opened successfully! Account Number: " + accountNumber);
        } else {
            System.out.println("Failed to open account.");
        }
    }

    private void depositFunds() {
        System.out.println("\n--- Deposit Funds ---");
        System.out.print("Account Number: ");
        String accountNumber = scanner.nextLine();
        double amount = getDoubleInput("Amount: BWP ");
        controller.deposit(accountNumber, amount);
    }

    private void withdrawFunds() {
        System.out.println("\n--- Withdraw Funds ---");
        System.out.print("Account Number: ");
        String accountNumber = scanner.nextLine();
        double amount = getDoubleInput("Amount: BWP ");
        controller.withdraw(accountNumber, amount);
    }

    private void calculateInterest() {
        System.out.println("\n--- Calculate Interest ---");
        System.out.println("1. Calculate for specific account");
        System.out.println("2. Calculate for all accounts");
        int choice = getIntInput("Choice: ");

        if (choice == 1) {
            System.out.print("Account Number: ");
            String accountNumber = scanner.nextLine();
            controller.calculateAndPayInterest(accountNumber);
        } else if (choice == 2) {
            controller.payInterestToAllAccounts();
            System.out.println("Interest calculated for all eligible accounts");
        }
    }

    private void viewAccountDetails() {
        System.out.println("\n--- Account Details ---");
        System.out.print("Account Number: ");
        String accountNumber = scanner.nextLine();
        
        Account account = controller.getAccount(accountNumber);
        if (account != null) {
            System.out.println("\n" + account.getAccountDetails());
            System.out.println("\nRecent Transactions:");
            List<Transaction> transactions = controller.getAccountTransactions(accountNumber);
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        } else {
            System.out.println("Account not found");
        }
    }

    private void viewAllCustomers() {
        System.out.println("\n--- All Customers ---");
        List<Customer> customers = controller.getAllCustomers();
        for (Customer c : customers) {
            System.out.println(c);
        }
    }

    private void viewAllAccounts() {
        System.out.println("\n--- All Accounts ---");
        List<Account> accounts = controller.getAllAccounts();
        for (Account a : accounts) {
            System.out.println(a);
        }
    }

    private void viewTransactionHistory() {
        System.out.println("\n--- Transaction History ---");
        System.out.print("Account Number (or press Enter for all): ");
        String accountNumber = scanner.nextLine();
        
        List<Transaction> transactions;
        if (accountNumber.isEmpty()) {
            transactions = controller.getAllTransactions();
        } else {
            transactions = controller.getAccountTransactions(accountNumber);
        }

        for (Transaction t : transactions) {
            System.out.println(t);
        }
    }

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Invalid input. " + prompt);
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextDouble()) {
            scanner.next();
            System.out.print("Invalid input. " + prompt);
        }
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        view.start();
    }
}