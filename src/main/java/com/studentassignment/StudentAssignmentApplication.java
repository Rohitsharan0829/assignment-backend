package com.studentassignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Student Assignment Management System.
 * 
 * This Spring Boot application provides REST APIs for:
 * - User registration and authentication (JWT-based)
 * - Assignment creation and management (Faculty)
 * - Assignment submission and grading (Students & Faculty)
 * - PDF file upload and download
 */
@SpringBootApplication
public class StudentAssignmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentAssignmentApplication.class, args);
        System.out.println("==============================================");
        System.out.println("  Student Assignment Management System");
        System.out.println("  Server is running on http://localhost:8080");
        System.out.println("==============================================");
    }
}
