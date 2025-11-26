package main.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BankingService {
    // Storage for customers and accounts
    private Map<String, Customer> customers;
    private Map<String, Account> accounts;
    private int customerCounter = 1000;
    private int accountCounter = 10000;
    
    // Constructor
    public BankingService() {
        this.customers = new HashMap<>();
        this.accounts = new HashMap<>();
    }
    
    // CREATE CUSTOMER
    public Customer createCustomer(String firstName, String surname, String address, 
                                 String phoneNumber, String email) {
        String customerId = "CUST" + (++customerCounter);
        Customer customer = new Customer(customerId, firstName, surname, address);
        customer.setPhoneNumber(phoneNumber);
        customer.setEmail(email);
        customers.put(customerId, customer);
        System.out.println("✅ Customer created successfully!");
        return customer;
    }
    
    // OPEN SAVINGS ACCOUNT
    public SavingsAccount openSavingsAccount(String customerId, String branch) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("❌ Customer not found");
        }
        
        String accountNumber = "SAV" + (++accountCounter);
        SavingsAccount account = new SavingsAccount(accountNumber, customer, branch);
        accounts.put(accountNumber, account);
        customer.addAccount(account);
        System.out.println("✅ Savings Account opened successfully!");
        return account;
    }
    
    // OPEN INVESTMENT ACCOUNT
    public InvestmentAccount openInvestmentAccount(String customerId, String branch, double initialDeposit) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("❌ Customer not found");
        }
        
        String accountNumber = "INV" + (++accountCounter);
        InvestmentAccount account = new InvestmentAccount(accountNumber, customer, branch, initialDeposit);
        accounts.put(accountNumber, account);
        customer.addAccount(account);
        System.out.println("✅ Investment Account opened successfully!");
        return account;
    }
    
    // OPEN CHEQUE ACCOUNT
    public ChequeAccount openChequeAccount(String customerId, String branch, 
                                         String employerName, String employerAddress) {
        Customer customer = customers.get(customerId);
        if (customer == null) {
            throw new IllegalArgumentException("❌ Customer not found");
        }
        
        String accountNumber = "CHQ" + (++accountCounter);
        ChequeAccount account = new ChequeAccount(accountNumber, customer, branch, employerName, employerAddress);
        accounts.put(accountNumber, account);
        customer.addAccount(account);
        System.out.println("✅ Cheque Account opened successfully!");
        return account;
    }
    
    // DEPOSIT MONEY
    public void deposit(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.deposit(amount);
        } else {
            throw new IllegalArgumentException("❌ Account not found");
        }
    }
    
    // WITHDRAW MONEY
    public boolean withdraw(String accountNumber, double amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            return account.withdraw(amount);
        }
        throw new IllegalArgumentException("❌ Account not found");
    }
    
    // CALCULATE MONTHLY INTEREST FOR ALL ACCOUNTS
    public void calculateMonthlyInterest() {
        System.out.println("💰 Calculating monthly interest...");
        for (Account account : accounts.values()) {
            double interest = account.calculateInterest();
            if (interest > 0) {
                account.deposit(interest);
                System.out.println("Interest BWP" + String.format("%.2f", interest) + 
                                 " added to account " + account.getAccountNumber());
            }
        }
        System.out.println("✅ Interest calculation completed!");
    }
    
    // GET ACCOUNT INFORMATION
    public Account getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
    
    // GET CUSTOMER INFORMATION
    public Customer getCustomer(String customerId) {
        return customers.get(customerId);
    }
    
    // GET ALL ACCOUNTS FOR A CUSTOMER
    public List<Account> getCustomerAccounts(String customerId) {
        Customer customer = customers.get(customerId);
        return customer != null ? customer.getAccounts() : new ArrayList<>();
    }
    
    // GET ALL CUSTOMERS
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    // DISPLAY ALL CUSTOMERS (for testing)
    public void displayAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No customers found");
            return;
        }

        System.out.println("\n=== ALL CUSTOMERS ===");
        for (Customer customer : customers.values()) {
            System.out.println("ID: " + customer.getCustomerId() +
                             " | Name: " + customer.getFirstName() + " " + customer.getSurname() +
                             " | Accounts: " + customer.getAccounts().size());
        }
    }
}