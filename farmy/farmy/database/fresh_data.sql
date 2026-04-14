-- FARMY Database - Fresh Data
-- Clear existing data
USE farmy;

DELETE FROM vaccinations;
DELETE FROM visitors;
DELETE FROM animals;
DELETE FROM batches;

-- Insert Batches
INSERT INTO batches (batch_name, species, arrival_date, batch_size, notes) VALUES
('Broiler Batch A', 'Chicken', '2026-01-15', 50, 'Fast-growing broilers for market'),
('Layer Batch B', 'Chicken', '2026-01-10', 30, 'Egg-laying hens'),
('Dairy Batch C', 'Cattle', '2025-12-01', 10, 'Holstein dairy cows'),
('Grower Pigs', 'Pig', '2026-01-05', 20, 'Yorkshire pigs for market');

-- Insert Animals (30 total)
-- Broiler Batch A (10 chickens)
INSERT INTO animals (batch_id, tag_number, species, breed, birth_date, gender, health_status, weight, notes, last_checkup) VALUES
(1, 'BR-001', 'Chicken', 'Cobb 500', '2026-01-15', 'Male', 'Normal', 2.5, 'Good growth rate', '2026-01-30'),
(1, 'BR-002', 'Chicken', 'Cobb 500', '2026-01-15', 'Female', 'Normal', 2.3, 'Healthy bird', '2026-01-30'),
(1, 'BR-003', 'Chicken', 'Cobb 500', '2026-01-15', 'Male', 'Sick', 1.8, 'Respiratory issue, under treatment', '2026-02-01'),
(1, 'BR-004', 'Chicken', 'Cobb 500', '2026-01-15', 'Female', 'Normal', 2.4, 'Strong and active', '2026-01-30'),
(1, 'BR-005', 'Chicken', 'Cobb 500', '2026-01-15', 'Male', 'Normal', 2.6, 'Above average weight', '2026-01-30'),
(1, 'BR-006', 'Chicken', 'Cobb 500', '2026-01-15', 'Female', 'Normal', 2.2, 'Good condition', '2026-01-30'),
(1, 'BR-007', 'Chicken', 'Cobb 500', '2026-01-15', 'Male', 'Normal', 2.5, 'Excellent feeder', '2026-01-30'),
(1, 'BR-008', 'Chicken', 'Cobb 500', '2026-01-15', 'Female', 'Sick', 1.9, 'Low appetite, monitoring', '2026-02-01'),
(1, 'BR-009', 'Chicken', 'Cobb 500', '2026-01-15', 'Male', 'Normal', 2.7, 'Top performer', '2026-01-30'),
(1, 'BR-010', 'Chicken', 'Cobb 500', '2026-01-15', 'Female', 'Normal', 2.3, 'Healthy and active', '2026-01-30');

-- Layer Batch B (8 chickens)
INSERT INTO animals (batch_id, tag_number, species, breed, birth_date, gender, health_status, weight, notes, last_checkup) VALUES
(2, 'LAY-001', 'Chicken', 'ISA Brown', '2026-01-10', 'Female', 'Normal', 1.8, 'Good egg production', '2026-01-29'),
(2, 'LAY-002', 'Chicken', 'ISA Brown', '2026-01-10', 'Female', 'Normal', 1.9, 'Consistent layer', '2026-01-29'),
(2, 'LAY-003', 'Chicken', 'ISA Brown', '2026-01-10', 'Female', 'Quarantine', 1.6, 'Precautionary isolation', '2026-02-01'),
(2, 'LAY-004', 'Chicken', 'ISA Brown', '2026-01-10', 'Female', 'Normal', 1.8, 'Strong health', '2026-01-29'),
(2, 'LAY-005', 'Chicken', 'ISA Brown', '2026-01-10', 'Female', 'Normal', 1.9, 'Excellent layer', '2026-01-29'),
(2, 'LAY-006', 'Chicken', 'ISA Brown', '2026-01-10', 'Female', 'Normal', 1.7, 'Good condition', '2026-01-29'),
(2, 'LAY-007', 'Chicken', 'ISA Brown', '2026-01-10', 'Female', 'Normal', 1.8, 'Healthy bird', '2026-01-29'),
(2, 'LAY-008', 'Chicken', 'ISA Brown', '2026-01-10', 'Female', 'Normal', 1.9, 'High production', '2026-01-29');

