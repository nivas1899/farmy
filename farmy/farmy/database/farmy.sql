-- =====================================================
-- FARMY Database Schema
-- Farm Management & Biosecurity System
-- =====================================================

DROP DATABASE IF EXISTS farmy;
CREATE DATABASE farmy CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE farmy;

-- =====================================================
-- Table: users
-- Purpose: Authentication and user management
-- =====================================================
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_username (username)
) ENGINE=InnoDB;

-- =====================================================
-- Table: batches
-- Purpose: Group animals into batches/lots
-- =====================================================
CREATE TABLE batches (
    batch_id INT AUTO_INCREMENT PRIMARY KEY,
    batch_name VARCHAR(100) NOT NULL,
    animal_type ENUM('Pig', 'Poultry') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_batch_name (batch_name)
) ENGINE=InnoDB;

-- =====================================================
-- Table: animals
-- Purpose: Individual animal health tracking
-- =====================================================
CREATE TABLE animals (
    animal_id INT AUTO_INCREMENT PRIMARY KEY,
    batch_id INT NOT NULL,
    animal_code VARCHAR(50) NOT NULL UNIQUE,
    status ENUM('NORMAL', 'SICK', 'ISOLATED', 'DEAD') DEFAULT 'NORMAL',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (batch_id) REFERENCES batches(batch_id) ON DELETE CASCADE,
    INDEX idx_batch (batch_id),
    INDEX idx_status (status)
) ENGINE=InnoDB;

-- =====================================================
-- Table: vaccinations
-- Purpose: Vaccination scheduling and tracking
-- =====================================================
CREATE TABLE vaccinations (
    vaccine_id INT AUTO_INCREMENT PRIMARY KEY,
    batch_id INT NOT NULL,
    vaccine_name VARCHAR(100) NOT NULL,
    scheduled_date DATE NOT NULL,
    status ENUM('Pending', 'Done') DEFAULT 'Pending',
    administered_date DATE NULL,
    notes TEXT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (batch_id) REFERENCES batches(batch_id) ON DELETE CASCADE,
    INDEX idx_batch (batch_id),
    INDEX idx_status (status),
    INDEX idx_scheduled (scheduled_date)
) ENGINE=InnoDB;

-- =====================================================
-- Table: visitors
-- Purpose: Farm visitor biosecurity tracking
-- =====================================================
CREATE TABLE visitors (
    visitor_id INT AUTO_INCREMENT PRIMARY KEY,
    visitor_name VARCHAR(100) NOT NULL,
    role VARCHAR(100) NULL,
    entry_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    sanitized BOOLEAN DEFAULT false,
    notes TEXT NULL,
    INDEX idx_entry_date (DATE(entry_time))
) ENGINE=InnoDB;

-- =====================================================
-- Sample Data for Testing
-- =====================================================

-- Create test user (password: 'admin123' - use proper hashing in production)
INSERT INTO users (username, password_hash) VALUES 
('admin', 'e3afed0047b08059d0fada10f400c1e5'),  -- MD5 hash for demo only
('farmer1', '5f4dcc3b5aa765d61d8327deb882cf99'); -- password: 'password'

-- Create sample batches
INSERT INTO batches (batch_name, animal_type) VALUES 
('Batch-A', 'Pig'),
('Batch-B', 'Pig'),
('Grower-1', 'Poultry'),
('Shed-2', 'Poultry');

-- Add sample animals
INSERT INTO animals (batch_id, animal_code, status) VALUES 
(1, 'Batch-A-01', 'NORMAL'),
(1, 'Batch-A-02', 'NORMAL'),
(1, 'Batch-A-03', 'SICK'),
(1, 'Batch-A-04', 'NORMAL'),
(1, 'Batch-A-05', 'ISOLATED'),
(2, 'Batch-B-01', 'NORMAL'),
(2, 'Batch-B-02', 'NORMAL'),
(2, 'Batch-B-03', 'NORMAL'),
(3, 'Grower-1-01', 'NORMAL'),
(3, 'Grower-1-02', 'NORMAL'),
(3, 'Grower-1-03', 'DEAD'),
(4, 'Shed-2-01', 'NORMAL'),
(4, 'Shed-2-02', 'SICK');

-- Add sample vaccinations
INSERT INTO vaccinations (batch_id, vaccine_name, scheduled_date, status, administered_date) VALUES 
(1, 'Swine Flu Vaccine', DATE_SUB(CURDATE(), INTERVAL 5 DAY), 'Done', DATE_SUB(CURDATE(), INTERVAL 5 DAY)),
(1, 'Deworming Treatment', CURDATE(), 'Pending', NULL),
(2, 'PPR Vaccine', DATE_ADD(CURDATE(), INTERVAL 3 DAY), 'Pending', NULL),
(3, 'Newcastle Disease', DATE_SUB(CURDATE(), INTERVAL 2 DAY), 'Done', DATE_SUB(CURDATE(), INTERVAL 2 DAY)),
(3, 'Gumboro Vaccine', DATE_ADD(CURDATE(), INTERVAL 7 DAY), 'Pending', NULL),
(4, 'IBD Vaccine', DATE_SUB(CURDATE(), INTERVAL 1 DAY), 'Pending', NULL);

-- Add sample visitors
INSERT INTO visitors (visitor_name, role, entry_time, sanitized, notes) VALUES 
('John Smith', 'Veterinarian', DATE_SUB(NOW(), INTERVAL 2 HOUR), true, 'Routine checkup'),
('Mary Johnson', 'Feed Supplier', DATE_SUB(NOW(), INTERVAL 5 HOUR), true, 'Delivered feed'),
('Robert Lee', 'Farm Inspector', DATE_SUB(NOW(), INTERVAL 1 DAY), true, 'Health inspection'),
('Sarah Williams', 'Technician', DATE_SUB(NOW(), INTERVAL 3 HOUR), true, 'Equipment maintenance'),
('David Brown', 'Visitor', NOW(), true, 'Family visit'),
('Lisa Garcia', 'Consultant', DATE_SUB(NOW(), INTERVAL 30 MINUTE), false, 'Advisory visit');

-- =====================================================
-- Views for Dashboard Analytics
-- =====================================================

-- Animal status summary view
CREATE VIEW v_animal_status_summary AS
SELECT 
    status,
    COUNT(*) as count
FROM animals
GROUP BY status;

-- Today's visitor count view
CREATE VIEW v_today_visitors AS
SELECT 
    COUNT(*) as visitor_count
FROM visitors
WHERE DATE(entry_time) = CURDATE();

-- Pending vaccinations view
CREATE VIEW v_pending_vaccinations AS
SELECT 
    v.vaccine_id,
    b.batch_name,
    v.vaccine_name,
    v.scheduled_date,
    DATEDIFF(CURDATE(), v.scheduled_date) as days_overdue
FROM vaccinations v
JOIN batches b ON v.batch_id = b.batch_id
WHERE v.status = 'Pending'
ORDER BY v.scheduled_date ASC;

-- =====================================================
-- End of Schema
-- =====================================================
