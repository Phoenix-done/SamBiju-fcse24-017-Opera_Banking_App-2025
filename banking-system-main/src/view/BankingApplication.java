package view;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"view", "controller"})
public class BankingApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(BankingApplication.class, args);
        System.out.println("Banking System Started!");
        System.out.println("Access the application at: http://localhost8080:");
    }
}