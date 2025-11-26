package main.java;

public class InvestmentAccount extends Account {
    private static final double MONTHLY_INTEREST_RATE = 0.05; // 5%
    private static final double MINIMUM_OPENING_BALANCE = 500.0;
    
    // Constructor
    public InvestmentAccount(String accountNumber, Customer customer, String branch, double initialDeposit) {
        super(accountNumber, customer, branch);
        if (initialDeposit >= MINIMUM_OPENING_BALANCE) {
            this.balance = initialDeposit;
            System.out.println("Investment Account opened with BWP" + initialDeposit);
        } else {
            throw new IllegalArgumentException("Investment Account requires minimum BWP500 opening balance");
        }
    }
    
    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited BWP" + amount + " to Investment Account " + accountNumber);
            System.out.println("New Balance: BWP" + balance);
        } else {
            System.out.println("Invalid deposit amount");
        }
    }
    
    @Override
    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn BWP" + amount + " from Investment Account " + accountNumber);
            System.out.println("New Balance: BWP" + balance);
            return true;
        } else {
            System.out.println("Insufficient funds or invalid amount");
            return false;
        }
    }
    
    @Override
    public double calculateInterest() {
        return balance * MONTHLY_INTEREST_RATE;
    }
}
