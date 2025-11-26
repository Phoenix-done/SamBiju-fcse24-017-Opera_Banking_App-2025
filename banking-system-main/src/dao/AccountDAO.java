package dao;

import model.*;
import util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    public boolean createAccount(Account account) {
        String sql = "INSERT INTO Account (account_number, customer_id, account_type, balance, branch, interest_rate, employer_name, employer_address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, account.getAccountNumber());
            pstmt.setInt(2, account.getCustomer().getCustomerId());
            pstmt.setString(3, account.getAccountType());
            pstmt.setDouble(4, account.getBalance());
            pstmt.setString(5, account.getBranch());
            
            if (account instanceof SavingsAccount) {
                pstmt.setDouble(6, ((SavingsAccount) account).getInterestRate());
                pstmt.setNull(7, Types.VARCHAR);
                pstmt.setNull(8, Types.VARCHAR);
            } else if (account instanceof InvestmentAccount) {
                pstmt.setDouble(6, ((InvestmentAccount) account).getInterestRate());
                pstmt.setNull(7, Types.VARCHAR);
                pstmt.setNull(8, Types.VARCHAR);
            } else if (account instanceof ChequeAccount) {
                pstmt.setNull(6, Types.DECIMAL);
                ChequeAccount cheque = (ChequeAccount) account;
                pstmt.setString(7, cheque.getEmployerName());
                pstmt.setString(8, cheque.getEmployerAddress());
            }
            
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Account created: " + account.getAccountNumber());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error creating account: " + e.getMessage());
        }
        return false;
    }

    public Account getAccountByNumber(String accountNumber) {
        String sql = "SELECT a.*, c.* FROM Account a " +
                    "JOIN Customer c ON a.customer_id = c.customer_id " +
                    "WHERE a.account_number = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return extractAccountFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error getting account: " + e.getMessage());
        }
        return null;
    }

    public List<Account> getAccountsByCustomerId(int customerId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT a.*, c.* FROM Account a " +
                    "JOIN Customer c ON a.customer_id = c.customer_id " +
                    "WHERE a.customer_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                accounts.add(extractAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting accounts: " + e.getMessage());
        }
        return accounts;
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT a.*, c.* FROM Account a JOIN Customer c ON a.customer_id = c.customer_id";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                accounts.add(extractAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all accounts: " + e.getMessage());
        }
        return accounts;
    }

    public boolean updateAccountBalance(String accountNumber, double newBalance) {
        String sql = "UPDATE Account SET balance = ? WHERE account_number = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accountNumber);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating balance: " + e.getMessage());
        }
        return false;
    }

    public boolean deleteAccount(String accountNumber) {
        String sql = "DELETE FROM Account WHERE account_number = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, accountNumber);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting account: " + e.getMessage());
        }
        return false;
    }

    private Account extractAccountFromResultSet(ResultSet rs) throws SQLException {
        Customer customer = new Customer(
            rs.getInt("customer_id"),
            rs.getString("firstname"),
            rs.getString("surname"),
            rs.getString("address"),
            rs.getString("phone"),
            rs.getString("email")
        );

        String accountType = rs.getString("account_type");
        String accountNumber = rs.getString("account_number");
        double balance = rs.getDouble("balance");
        String branch = rs.getString("branch");

        Account account = null;

        if ("Savings".equals(accountType)) {
            account = new SavingsAccount(accountNumber, customer, balance, branch);
        } else if ("Investment".equals(accountType)) {
            account = new InvestmentAccount(accountNumber, customer, balance, branch);
        } else if ("Cheque".equals(accountType)) {
            String employerName = rs.getString("employer_name");
            String employerAddress = rs.getString("employer_address");
            account = new ChequeAccount(accountNumber, customer, balance, branch, employerName, employerAddress);
        }

        if (account != null) {
            Timestamp timestamp = rs.getTimestamp("created_date");
            if (timestamp != null) {
                account.setCreatedDate(timestamp.toLocalDateTime());
            }
        }

        return account;
    }
}