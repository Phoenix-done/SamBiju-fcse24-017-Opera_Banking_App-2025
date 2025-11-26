package main.java;

import java.util.Scanner;
import java.util.List;

public class BankingApplication {
    private BankingService bankingService;
    private Scanner scanner;
    
    // Constructor
    public BankingApplication() {
        this.bankingService = new BankingService();
        this.scanner = new Scanner(System.in);
    }
    
    // MAIN METHOD - This starts your program
    public static void main(String[] args) {
        BankingApplication app = new BankingApplication();
        app.run();
    }
    
    // RUN THE APPLICATION
    public void run() {
        System.out.println("🏦 ===== WELCOME TO DYNAMIC BANKING SYSTEM ===== 🏦");
        System.out.println("Your complete banking solution!");
        
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1: createCustomer(); break;
                case 2: openAccount(); break;
                case 3: depositMoney(); break;
                case 4: withdrawMoney(); break;
                case 5: checkBalance(); break;
                case 6: viewCustomerAccounts(); break;
                case 7: calculateInterest(); break;
                case 8: viewAllCustomers(); break;
                case 9: 
                    System.out.println("🙏 Thank you for using Dynamic Banking System!");
                    System.out.println("Have a great day!");
                    return;
                default:
                    System.out.println("❌ Invalid option. Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    // DISPLAY MAIN MENU
    private void displayMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🏦          BANKING SYSTEM MENU          🏦");
        System.out.println("=".repeat(50));
        System.out.println("1. 👤 Create Customer");
        System.out.println("2. 🏦 Open Account");
        System.out.println("3. 💰 Deposit Money");
        System.out.println("4. 💸 Withdraw Money");
        System.out.println("5. 💳 Check Balance");
        System.out.println("6. 📋 View Customer Accounts");
        System.out.println("7. 📈 Calculate Monthly Interest");
        System.out.println("8. 👥 View All Customers");
        System.out.println("9. 🚪 Exit");
        System.out.println("=".repeat(50));
    }
    
    // CREATE NEW CUSTOMER
    private void createCustomer() {
        System.out.println("\n👤 === CREATE NEW CUSTOMER ===");
        
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        try {
            Customer customer = bankingService.createCustomer(firstName, surname, address, phone, email);
            System.out.println("🎉 Customer created successfully!");
            System.out.println("📋 Customer ID: " + customer.getCustomerId());
            System.out.println("📝 Name: " + customer.getFirstName() + " " + customer.getSurname());
        } catch (Exception e) {
            System.out.println("❌ Error creating customer: " + e.getMessage());
        }
    }
    
    // OPEN NEW ACCOUNT
    private void openAccount() {
        System.out.println("\n🏦 === OPEN NEW ACCOUNT ===");
        
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        System.out.print("Enter branch name: ");
        String branch = scanner.nextLine();
        
        System.out.println("\nSelect account type:");
        System.out.println("1. 💰 Savings Account (0.05% monthly interest, no withdrawals)");
        System.out.println("2. 📈 Investment Account (5% monthly interest, BWP500 minimum)");
        System.out.println("3. 💳 Cheque Account (for salary, requires employment info)");
        
        int accountType = getIntInput("Choose account type (1-3): ");
        
        try {
            switch (accountType) {
                case 1:
                    SavingsAccount savingsAccount = bankingService.openSavingsAccount(customerId, branch);
                    System.out.println("🎉 Savings Account created!");
                    System.out.println("📋 Account Number: " + savingsAccount.getAccountNumber());
                    break;
                    
                case 2:
                    double initialDeposit = getDoubleInput("Enter initial deposit (minimum BWP500): ");
                    InvestmentAccount investmentAccount = bankingService.openInvestmentAccount(customerId, branch, initialDeposit);
                    System.out.println("🎉 Investment Account created!");
                    System.out.println("📋 Account Number: " + investmentAccount.getAccountNumber());
                    break;
                    
                case 3:
                    System.out.print("Enter employer name: ");
                    String employerName = scanner.nextLine();
                    System.out.print("Enter employer address: ");
                    String employerAddress = scanner.nextLine();
                    ChequeAccount chequeAccount = bankingService.openChequeAccount(customerId, branch, employerName, employerAddress);
                    System.out.println("🎉 Cheque Account created!");
                    System.out.println("📋 Account Number: " + chequeAccount.getAccountNumber());
                    break;
                    
                default:
                    System.out.println("❌ Invalid account type");
            }
        } catch (Exception e) {
            System.out.println("❌ Error opening account: " + e.getMessage());
        }
    }
    
    // DEPOSIT MONEY
    private void depositMoney() {
        System.out.println("\n💰 === DEPOSIT MONEY ===");
        
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        double amount = getDoubleInput("Enter amount to deposit: BWP");
        
        bankingService.deposit(accountNumber, amount);
    }
    
    // WITHDRAW MONEY
    private void withdrawMoney() {
        System.out.println("\n💸 === WITHDRAW MONEY ===");
        
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        double amount = getDoubleInput("Enter amount to withdraw: BWP");
        
        boolean success = bankingService.withdraw(accountNumber, amount);
        if (success) {
            System.out.println("✅ Withdrawal successful!");
        }
    }
    
    // CHECK ACCOUNT BALANCE
    private void checkBalance() {
        System.out.println("\n💳 === CHECK BALANCE ===");
        
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        
        Account account = bankingService.getAccount(accountNumber);
        if (account != null) {
            System.out.println("💰 Account Balance: BWP" + String.format("%.2f", account.getBalance()));
            System.out.println("🏦 Account Type: " + account.getClass().getSimpleName());
            System.out.println("🏢 Branch: " + account.getBranch());
            System.out.println("👤 Customer: " + account.getCustomer().getFirstName() + " " + 
                             account.getCustomer().getSurname());
        } else {
            System.out.println("❌ Account not found");
        }
    }
    
    // VIEW CUSTOMER ACCOUNTS
    private void viewCustomerAccounts() {
        System.out.println("\n📋 === VIEW CUSTOMER ACCOUNTS ===");
        
        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();
        
        Customer customer = bankingService.getCustomer(customerId);
        if (customer == null) {
            System.out.println("❌ Customer not found");
            return;
        }
        
        List<Account> accounts = bankingService.getCustomerAccounts(customerId);
        if (accounts.isEmpty()) {
            System.out.println("📭 No accounts found for this customer");
        } else {
            System.out.println("👤 Customer: " + customer.getFirstName() + " " + customer.getSurname());
            System.out.println("📋 Customer Accounts:");
            System.out.println("-".repeat(60));
            
            for (Account account : accounts) {
                System.out.printf("🏦 %-15s | %-20s | BWP%.2f%n", 
                    account.getAccountNumber(), 
                    account.getClass().getSimpleName(), 
                    account.getBalance());
            }
        }
    }
    
    // CALCULATE INTEREST
    private void calculateInterest() {
        System.out.println("\n📈 === CALCULATE MONTHLY INTEREST ===");
        bankingService.calculateMonthlyInterest();
    }
    
    // VIEW ALL CUSTOMERS
    private void viewAllCustomers() {
        System.out.println("\n👥 === ALL CUSTOMERS ===");
        bankingService.displayAllCustomers();
    }
    
    // HELPER METHODS FOR INPUT
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid number");
            }
        }
    }
    
    private double getDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("❌ Please enter a valid amount");
            }
        }
    }
}