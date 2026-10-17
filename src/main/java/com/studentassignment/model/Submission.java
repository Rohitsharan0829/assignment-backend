package com.studentassignment.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="submissions", uniqueConstraints=@UniqueConstraint(name="unique_submission",columnNames={"assignment_id","student_id"}))
public class Submission {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="assignment_id",nullable=false) private Assignment assignment;
    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="student_id",nullable=false) private User student;
    @Column(name="file_path",nullable=false,length=500) private String filePath;
    @Column(name="file_name",nullable=false,length=255) private String fileName;
    @Column(name="submitted_at",nullable=false) private LocalDateTime submittedAt;
    @Enumerated(EnumType.STRING) @Column(nullable=false,length=16) private SubmissionStatus status=SubmissionStatus.SUBMITTED;
    private Integer marks;
    @Column(columnDefinition="TEXT") private String feedback;
    @Column(name="graded_at") private LocalDateTime gradedAt;
    protected Submission() {}
    public Submission(Assignment assignment,User student,String filePath,String fileName,SubmissionStatus status){this.assignment=assignment;this.student=student;this.filePath=filePath;this.fileName=fileName;this.status=status;this.submittedAt=LocalDateTime.now();}
    public void replaceFile(String path,String name,SubmissionStatus newStatus){filePath=path;fileName=name;status=newStatus;submittedAt=LocalDateTime.now();marks=null;feedback=null;gradedAt=null;}
    public void grade(int marks,String feedback){this.marks=marks;this.feedback=feedback;this.status=SubmissionStatus.GRADED;this.gradedAt=LocalDateTime.now();}
    public Long getId(){return id;} public Assignment getAssignment(){return assignment;} public User getStudent(){return student;} public String getFilePath(){return filePath;} public String getFileName(){return fileName;} public LocalDateTime getSubmittedAt(){return submittedAt;} public SubmissionStatus getStatus(){return status;} public Integer getMarks(){return marks;} public String getFeedback(){return feedback;}
}
