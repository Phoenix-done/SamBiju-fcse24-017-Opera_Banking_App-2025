package view;

import controller.BankingController;
import model.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class BankingWebController {
    
    private BankingController bankingController = new BankingController();

    @GetMapping("/")
    public String home(Model model) {
        List<Customer> customers = bankingController.getAllCustomers();
        List<Account> accounts = bankingController.getAllAccounts();
        model.addAttribute("customerCount", customers.size());
        model.addAttribute("accountCount", accounts.size());
        return "index";
    }

    @GetMapping("/customers")
    public String listCustomers(Model model) {
        List<Customer> customers = bankingController.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customers";
    }

    @GetMapping("/customers/new")
    public String newCustomerForm() {
        return "customer-form";
    }

    @PostMapping("/customers/create")
    public String createCustomer(@RequestParam String firstname, 
                                 @RequestParam String surname,
                                 @RequestParam String address,
                                 @RequestParam String phone,
                                 @RequestParam String email) {
        bankingController.registerCustomer(firstname, surname, address, phone, email);
        return "redirect:/customers";
    }

    @GetMapping("/accounts")
    public String listAccounts(Model model) {
        List<Account> accounts = bankingController.getAllAccounts();
        model.addAttribute("accounts", accounts);
        return "accounts";
    }

    @GetMapping("/accounts/new")
    public String newAccountForm(Model model) {
        List<Customer> customers = bankingController.getAllCustomers();
        model.addAttribute("customers", customers);
        return "account-form";
    }

    @PostMapping("/accounts/create-savings")
    public String createSavingsAccount(@RequestParam int customerId,
                                       @RequestParam String branch) {
        bankingController.openSavingsAccount(customerId, branch);
        return "redirect:/accounts";
    }

    @PostMapping("/accounts/create-investment")
    public String createInvestmentAccount(@RequestParam int customerId,
                                          @RequestParam String branch,
                                          @RequestParam double initialDeposit) {
        bankingController.openInvestmentAccount(customerId, branch, initialDeposit);
        return "redirect:/accounts";
    }

    @PostMapping("/accounts/create-cheque")
    public String createChequeAccount(@RequestParam int customerId,
                                      @RequestParam String branch,
                                      @RequestParam String employerName,
                                      @RequestParam String employerAddress) {
        bankingController.openChequeAccount(customerId, branch, employerName, employerAddress);
        return "redirect:/accounts";
    }

    @GetMapping("/accounts/{accountNumber}")
    public String accountDetails(@PathVariable String accountNumber, Model model) {
        Account account = bankingController.getAccount(accountNumber);
        List<Transaction> transactions = bankingController.getAccountTransactions(accountNumber);
        model.addAttribute("account", account);
        model.addAttribute("transactions", transactions);
        return "account-details";
    }

    @PostMapping("/accounts/{accountNumber}/deposit")
    public String deposit(@PathVariable String accountNumber,
                         @RequestParam double amount) {
        bankingController.deposit(accountNumber, amount);
        return "redirect:/accounts/" + accountNumber;
    }

    @PostMapping("/accounts/{accountNumber}/withdraw")
    public String withdraw(@PathVariable String accountNumber,
                          @RequestParam double amount) {
        bankingController.withdraw(accountNumber, amount);
        return "redirect:/accounts/" + accountNumber;
    }

    @PostMapping("/accounts/{accountNumber}/interest")
    public String calculateInterest(@PathVariable String accountNumber) {
        bankingController.calculateAndPayInterest(accountNumber);
        return "redirect:/accounts/" + accountNumber;
    }

    @PostMapping("/accounts/pay-all-interest")
    public String payAllInterest() {
        bankingController.payInterestToAllAccounts();
        return "redirect:/accounts";
    }

    @GetMapping("/transactions")
    public String listTransactions(Model model) {
        List<Transaction> transactions = bankingController.getAllTransactions();
        model.addAttribute("transactions", transactions);
        return "transactions";
    }
}