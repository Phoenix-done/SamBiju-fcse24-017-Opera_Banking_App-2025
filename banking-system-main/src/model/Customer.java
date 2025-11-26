package model;

import java.time.LocalDateTime;

public class Customer {
    private int customerId;
    private String firstname;
    private String surname;
    private String address;
    private String phone;
    private String email;
    private LocalDateTime createdDate;

    public Customer() {
        this.createdDate = LocalDateTime.now();
    }

    public Customer(String firstname, String surname, String address, String phone, String email) {
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.createdDate = LocalDateTime.now();
    }

    public Customer(int customerId, String firstname, String surname, String address, String phone, String email) {
        this.customerId = customerId;
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.createdDate = LocalDateTime.now();
    }

    // Getters and Setters
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }
    public String getSurname() { return surname; }
    public void setSurname(String surname) { this.surname = surname; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }

    public String getFullName() {
        return firstname + " " + surname;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", name='" + getFullName() + "'" +
                ", email='" + email + "'" +
                ", phone='" + phone + "'" +
                "}";
    }
}