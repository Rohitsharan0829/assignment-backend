package com.studentassignment.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="assignments")
public class Assignment {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false,length=200) private String title;
    @Column(columnDefinition="TEXT") private String description;
    @Column(nullable=false,length=100) private String subject;
    @Column(name="max_marks",nullable=false) private Integer maxMarks=100;
    @Column(nullable=false) private LocalDateTime deadline;
    @Column(name="instruction_file_path",length=500) private String instructionFilePath;
    @Column(name="instruction_file_name",length=255) private String instructionFileName;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="created_by",nullable=false) private User createdBy;
    @Column(name="created_at",insertable=false,updatable=false) private LocalDateTime createdAt;
    @Column(name="updated_at",insertable=false,updatable=false) private LocalDateTime updatedAt;
    protected Assignment() {}
    public Assignment(String title,String description,String subject,Integer maxMarks,LocalDateTime deadline,User createdBy){this.title=title;this.description=description;this.subject=subject;this.maxMarks=maxMarks;this.deadline=deadline;this.createdBy=createdBy;}
    public void update(String title,String description,String subject,Integer maxMarks,LocalDateTime deadline){this.title=title;this.description=description;this.subject=subject;this.maxMarks=maxMarks;this.deadline=deadline;}
    public void setInstructionFile(String path,String name){this.instructionFilePath=path;this.instructionFileName=name;}
    public Long getId(){return id;} public String getTitle(){return title;} public String getDescription(){return description;} public String getSubject(){return subject;} public Integer getMaxMarks(){return maxMarks;} public LocalDateTime getDeadline(){return deadline;} public String getInstructionFilePath(){return instructionFilePath;} public String getInstructionFileName(){return instructionFileName;} public User getCreatedBy(){return createdBy;}
}
