package main.java;

public class SavingsAccount extends Account {
    private static final double MONTHLY_INTEREST_RATE = 0.0005; // 0.05%
    
    // Constructor
    public SavingsAccount(String accountNumber, Customer customer, String branch) {
        super(accountNumber, customer, branch);
    }
    
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited BWP" + amount + " to Savings Account " + accountNumber);
            System.out.println("New Balance: BWP" + balance);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }
    
    @Override
    public boolean withdraw(double amount) {
        System.out.println("Withdrawals are not allowed on Savings Account");
        return false;
    }
    
    @Override
    public double calculateInterest() {
        return balance * MONTHLY_INTEREST_RATE;
    }
}