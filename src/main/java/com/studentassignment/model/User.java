package com.studentassignment.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name="full_name", nullable=false, length=100) private String fullName;
    @Column(nullable=false, unique=true, length=150) private String email;
    @Column(nullable=false, length=255) private String password;
    @Enumerated(EnumType.STRING) @Column(nullable=false, length=16) private UserRole role;
    @Column(length=100) private String department;
    @Column(name="created_at", insertable=false, updatable=false) private LocalDateTime createdAt;
    @Column(name="updated_at", insertable=false, updatable=false) private LocalDateTime updatedAt;
    protected User() {}
    public User(String fullName, String email, String password, UserRole role, String department) { this.fullName=fullName; this.email=email; this.password=password; this.role=role; this.department=department; }
    public Long getId(){return id;} public String getFullName(){return fullName;} public String getEmail(){return email;} public String getPassword(){return password;} public UserRole getRole(){return role;} public String getDepartment(){return department;}
}
