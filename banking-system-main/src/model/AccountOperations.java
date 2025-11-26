package model;

public interface AccountOperations {
    void deposit(double amount);
    boolean withdraw(double amount);
    void calculateInterest();
    String getAccountDetails();
}