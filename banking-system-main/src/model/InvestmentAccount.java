package model;

public class InvestmentAccount extends Account {
    private static final double INTEREST_RATE = 0.05;
    private static final double MINIMUM_OPENING_BALANCE = 500.00;

    public InvestmentAccount(String accountNumber, Customer customer, String branch, double initialDeposit) {
        super(accountNumber, customer, branch, "Investment");
        if (initialDeposit >= MINIMUM_OPENING_BALANCE) {
            this.balance = initialDeposit;
        } else {
            throw new IllegalArgumentException("Minimum BWP 500 required");
        }
    }

    public InvestmentAccount(String accountNumber, Customer customer, double balance, String branch) {
        super(accountNumber, customer, balance, branch, "Investment");
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0 || amount > balance) {
            System.out.println("Invalid withdrawal");
            return false;
        }
        balance -= amount;
        return true;
    }

    @Override
    public void calculateInterest() {
        double interest = balance * INTEREST_RATE;
        balance += interest;
        System.out.println("Interest: BWP " + interest);
    }

    public double getInterestRate() {
        return INTEREST_RATE;
    }

    public static double getMinimumOpeningBalance() {
        return MINIMUM_OPENING_BALANCE;
    }
}