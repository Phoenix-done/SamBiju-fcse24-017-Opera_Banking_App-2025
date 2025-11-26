package controller;

import dao.*;
import model.*;
import util.AccountNumberGenerator;
import java.util.List;

public class BankingController {
    private CustomerDAO customerDAO;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    public BankingController() {
        this.customerDAO = new CustomerDAO();
        this.accountDAO = new AccountDAO();
        this.transactionDAO = new TransactionDAO();
    }

    // Customer Management
    public boolean registerCustomer(String firstname, String surname, String address, String phone, String email) {
        Customer customer = new Customer(firstname, surname, address, phone, email);
        return customerDAO.createCustomer(customer);
    }

    public Customer getCustomer(int customerId) {
        return customerDAO.getCustomerById(customerId);
    }

    public List<Customer> getAllCustomers() {
        return customerDAO.getAllCustomers();
    }

    public List<Customer> searchCustomers(String searchTerm) {
        return customerDAO.searchCustomersByName(searchTerm);
    }

    public boolean updateCustomer(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    // Account Management
    public String openSavingsAccount(int customerId, String branch) {
        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found");
            return null;
        }

        String accountNumber = AccountNumberGenerator.generateAccountNumber("SAVINGS");
        SavingsAccount account = new SavingsAccount(accountNumber, customer, branch);
        
        if (accountDAO.createAccount(account)) {
            return accountNumber;
        }
        return null;
    }

    public String openInvestmentAccount(int customerId, String branch, double initialDeposit) {
        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found");
            return null;
        }

        if (initialDeposit < 500.00) {
            System.out.println("Investment Account requires minimum BWP 500");
            return null;
        }

        String accountNumber = AccountNumberGenerator.generateAccountNumber("INVESTMENT");
        InvestmentAccount account = new InvestmentAccount(accountNumber, customer, branch, initialDeposit);
        
        if (accountDAO.createAccount(account)) {
            Transaction transaction = new Transaction(accountNumber, "DEPOSIT", initialDeposit, "Initial deposit");
            transactionDAO.createTransaction(transaction);
            return accountNumber;
        }
        return null;
    }

    public String openChequeAccount(int customerId, String branch, String employerName, String employerAddress) {
        Customer customer = customerDAO.getCustomerById(customerId);
        if (customer == null) {
            System.out.println("Customer not found");
            return null;
        }

        String accountNumber = AccountNumberGenerator.generateAccountNumber("CHEQUE");
        ChequeAccount account = new ChequeAccount(accountNumber, customer, branch, employerName, employerAddress);
        
        if (accountDAO.createAccount(account)) {
            return accountNumber;
        }
        return null;
    }

    public Account getAccount(String accountNumber) {
        return accountDAO.getAccountByNumber(accountNumber);
    }

    public List<Account> getCustomerAccounts(int customerId) {
        return accountDAO.getAccountsByCustomerId(customerId);
    }

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    // Transaction Operations
    public boolean deposit(String accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount");
            return false;
        }

        Account account = accountDAO.getAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found");
            return false;
        }

        account.deposit(amount);
        
        if (accountDAO.updateAccountBalance(accountNumber, account.getBalance())) {
            Transaction transaction = new Transaction(accountNumber, "DEPOSIT", amount, "Deposit");
            transactionDAO.createTransaction(transaction);
            System.out.println("Deposit successful. New balance: BWP " + account.getBalance());
            return true;
        }
        return false;
    }

    public boolean withdraw(String accountNumber, double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount");
            return false;
        }

        Account account = accountDAO.getAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found");
            return false;
        }

        if (account.withdraw(amount)) {
            if (accountDAO.updateAccountBalance(accountNumber, account.getBalance())) {
                Transaction transaction = new Transaction(accountNumber, "WITHDRAWAL", amount, "Withdrawal");
                transactionDAO.createTransaction(transaction);
                System.out.println("Withdrawal successful. New balance: BWP " + account.getBalance());
                return true;
            }
        }
        return false;
    }

    public boolean calculateAndPayInterest(String accountNumber) {
        Account account = accountDAO.getAccountByNumber(accountNumber);
        if (account == null) {
            System.out.println("Account not found");
            return false;
        }

        double oldBalance = account.getBalance();
        account.calculateInterest();
        double newBalance = account.getBalance();
        double interest = newBalance - oldBalance;

        if (interest > 0) {
            if (accountDAO.updateAccountBalance(accountNumber, newBalance)) {
                Transaction transaction = new Transaction(accountNumber, "INTEREST", interest, "Monthly interest payment");
                transactionDAO.createTransaction(transaction);
                System.out.println("Interest paid: BWP " + interest);
                return true;
            }
        }
        return false;
    }

    public boolean payInterestToAllAccounts() {
        List<Account> accounts = accountDAO.getAllAccounts();
        boolean success = true;

        for (Account account : accounts) {
            if (account instanceof SavingsAccount || account instanceof InvestmentAccount) {
                if (!calculateAndPayInterest(account.getAccountNumber())) {
                    success = false;
                }
            }
        }
        return success;
    }

    public List<Transaction> getAccountTransactions(String accountNumber) {
        return transactionDAO.getTransactionsByAccountNumber(accountNumber);
    }

    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }

    public double getAccountBalance(String accountNumber) {
        Account account = accountDAO.getAccountByNumber(accountNumber);
        return account != null ? account.getBalance() : 0.0;
    }
}