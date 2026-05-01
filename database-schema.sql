-- Database schema for Second-Hand Marketplace

CREATE DATABASE IF NOT EXISTS second_hand_marketplace;
USE second_hand_marketplace;

CREATE TABLE IF NOT EXISTS Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    role VARCHAR(20) NOT NULL DEFAULT 'Buyer'
);

CREATE TABLE IF NOT EXISTS Products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT NOT NULL,
    price DOUBLE NOT NULL,
    product_condition VARCHAR(80) NOT NULL,
    category VARCHAR(80) NOT NULL,
    status VARCHAR(40) NOT NULL DEFAULT 'Available',
    seller_id INT NOT NULL,
    FOREIGN KEY (seller_id) REFERENCES Users(user_id)
);

CREATE TABLE IF NOT EXISTS Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    buyer_id INT NOT NULL,
    product_id INT NOT NULL,
    total_amount DOUBLE NOT NULL,
    status VARCHAR(40) NOT NULL DEFAULT 'Placed',
    order_date DATE NOT NULL,
    FOREIGN KEY (buyer_id) REFERENCES Users(user_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

CREATE TABLE IF NOT EXISTS Payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    amount DOUBLE NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    status VARCHAR(40) NOT NULL DEFAULT 'Pending',
    payment_date DATE NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);

CREATE TABLE IF NOT EXISTS Deliveries (
    delivery_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    status VARCHAR(40) NOT NULL DEFAULT 'Pending',
    pickup_date DATE NOT NULL,
    delivery_date DATE,
    delivery_partner VARCHAR(100),
    tracking_id VARCHAR(50) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);

CREATE TABLE IF NOT EXISTS Messages (
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    sender_id INT NOT NULL,
    recipient_id INT NOT NULL,
    content TEXT NOT NULL,
    timestamp DATE NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (sender_id) REFERENCES Users(user_id),
    FOREIGN KEY (recipient_id) REFERENCES Users(user_id)
);
