-- ============================================================
-- Student Assignment Management System - Database Schema
-- Database: student_assignment_db
-- ============================================================

-- Create the database
CREATE DATABASE IF NOT EXISTS student_assignment_db;
USE student_assignment_db;

-- ============================================================
-- 1. USERS TABLE
-- Stores student and faculty/admin accounts
-- ============================================================
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('STUDENT', 'FACULTY') NOT NULL DEFAULT 'STUDENT',
    department VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 2. ASSIGNMENTS TABLE
-- Stores assignments created by faculty
-- ============================================================
CREATE TABLE IF NOT EXISTS assignments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    description TEXT,
    subject VARCHAR(100) NOT NULL,
    max_marks INT NOT NULL DEFAULT 100,
    deadline DATETIME NOT NULL,
    instruction_file_path VARCHAR(500),
    instruction_file_name VARCHAR(255),
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- 3. SUBMISSIONS TABLE
-- Stores student assignment submissions
-- ============================================================
CREATE TABLE IF NOT EXISTS submissions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    assignment_id BIGINT NOT NULL,
    student_id BIGINT NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    file_name VARCHAR(255) NOT NULL,
    submitted_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status ENUM('SUBMITTED', 'GRADED', 'LATE') NOT NULL DEFAULT 'SUBMITTED',
    marks INT DEFAULT NULL,
    feedback TEXT DEFAULT NULL,
    graded_at TIMESTAMP NULL,
    FOREIGN KEY (assignment_id) REFERENCES assignments(id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES users(id) ON DELETE CASCADE,
    UNIQUE KEY unique_submission (assignment_id, student_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ============================================================
-- INDEXES for performance
-- ============================================================
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_assignments_subject ON assignments(subject);
CREATE INDEX idx_assignments_deadline ON assignments(deadline);
CREATE INDEX idx_assignments_created_by ON assignments(created_by);
CREATE INDEX idx_submissions_assignment ON submissions(assignment_id);
CREATE INDEX idx_submissions_student ON submissions(student_id);
CREATE INDEX idx_submissions_status ON submissions(status);

-- ============================================================
-- SAMPLE DATA
-- Note: Passwords are BCrypt hashed. The plain text is shown in comments.
-- ============================================================

-- Faculty account: faculty@example.com / password123
INSERT INTO users (full_name, email, password, role, department) VALUES
('Dr. Rahul Sharma', 'faculty@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'FACULTY', 'Computer Science');

-- Student accounts: student1@example.com, student2@example.com, rohit@gmail.com
INSERT INTO users (full_name, email, password, role, department) VALUES
('Anita Verma', 'student1@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'STUDENT', 'Computer Science'),
('Raj Patel', 'student2@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'STUDENT', 'Computer Science'),
('Rohit Kumar', 'rohit@gmail.com', '$2a$10$eACCYoNOHEqgkVE8aGkVbOu/O25K64aJ5n0y58u2tNsq7nLz4k7u2', 'STUDENT', 'Computer Science');

-- Sample assignments
INSERT INTO assignments (title, description, subject, max_marks, deadline, created_by) VALUES
('Java OOP Concepts', 'Write a program demonstrating all four pillars of OOP: Encapsulation, Inheritance, Polymorphism, and Abstraction. Include at least two classes and one interface.', 'Java Programming', 100, DATE_ADD(NOW(), INTERVAL 7 DAY), 1),
('SQL Database Design', 'Design a normalized database schema for a library management system. Include ER diagram description and SQL CREATE TABLE statements with proper constraints.', 'Database Management', 50, DATE_ADD(NOW(), INTERVAL 14 DAY), 1),
('HTML CSS Portfolio', 'Create a personal portfolio website using HTML5 and CSS3. The website should be responsive and include at least 3 pages: Home, About, and Contact.', 'Web Development', 75, DATE_ADD(NOW(), INTERVAL 10 DAY), 1);
