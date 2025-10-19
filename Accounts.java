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

    public int getAccountId() {
    return accountId;
}

public void setAccountId(int accountId) {
    this.accountId = accountId;
}

public int getAccountNumber() {
    return accountNumber;
}

public void setAccountNumber(int accountNumber) {
    this.accountNumber = accountNumber;
}

public double getBalance() {
    return balance;
}

public void setBalance(double balance) {
    this.balance = balance;
}

public String getDate() {
    return date;
}

public void setDate(String date) {
    this.date = date;
}

public String getStatus() {
    return status;
}

public void setStatus(String status) {
    this.status = status;
}

public int getOwnerId() {
    return ownerId;
}

public void setOwnerId(int ownerId) {
    this.ownerId = ownerId;
}

public String getAccountType() {
    return accountType;
}

public void setAccountType(String accountType) {
    this.accountType = accountType;
}


    

}