-- Dairy Batch C (6 cattle)
INSERT INTO animals (batch_id, tag_number, species, breed, birth_date, gender, health_status, weight, notes, last_checkup) VALUES
(3, 'COW-001', 'Cattle', 'Holstein', '2023-05-15', 'Female', 'Normal', 650.0, 'High milk yield, 25L/day', '2026-01-28'),
(3, 'COW-002', 'Cattle', 'Holstein', '2023-06-20', 'Female', 'Normal', 620.0, 'Good milker, 22L/day', '2026-01-28'),
(3, 'COW-003', 'Cattle', 'Holstein', '2023-07-10', 'Female', 'Sick', 580.0, 'Mastitis treatment ongoing', '2026-02-01'),
(3, 'COW-004', 'Cattle', 'Holstein', '2023-08-05', 'Female', 'Normal', 640.0, 'Excellent health, 24L/day', '2026-01-28'),
(3, 'COW-005', 'Cattle', 'Holstein', '2024-01-12', 'Female', 'Normal', 450.0, 'Young heifer, not yet producing', '2026-01-28'),
(3, 'COW-006', 'Cattle', 'Holstein', '2023-04-18', 'Female', 'Normal', 660.0, 'Top producer, 26L/day', '2026-01-28');

-- Grower Pigs (6 pigs)
INSERT INTO animals (batch_id, tag_number, species, breed, birth_date, gender, health_status, weight, notes, last_checkup) VALUES
(4, 'PIG-001', 'Pig', 'Yorkshire', '2025-12-10', 'Male', 'Normal', 85.0, 'Good weight gain', '2026-01-27'),
(4, 'PIG-002', 'Pig', 'Yorkshire', '2025-12-10', 'Female', 'Normal', 80.0, 'Healthy grower', '2026-01-27'),
(4, 'PIG-003', 'Pig', 'Yorkshire', '2025-12-10', 'Male', 'Normal', 88.0, 'Above average size', '2026-01-27'),
(4, 'PIG-004', 'Pig', 'Yorkshire', '2025-12-10', 'Female', 'Normal', 82.0, 'Strong and active', '2026-01-27'),
(4, 'PIG-005', 'Pig', 'Yorkshire', '2025-12-10', 'Male', 'Normal', 86.0, 'Good feeder', '2026-01-27'),
(4, 'PIG-006', 'Pig', 'Yorkshire', '2025-12-10', 'Female', 'Normal', 81.0, 'Healthy pig', '2026-01-27');

-- Insert Vaccinations (15 records)
INSERT INTO vaccinations (animal_id, vaccine_name, vaccination_date, next_due_date, administered_by, batch_number, status, notes) VALUES
(1, 'Newcastle Disease', '2026-01-20', '2026-04-20', 'Dr. Sarah Johnson', 'ND-2026-01', 'Completed', 'No adverse reactions'),
(2, 'Newcastle Disease', '2026-01-20', '2026-04-20', 'Dr. Sarah Johnson', 'ND-2026-01', 'Completed', 'Successful vaccination'),
(3, 'Newcastle Disease', '2026-01-20', '2026-04-20', 'Dr. Sarah Johnson', 'ND-2026-01', 'Completed', 'Bird was healthy at time'),
(4, 'Infectious Bronchitis', '2026-02-05', '2026-05-05', 'Dr. Michael Chen', 'IB-2026-02', 'Scheduled', 'Upcoming vaccination'),
(5, 'Infectious Bronchitis', '2026-02-05', '2026-05-05', 'Dr. Michael Chen', 'IB-2026-02', 'Scheduled', 'Part of routine schedule'),
(11, 'Marek Disease', '2026-01-15', '2026-07-15', 'Dr. Sarah Johnson', 'MD-2026-01', 'Completed', 'Layers vaccinated successfully'),
(12, 'Marek Disease', '2026-01-15', '2026-07-15', 'Dr. Sarah Johnson', 'MD-2026-01', 'Completed', 'Good response'),
(19, 'Foot and Mouth Disease', '2026-01-25', '2026-07-25', 'Dr. Robert Lee', 'FMD-2026-01', 'Completed', 'Dairy herd protected'),
(20, 'Foot and Mouth Disease', '2026-01-25', '2026-07-25', 'Dr. Robert Lee', 'FMD-2026-01', 'Completed', 'Required for dairy'),
(21, 'Brucellosis', '2026-02-10', '2026-08-10', 'Dr. Robert Lee', 'BR-2026-02', 'Scheduled', 'Scheduled for next week'),
(25, 'Porcine Parvovirus', '2026-01-22', '2026-04-22', 'Dr. Lisa Martinez', 'PPV-2026-01', 'Completed', 'Breeding stock protection'),
(26, 'Porcine Parvovirus', '2026-01-22', '2026-04-22', 'Dr. Lisa Martinez', 'PPV-2026-01', 'Completed', 'No issues noted'),
(27, 'Erysipelas', '2026-02-08', '2026-08-08', 'Dr. Lisa Martinez', 'ERY-2026-02', 'Scheduled', 'Important for market pigs'),
(28, 'Erysipelas', '2026-02-08', '2026-08-08', 'Dr. Lisa Martinez', 'ERY-2026-02', 'Scheduled', 'Routine vaccination'),
(29, 'Erysipelas', '2026-02-08', '2026-08-08', 'Dr. Lisa Martinez', 'ERY-2026-02', 'Scheduled', 'Preventive measure');

