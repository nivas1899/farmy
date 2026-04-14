#!/bin/bash

# FARMY Database Setup Script
# This script sets up the database for the FARMY application

set -e

echo "🗄️  FARMY Database Setup"
echo "======================="
echo ""

# Check if MySQL/MariaDB is running
if ! sudo systemctl is-active --quiet mariadb; then
    echo "❌ MariaDB is not running. Starting it..."
    sudo systemctl start mariadb
    sleep 2
fi

echo "📊 Setting up database..."
echo ""

# Run all database commands as sudo mysql (uses unix_socket auth for root)
sudo mysql <<EOF
-- Create database
CREATE DATABASE IF NOT EXISTS farmy;

-- Use the database
USE farmy;

-- Drop existing tables if they exist (to avoid conflicts)
DROP TABLE IF EXISTS visitors;
DROP TABLE IF EXISTS vaccinations;
DROP TABLE IF EXISTS animals;
DROP TABLE IF EXISTS batches;
DROP TABLE IF EXISTS users;

-- Create users table
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Create batches table
CREATE TABLE batches (
    batch_id INT PRIMARY KEY AUTO_INCREMENT,
    batch_name VARCHAR(100) NOT NULL,
    species VARCHAR(50) NOT NULL,
    batch_size INT NOT NULL,
    arrival_date DATE NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_species (species),
    INDEX idx_arrival (arrival_date)
) ENGINE=InnoDB;

-- Create animals table
CREATE TABLE animals (
    animal_id INT PRIMARY KEY AUTO_INCREMENT,
    batch_id INT,
    tag_number VARCHAR(50) UNIQUE NOT NULL,
    species VARCHAR(50) NOT NULL,
    breed VARCHAR(100),
    birth_date DATE,
    gender ENUM('Male', 'Female', 'Unknown') DEFAULT 'Unknown',
    health_status ENUM('Normal', 'Sick', 'Quarantine', 'Deceased') DEFAULT 'Normal',
    weight DECIMAL(10,2),
    notes TEXT,
    last_checkup DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (batch_id) REFERENCES batches(batch_id) ON DELETE SET NULL,
    INDEX idx_species (species),
    INDEX idx_health_status (health_status),
    INDEX idx_tag (tag_number)
) ENGINE=InnoDB;

-- Create vaccinations table
CREATE TABLE vaccinations (
    vaccination_id INT PRIMARY KEY AUTO_INCREMENT,
    animal_id INT NOT NULL,
    vaccine_name VARCHAR(100) NOT NULL,
    vaccination_date DATE NOT NULL,
    next_due_date DATE,
    administered_by VARCHAR(100),
    batch_number VARCHAR(50),
    notes TEXT,
    status ENUM('Scheduled', 'Completed', 'Overdue') DEFAULT 'Scheduled',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (animal_id) REFERENCES animals(animal_id) ON DELETE CASCADE,
    INDEX idx_status (status),
    INDEX idx_next_due (next_due_date)
) ENGINE=InnoDB;

-- Create visitors table  
CREATE TABLE visitors (
    visitor_id INT PRIMARY KEY AUTO_INCREMENT,
    visitor_name VARCHAR(100) NOT NULL,
    organization VARCHAR(100),
    purpose VARCHAR(200) NOT NULL,
    visit_date DATETIME NOT NULL,
    exit_date DATETIME,
    temperature DECIMAL(4,1),
    biosecurity_compliance ENUM('Yes', 'No', 'Partial') DEFAULT 'Yes',
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_visit_date (visit_date),
    INDEX idx_compliance (biosecurity_compliance)
) ENGINE=InnoDB;

-- Insert sample data
INSERT INTO users (username, password) VALUES 
('admin', '5f4dcc3b5aa765d61d8327deb882cf99');  -- password: password

INSERT INTO batches (batch_name, species, batch_size, arrival_date, notes) VALUES
('Batch-001', 'Chicken', 100, '2026-01-15', 'Broiler chickens'),
('Batch-002', 'Cattle', 20, '2026-01-20', 'Dairy cows');

INSERT INTO animals (batch_id, tag_number, species, breed, birth_date, gender, health_status, weight) VALUES
(1, 'CHK-001', 'Chicken', 'Broiler', '2025-12-01', 'Female', 'Normal', 2.5),
(1, 'CHK-002', 'Chicken', 'Broiler', '2025-12-01', 'Male', 'Normal', 2.7),
(2, 'COW-001', 'Cattle', 'Holstein', '2023-05-10', 'Female', 'Normal', 450.0);

-- Grant privileges (allow root to connect from localhost without password for development)
GRANT ALL PRIVILEGES ON farmy.* TO 'root'@'localhost';
FLUSH PRIVILEGES;

SELECT 'Database setup complete!' AS status;
EOF

echo ""
echo "✅ Database setup complete!"
echo ""
echo "📋 Database Summary:"
sudo mysql -e "USE farmy; SELECT 'users' as table_name, COUNT(*) as count FROM users UNION SELECT 'batches', COUNT(*) FROM batches UNION SELECT 'animals', COUNT(*) FROM animals;"
echo ""
echo "✅ Ready to use!"
