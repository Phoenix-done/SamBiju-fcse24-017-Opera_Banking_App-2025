package main.java;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class BankingGUI extends JFrame {
    private BankingService bankingService;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    
    // Color scheme
    private final Color PRIMARY_COLOR = new Color(41, 128, 185);
    private final Color SECONDARY_COLOR = new Color(52, 152, 219);
    private final Color SUCCESS_COLOR = new Color(46, 204, 113);
    private final Color DANGER_COLOR = new Color(231, 76, 60);
    private final Color BACKGROUND_COLOR = new Color(236, 240, 241);
    private final Color CARD_COLOR = Color.WHITE;
    
    public BankingGUI() {
        this.bankingService = new BankingService();
        initializeUI();
    }
    
    private void initializeUI() {
        setTitle("🏦 Dynamic Banking System");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Main layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(BACKGROUND_COLOR);
        
        // Add screens
        mainPanel.add(createHomeScreen(), "HOME");
        mainPanel.add(createCustomerScreen(), "CUSTOMER");
        mainPanel.add(createAccountScreen(), "ACCOUNT");
        mainPanel.add(createTransactionScreen(), "TRANSACTION");
        mainPanel.add(createViewScreen(), "VIEW");
        
        add(mainPanel);
        setVisible(true);
    }
    
    // HOME SCREEN
    private JPanel createHomeScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        // Header
        JPanel header = createHeader("Welcome to Dynamic Banking System");
        panel.add(header, BorderLayout.NORTH);
        
        // Main content
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;
        
        // Menu buttons
        String[][] menuItems = {
            {"👤 Create Customer", "CUSTOMER", "Register a new customer"},
            {"🏦 Open Account", "ACCOUNT", "Open a new bank account"},
            {"💰 Transactions", "TRANSACTION", "Deposit or Withdraw money"},
            {"📋 View Details", "VIEW", "View accounts and customers"}
        };
        
        int row = 0;
        for (String[] item : menuItems) {
            JButton btn = createMenuButton(item[0], item[2]);
            btn.addActionListener(e -> cardLayout.show(mainPanel, item[1]));
            gbc.gridx = row % 2;
            gbc.gridy = row / 2;
            content.add(btn, gbc);
            row++;
        }
        
        panel.add(content, BorderLayout.CENTER);
        
        // Footer
        JLabel footer = new JLabel("Your Complete Banking Solution", SwingConstants.CENTER);
        footer.setFont(new Font("Arial", Font.ITALIC, 12));
        footer.setBorder(new EmptyBorder(10, 0, 10, 0));
        panel.add(footer, BorderLayout.SOUTH);
        
        return panel;
    }
    
    // CREATE CUSTOMER SCREEN
    private JPanel createCustomerScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        panel.add(createHeader("Create New Customer"), BorderLayout.NORTH);
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(CARD_COLOR);
        form.setBorder(new CompoundBorder(
            new EmptyBorder(20, 20, 20, 20),
            new LineBorder(PRIMARY_COLOR, 2)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Form fields
        JTextField firstNameField = new JTextField(20);
        JTextField surnameField = new JTextField(20);
        JTextField addressField = new JTextField(20);
        JTextField phoneField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        
        addFormField(form, gbc, 0, "First Name:", firstNameField);
        addFormField(form, gbc, 1, "Surname:", surnameField);
        addFormField(form, gbc, 2, "Address:", addressField);
        addFormField(form, gbc, 3, "Phone:", phoneField);
        addFormField(form, gbc, 4, "Email:", emailField);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(CARD_COLOR);
        
        JButton createBtn = createStyledButton("Create Customer", SUCCESS_COLOR);
        JButton backBtn = createStyledButton("Back to Home", PRIMARY_COLOR);
        
        createBtn.addActionListener(e -> {
            try {
                Customer customer = bankingService.createCustomer(
                    firstNameField.getText(),
                    surnameField.getText(),
                    addressField.getText(),
                    phoneField.getText(),
                    emailField.getText()
                );
                showMessage("Success!", "Customer created!\nID: " + customer.getCustomerId(), 
                           JOptionPane.INFORMATION_MESSAGE);
                clearFields(firstNameField, surnameField, addressField, phoneField, emailField);
            } catch (Exception ex) {
                showMessage("Error", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        });
        
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        
        buttonPanel.add(createBtn);
        buttonPanel.add(backBtn);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        form.add(buttonPanel, gbc);
        
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BACKGROUND_COLOR);
        wrapper.add(form);
        
        panel.add(wrapper, BorderLayout.CENTER);
        return panel;
    }
    
    // OPEN ACCOUNT SCREEN
    private JPanel createAccountScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        panel.add(createHeader("Open New Account"), BorderLayout.NORTH);
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(CARD_COLOR);
        form.setBorder(new CompoundBorder(
            new EmptyBorder(20, 20, 20, 20),
            new LineBorder(PRIMARY_COLOR, 2)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JTextField customerIdField = new JTextField(20);
        JTextField branchField = new JTextField(20);
        
        addFormField(form, gbc, 0, "Customer ID:", customerIdField);
        addFormField(form, gbc, 1, "Branch:", branchField);
        
        // Account type selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        form.add(new JLabel("Account Type:"), gbc);
        
        String[] accountTypes = {"Savings Account", "Investment Account", "Cheque Account"};
        JComboBox<String> accountTypeCombo = new JComboBox<>(accountTypes);
        gbc.gridx = 1;
        form.add(accountTypeCombo, gbc);
        
        // Additional fields panel (dynamic)
        JPanel additionalPanel = new JPanel(new GridBagLayout());
        additionalPanel.setBackground(CARD_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        form.add(additionalPanel, gbc);
        
        JTextField initialDepositField = new JTextField(20);
        JTextField employerNameField = new JTextField(20);
        JTextField employerAddressField = new JTextField(20);
        
        accountTypeCombo.addActionListener(e -> {
            additionalPanel.removeAll();
            GridBagConstraints agbc = new GridBagConstraints();
            agbc.insets = new Insets(5, 10, 5, 10);
            agbc.fill = GridBagConstraints.HORIZONTAL;
            
            if (accountTypeCombo.getSelectedIndex() == 1) { // Investment
                addFormField(additionalPanel, agbc, 0, "Initial Deposit (min BWP500):", initialDepositField);
            } else if (accountTypeCombo.getSelectedIndex() == 2) { // Cheque
                addFormField(additionalPanel, agbc, 0, "Employer Name:", employerNameField);
                addFormField(additionalPanel, agbc, 1, "Employer Address:", employerAddressField);
            }
            additionalPanel.revalidate();
            additionalPanel.repaint();
        });
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(CARD_COLOR);
        
        JButton openBtn = createStyledButton("Open Account", SUCCESS_COLOR);
        JButton backBtn = createStyledButton("Back to Home", PRIMARY_COLOR);
        
        openBtn.addActionListener(e -> {
            try {
                String customerId = customerIdField.getText();
                String branch = branchField.getText();
                Account account = null;
                
                switch (accountTypeCombo.getSelectedIndex()) {
                    case 0: // Savings
                        account = bankingService.openSavingsAccount(customerId, branch);
                        break;
                    case 1: // Investment
                        double deposit = Double.parseDouble(initialDepositField.getText());
                        account = bankingService.openInvestmentAccount(customerId, branch, deposit);
                        break;
                    case 2: // Cheque
                        account = bankingService.openChequeAccount(customerId, branch,
                            employerNameField.getText(), employerAddressField.getText());
                        break;
                }
                
                showMessage("Success!", "Account opened!\nAccount Number: " + account.getAccountNumber(),
                           JOptionPane.INFORMATION_MESSAGE);
                clearFields(customerIdField, branchField, initialDepositField, 
                           employerNameField, employerAddressField);
            } catch (Exception ex) {
                showMessage("Error", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        });
        
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        
        buttonPanel.add(openBtn);
        buttonPanel.add(backBtn);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        form.add(buttonPanel, gbc);
        
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BACKGROUND_COLOR);
        wrapper.add(form);
        
        panel.add(wrapper, BorderLayout.CENTER);
        return panel;
    }
    
    // TRANSACTION SCREEN
    private JPanel createTransactionScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        panel.add(createHeader("Banking Transactions"), BorderLayout.NORTH);
        
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(CARD_COLOR);
        form.setBorder(new CompoundBorder(
            new EmptyBorder(20, 20, 20, 20),
            new LineBorder(PRIMARY_COLOR, 2)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JTextField accountNumberField = new JTextField(20);
        JTextField amountField = new JTextField(20);
        
        addFormField(form, gbc, 0, "Account Number:", accountNumberField);
        addFormField(form, gbc, 1, "Amount (BWP):", amountField);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(CARD_COLOR);
        
        JButton depositBtn = createStyledButton("💰 Deposit", SUCCESS_COLOR);
        JButton withdrawBtn = createStyledButton("💸 Withdraw", DANGER_COLOR);
        JButton balanceBtn = createStyledButton("💳 Check Balance", PRIMARY_COLOR);
        JButton backBtn = createStyledButton("Back to Home", PRIMARY_COLOR);
        
        depositBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                bankingService.deposit(accountNumberField.getText(), amount);
                showMessage("Success!", "Deposit successful!", JOptionPane.INFORMATION_MESSAGE);
                amountField.setText("");
            } catch (Exception ex) {
                showMessage("Error", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        });
        
        withdrawBtn.addActionListener(e -> {
            try {
                double amount = Double.parseDouble(amountField.getText());
                boolean success = bankingService.withdraw(accountNumberField.getText(), amount);
                if (success) {
                    showMessage("Success!", "Withdrawal successful!", JOptionPane.INFORMATION_MESSAGE);
                    amountField.setText("");
                } else {
                    showMessage("Error", "Insufficient funds!", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                showMessage("Error", ex.getMessage(), JOptionPane.ERROR_MESSAGE);
            }
        });
        
        balanceBtn.addActionListener(e -> {
            Account account = bankingService.getAccount(accountNumberField.getText());
            if (account != null) {
                String message = String.format(
                    "Account: %s\nType: %s\nBalance: BWP %.2f\nBranch: %s\nCustomer: %s %s",
                    account.getAccountNumber(),
                    account.getClass().getSimpleName(),
                    account.getBalance(),
                    account.getBranch(),
                    account.getCustomer().getFirstName(),
                    account.getCustomer().getSurname()
                );
                showMessage("Account Details", message, JOptionPane.INFORMATION_MESSAGE);
            } else {
                showMessage("Error", "Account not found!", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        
        buttonPanel.add(depositBtn);
        buttonPanel.add(withdrawBtn);
        buttonPanel.add(balanceBtn);
        buttonPanel.add(backBtn);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        form.add(buttonPanel, gbc);
        
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(BACKGROUND_COLOR);
        wrapper.add(form);
        
        panel.add(wrapper, BorderLayout.CENTER);
        return panel;
    }
    
    // VIEW SCREEN
    private JPanel createViewScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BACKGROUND_COLOR);
        
        panel.add(createHeader("View Details"), BorderLayout.NORTH);
        
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        JButton viewCustomerAccountsBtn = createStyledButton("View Customer Accounts", PRIMARY_COLOR);
        JButton viewAllCustomersBtn = createStyledButton("View All Customers", SECONDARY_COLOR);
        JButton calculateInterestBtn = createStyledButton("Calculate Interest", SUCCESS_COLOR);
        JButton backBtn = createStyledButton("Back to Home", PRIMARY_COLOR);
        
        viewCustomerAccountsBtn.addActionListener(e -> {
            String customerId = JOptionPane.showInputDialog(this, "Enter Customer ID:");
            if (customerId != null && !customerId.isEmpty()) {
                Customer customer = bankingService.getCustomer(customerId);
                if (customer != null) {
                    List<Account> accounts = bankingService.getCustomerAccounts(customerId);
                    StringBuilder sb = new StringBuilder();
                    sb.append(String.format("Customer: %s %s\n\n", 
                        customer.getFirstName(), customer.getSurname()));
                    
                    if (accounts.isEmpty()) {
                        sb.append("No accounts found.");
                    } else {
                        for (Account acc : accounts) {
                            sb.append(String.format("Account: %s\nType: %s\nBalance: BWP %.2f\n\n",
                                acc.getAccountNumber(),
                                acc.getClass().getSimpleName(),
                                acc.getBalance()));
                        }
                    }
                    showMessage("Customer Accounts", sb.toString(), JOptionPane.INFORMATION_MESSAGE);
                } else {
                    showMessage("Error", "Customer not found!", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        viewAllCustomersBtn.addActionListener(e -> {
            List<Customer> customers = bankingService.getAllCustomers();
            if (customers.isEmpty()) {
                showMessage("All Customers", "No customers found.", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder("All Customers:\n\n");
                for (Customer customer : customers) {
                    sb.append(String.format("ID: %s | Name: %s %s | Accounts: %d\n",
                        customer.getCustomerId(),
                        customer.getFirstName(),
                        customer.getSurname(),
                        bankingService.getCustomerAccounts(customer.getCustomerId()).size()));
                }
                showMessage("All Customers", sb.toString(), JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        calculateInterestBtn.addActionListener(e -> {
            bankingService.calculateMonthlyInterest();
            showMessage("Success!", "Interest calculated and added to accounts!", 
                       JOptionPane.INFORMATION_MESSAGE);
        });
        
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "HOME"));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(viewCustomerAccountsBtn, gbc);
        gbc.gridy = 1;
        content.add(viewAllCustomersBtn, gbc);
        gbc.gridy = 2;
        content.add(calculateInterestBtn, gbc);
        gbc.gridy = 3;
        content.add(backBtn, gbc);
        
        panel.add(content, BorderLayout.CENTER);
        return panel;
    }
    
    // UTILITY METHODS
    private JPanel createHeader(String title) {
        JPanel header = new JPanel();
        header.setBackground(PRIMARY_COLOR);
        header.setBorder(new EmptyBorder(20, 10, 20, 10));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        header.add(titleLabel);
        
        return header;
    }
    
    private JButton createMenuButton(String text, String description) {
        JButton button = new JButton("<html><center>" + text + "<br><small>" + description + "</small></center></html>");
        button.setPreferredSize(new Dimension(250, 100));
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(SECONDARY_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
        
        return button;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 40));
        
        return button;
    }
    
    private void addFormField(JPanel panel, GridBagConstraints gbc, int row, 
                             String label, JTextField field) {
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(lbl, gbc);
        
        gbc.gridx = 1;
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(field, gbc);
    }
    
    private void showMessage(String title, String message, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }
    
    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }
    
    // MAIN METHOD
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new BankingGUI();
        });
    }
}