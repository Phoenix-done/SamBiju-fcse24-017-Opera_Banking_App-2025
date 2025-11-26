-- Banking System Database Schema

CREATE TABLE IF NOT EXISTS Customer (
    customer_id INTEGER PRIMARY KEY AUTOINCREMENT,
    firstname VARCHAR(100) NOT NULL,
    surname VARCHAR(100) NOT NULL,
    address TEXT,
    phone VARCHAR(20),
    email VARCHAR(100),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS Account (
    account_number VARCHAR(20) PRIMARY KEY,
    customer_id INTEGER NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    balance DECIMAL(15, 2) DEFAULT 0.00,
    branch VARCHAR(100),
    interest_rate DECIMAL(5, 4),
    employer_name VARCHAR(200),
    employer_address TEXT,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES Customer(customer_id)
);

CREATE TABLE IF NOT EXISTS Transaction (
    transaction_id INTEGER PRIMARY KEY AUTOINCREMENT,
    account_number VARCHAR(20) NOT NULL,
    transaction_type VARCHAR(20) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT,
    FOREIGN KEY (account_number) REFERENCES Account(account_number)
);

-- Insert sample data
INSERT INTO Customer (firstname, surname, address, phone, email) VALUES
('John', 'Doe', '123 Main St, Gaborone', '71234567', 'john.doe@email.com'),
('Jane', 'Smith', '456 Park Ave, Gaborone', '72345678', 'jane.smith@email.com'),
('Mike', 'Johnson', '789 Oak Rd, Francistown', '73456789', 'mike.j@email.com'),
('Sarah', 'Williams', '321 Pine St, Maun', '74567890', 'sarah.w@email.com'),
('David', 'Brown', '654 Elm St, Gaborone', '75678901', 'david.b@email.com'),
('Lisa', 'Davis', '987 Cedar Ave, Kasane', '76789012', 'lisa.d@email.com'),
('Tom', 'Wilson', '147 Birch Ln, Gaborone', '77890123', 'tom.w@email.com'),
('Emma', 'Taylor', '258 Maple Dr, Serowe', '78901234', 'emma.t@email.com'),
('Chris', 'Anderson', '369 Walnut Way, Gaborone', '79012345', 'chris.a@email.com'),
('Amy', 'Thomas', '741 Cherry Ct, Palapye', '70123456', 'amy.t@email.com');