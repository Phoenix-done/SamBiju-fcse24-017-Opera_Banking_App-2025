package main.java;

import java.util.Date;

public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String branch;
    protected Customer customer;
    protected Date dateOpened;
    
    // Constructor
    public Account(String accountNumber, Customer customer, String branch) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.branch = branch;
        this.balance = 0.0;
        this.dateOpened = new Date();
    }
    
    // Abstract methods - each account type will implement these differently
    public abstract void deposit(double amount);
    public abstract boolean withdraw(double amount);
    public abstract double calculateInterest();
    
    // Getters and Setters
    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public Customer getCustomer() { return customer; }
    public String getBranch() { return branch; }
    public Date getDateOpened() { return dateOpened; }
    
    protected void setBalance(double balance) { this.balance = balance; }
}