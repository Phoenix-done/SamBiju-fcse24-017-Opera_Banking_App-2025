package model;

import java.time.LocalDateTime;

public abstract class Account implements AccountOperations {
    protected String accountNumber;
    protected Customer customer;
    protected double balance;
    protected String branch;
    protected String accountType;
    protected LocalDateTime createdDate;

    public Account(String accountNumber, Customer customer, String branch, String accountType) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.branch = branch;
        this.accountType = accountType;
        this.balance = 0.0;
        this.createdDate = LocalDateTime.now();
    }

    public Account(String accountNumber, Customer customer, double balance, String branch, String accountType) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
        this.branch = branch;
        this.accountType = accountType;
        this.createdDate = LocalDateTime.now();
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Deposited: BWP " + amount);
        }
    }

    @Override
    public abstract boolean withdraw(double amount);

    @Override
    public abstract void calculateInterest();

    @Override
    public String getAccountDetails() {
        return "Account: " + accountNumber + 
               "\nType: " + accountType +
               "\nBalance: BWP " + balance +
               "\nBranch: " + branch;
    }

    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public Customer getCustomer() { return customer; }
    public void setCustomer(Customer customer) { this.customer = customer; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }
    public String getAccountType() { return accountType; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
}