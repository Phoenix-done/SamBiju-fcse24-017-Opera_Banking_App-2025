package util;

import java.util.Random;

public class AccountNumberGenerator {
    private static final String PREFIX = "BW";
    private static Random random = new Random();

    public static String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder(PREFIX);
        for (int i = 0; i < 10; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }

    public static String generateAccountNumber(String accountType) {
        String typePrefix = "";
        switch (accountType.toUpperCase()) {
            case "SAVINGS":
                typePrefix = "SAV";
                break;
            case "INVESTMENT":
                typePrefix = "INV";
                break;
            case "CHEQUE":
                typePrefix = "CHQ";
                break;
            default:
                typePrefix = "ACC";
        }
        
        StringBuilder accountNumber = new StringBuilder(PREFIX + typePrefix);
        for (int i = 0; i < 8; i++) {
            accountNumber.append(random.nextInt(10));
        }
        return accountNumber.toString();
    }
}