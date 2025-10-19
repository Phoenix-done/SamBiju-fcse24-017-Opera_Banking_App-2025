public class Accounts {
    

    private int accountId;
    private int accountNumber;
    private double balance;
    private String date;
    private String status;
    private int ownerId;
    private String accountType;
    
    public void deposit (double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public double getBalance()  {
        return balance;
    }

    
    public void setAccountType() {
        return accountType;
    }

    

}

