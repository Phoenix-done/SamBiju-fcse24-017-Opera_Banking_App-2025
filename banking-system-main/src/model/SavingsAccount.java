package model;

public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 0.0005;

    public SavingsAccount(String accountNumber, Customer customer, String branch) {
        super(accountNumber, customer, branch, "Savings");
    }

    public SavingsAccount(String accountNumber, Customer customer, double balance, String branch) {
        super(accountNumber, customer, balance, branch, "Savings");
    }

    @Override
    public boolean withdraw(double amount) {
        System.out.println("Withdrawals not allowed on Savings Accounts");
        return false;
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
}