
public class SavingsAccount extends Accounts {

    double monthlyRate;
    int deposit;

    public void applyMonthlyRate()  {
        balance += balance * monthlyRate;
    }
    


}