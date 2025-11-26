package main.java;

public class ChequeAccount extends Account {
    private String employerName;
    private String employerAddress;
    private boolean isEmployed;
    
    // Constructor
    public ChequeAccount(String accountNumber, Customer customer, String branch, 
                        String employerName, String employerAddress) {
        super(accountNumber, customer, branch);
        this.employerName = employerName;
        this.employerAddress = employerAddress;
        this.isEmployed = true;
    }
    
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited BWP" + amount + " to Cheque Account " + accountNumber);
            System.out.println("New Balance: BWP" + balance);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }
    
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn BWP" + amount + " from Cheque Account " + accountNumber);
            System.out.println("New Balance: BWP" + balance);
            return true;
        } else {
            System.out.println("Insufficient funds or invalid amount");
            return false;
        }
    }
    
    @Override
    public double calculateInterest() {
        return 0; // No interest on cheque accounts
    }
    
    // Employment-specific getters
    public String getEmployerName() { return employerName; }
    public String getEmployerAddress() { return employerAddress; }
    public boolean isEmployed() { return isEmployed; }
}