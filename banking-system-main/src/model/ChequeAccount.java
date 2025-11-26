package model;

public class ChequeAccount extends Account {
    private String employerName;
    private String employerAddress;

    public ChequeAccount(String accountNumber, Customer customer, String branch, 
                        String employerName, String employerAddress) {
        super(accountNumber, customer, branch, "Cheque");
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    public ChequeAccount(String accountNumber, Customer customer, double balance, String branch,
                        String employerName, String employerAddress) {
        super(accountNumber, customer, balance, branch, "Cheque");
        this.employerName = employerName;
        this.employerAddress = employerAddress;
    }

    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount");
            return false;
        }
        
        if (amount > balance) {
            System.out.println("Insufficient funds. Balance: BWP " + balance);
            return false;
        }

        balance -= amount;
        System.out.println("Withdrawn: BWP " + amount);
        return true;
    }

    @Override
    public void calculateInterest() {
        System.out.println("Cheque Account does not earn interest");
    }

    @Override
    public String getAccountDetails() {
        return super.getAccountDetails() + 
               "\nEmployer: " + employerName +
               "\nEmployer Address: " + employerAddress;
    }

    public String getEmployerName() { return employerName; }
    public void setEmployerName(String employerName) { this.employerName = employerName; }
    public String getEmployerAddress() { return employerAddress; }
    public void setEmployerAddress(String employerAddress) { this.employerAddress = employerAddress; }
}