-- Insert Visitors (12 records)
INSERT INTO visitors (visitor_name, organization, purpose, visit_date, exit_date, temperature, biosecurity_compliance, notes) VALUES
('Dr. Sarah Johnson', 'County Veterinary Service', 'Routine health inspection', '2026-02-01 09:00:00', '2026-02-01 11:30:00', 36.5, 'Yes', 'All biosecurity protocols followed'),
('John Miller', 'AG Supply Co.', 'Feed delivery', '2026-02-01 14:00:00', '2026-02-01 14:45:00', 36.7, 'Yes', 'Standard delivery, no animal contact'),
('Mary Thompson', 'State Agriculture Dept', 'Annual farm inspection', '2026-01-31 10:00:00', '2026-01-31 15:00:00', 36.6, 'Yes', 'Inspection passed successfully'),
('Dr. Michael Chen', 'Mobile Vet Services', 'Poultry vaccination', '2026-01-30 08:00:00', '2026-01-30 12:00:00', 36.5, 'Yes', 'Vaccinated broiler batch'),
('Tom Richards', 'Farm Equipment Ltd', 'Equipment repair', '2026-01-29 13:00:00', '2026-01-29 16:00:00', 36.8, 'Partial', 'Tools disinfected, limited barn access'),
('Emma Davis', 'Agricultural University', 'Research visit', '2026-01-28 09:30:00', '2026-01-28 14:00:00', 36.4, 'Yes', 'Student group tour completed'),
('Dr. Robert Lee', 'Bovine Health Specialists', 'Dairy herd checkup', '2026-01-27 07:00:00', '2026-01-27 10:30:00', 36.6, 'Yes', 'All cattle examined'),
('James Wilson', 'Feed & Grain Co.', 'Feed consultation', '2026-01-26 11:00:00', '2026-01-26 12:30:00', 36.7, 'Yes', 'Nutrition plan updated'),
('Sophie Brown', 'Farm Bureau', 'Membership renewal', '2026-01-25 15:00:00', '2026-01-25 16:00:00', 36.5, 'Yes', 'No barn entry required'),
('Dr. Lisa Martinez', 'Swine Health Center', 'Pig health assessment', '2026-01-24 08:30:00', '2026-01-24 11:00:00', 36.6, 'Yes', 'Herd health excellent'),
('David Clark', 'Organic Certification', 'Compliance audit', '2026-01-23 10:00:00', '2026-01-23 14:30:00', 36.5, 'Yes', 'Certification maintained'),
('Rachel Green', 'Equipment Maintenance', 'Scheduled maintenance', '2026-01-22 13:00:00', '2026-01-22 15:00:00', 36.9, 'Partial', 'Limited access granted');
