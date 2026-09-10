-- Universal Clean Seed Data for Civic Connect (H2 & MySQL Compatible)

-- Insert Departments
INSERT INTO departments (id, name, description) VALUES
(1, 'Roads & Infrastructure', 'Handles potholes, asphalt repair, and road maintenance'),
(2, 'Sanitation & Solid Waste', 'Garbage collection, street sweeping, and dump cleanups'),
(3, 'Electrical & Lighting', 'Streetlights, traffic lights, and power poles'),
(4, 'Water & Sewage', 'Water leaks, pipe bursts, and drainage overflows'),
(5, 'Parks & Public Safety', 'Park upkeep, fallen trees, and public safety hazards');

-- Insert Wards
INSERT INTO wards (id, ward_number, ward_name, zone) VALUES
(1, 101, 'Downtown Central', 'Zone A'),
(2, 102, 'Northside Park', 'Zone A'),
(3, 103, 'Westside Market', 'Zone B'),
(4, 104, 'Eastside Hub', 'Zone B');

-- Insert Categories with Default SLA Hours
INSERT INTO categories (id, name, default_sla_hours, description) VALUES
(1, 'Pothole & Road Damage', 48, 'Cracks, deep potholes, broken paving'),
(2, 'Garbage Accumulation', 24, 'Uncollected waste, illegal dumping'),
(3, 'Broken Streetlight', 72, 'Dark streetlights, blinking bulbs'),
(4, 'Water Leakage', 24, 'Burst pipe, water wasting on street'),
(5, 'Drainage Overflow', 36, 'Blocked sewers, rainwater clogging');

-- Insert Users (Password: 'password123' hashed with BCrypt)
-- Citizen user
INSERT INTO users (id, username, email, password, full_name, phone, role, ward_id, created_at) VALUES
(1, 'citizen_john', 'john@example.com', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymY05x0Z9Qj1qE15eJ/uE2', 'John Doe', '9876543210', 'ROLE_CITIZEN', 1, CURRENT_TIMESTAMP);

-- Officer user (Roads Dept, Ward 101)
INSERT INTO users (id, username, email, password, full_name, phone, role, department_id, ward_id, created_at) VALUES
(2, 'officer_smith', 'smith@gov.city', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymY05x0Z9Qj1qE15eJ/uE2', 'Officer Alex Smith', '9876543211', 'ROLE_OFFICER', 1, 1, CURRENT_TIMESTAMP);

-- Admin user
INSERT INTO users (id, username, email, password, full_name, phone, role, created_at) VALUES
(3, 'admin_super', 'admin@gov.city', '$2a$10$8.UnVuG9HHgffUDAlk8qfOuVGkqRzgVymY05x0Z9Qj1qE15eJ/uE2', 'City Chief Admin', '9876543212', 'ROLE_ADMIN', CURRENT_TIMESTAMP);

-- Insert Initial Sample Complaints
INSERT INTO complaints (id, complaint_number, title, description, category_id, department_id, ward_id, citizen_id, officer_id, status, latitude, longitude, address, photo_url, sla_deadline, is_escalated, created_at, updated_at) VALUES
(1001, 'CMP-2026-1001', 'Major Pothole on Main St', 'Hazardous deep pothole near central crossroad causing traffic slowdown.', 1, 1, 1, 1, 2, 'IN_PROGRESS', 28.6139, 77.2090, 'Main St, Ward 101, Downtown', 'https://images.unsplash.com/photo-1515162816999-a0c47dc192f7?auto=format&fit=crop&w=600&q=80', '2026-09-12 12:00:00', FALSE, '2026-09-10 06:00:00', '2026-09-10 10:00:00'),
(1002, 'CMP-2026-1002', 'Overflowing Trash Bins', 'Garbage bins at market square overflowing since yesterday.', 2, 2, 1, 1, NULL, 'SUBMITTED', 28.6145, 77.2095, 'Market Square, Ward 101', 'https://images.unsplash.com/photo-1530587191325-3db32d826c18?auto=format&fit=crop&w=600&q=80', '2026-09-11 18:00:00', FALSE, '2026-09-10 14:00:00', '2026-09-10 14:00:00'),
(1003, 'CMP-2026-1003', 'Streetlight Outage on 5th Ave', 'Dark street stretch near school entrance.', 3, 3, 2, 1, 2, 'RESOLVED', 28.6180, 77.2150, '5th Ave, Ward 102', 'https://images.unsplash.com/photo-1509114397022-ed747cca3f65?auto=format&fit=crop&w=600&q=80', '2026-09-09 18:00:00', FALSE, '2026-09-08 10:00:00', '2026-09-09 16:00:00');

-- Status History Timelines
INSERT INTO status_history (id, complaint_id, status, updated_by_id, comments, created_at) VALUES
(1, 1001, 'SUBMITTED', 1, 'Complaint submitted by citizen.', '2026-09-10 06:00:00'),
(2, 1001, 'ASSIGNED', 3, 'Auto-assigned to Roads Department (Officer Smith).', '2026-09-10 07:00:00'),
(3, 1001, 'IN_PROGRESS', 2, 'Officer Smith accepted task. Crew dispatched with asphalt patcher.', '2026-09-10 10:00:00'),
(4, 1003, 'SUBMITTED', 1, 'Issue reported.', '2026-09-08 10:00:00'),
(5, 1003, 'ASSIGNED', 3, 'Assigned to Electrical Dept.', '2026-09-08 14:00:00'),
(6, 1003, 'IN_PROGRESS', 2, 'Replacing broken LED fixture.', '2026-09-09 09:00:00'),
(7, 1003, 'RESOLVED', 2, 'New LED fixture installed and verified functional.', '2026-09-09 16:00:00');
