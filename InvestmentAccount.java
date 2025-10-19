public class InvestmentAccount extends Accounts {
    private double monthlyRate;
    private double minimalDeposit;

    public void applyMonthlyRate()  {
        balance += balance * monthlyRate;
    }
}
