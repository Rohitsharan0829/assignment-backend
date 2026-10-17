package com.studentassignment.service;
import com.studentassignment.model.*; import com.studentassignment.repository.*; import java.time.LocalDateTime; import java.util.*; import org.springframework.http.HttpStatus; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import org.springframework.web.multipart.MultipartFile;
@Service public class AssignmentService {
 private final AssignmentRepository assignments;private final UserRepository users;private final FileStorageService files;
 public AssignmentService(AssignmentRepository assignments,UserRepository users,FileStorageService files){this.assignments=assignments;this.users=users;this.files=files;}
 public List<Assignment> list(String subject){return subject==null||subject.isBlank()?assignments.findAllByOrderByDeadlineAsc():assignments.findBySubjectContainingIgnoreCaseOrderByDeadlineAsc(subject.trim());}
 public List<Assignment> faculty(Long userId){return assignments.findByCreatedByIdOrderByDeadlineAsc(userId);}
 public Assignment get(Long id){return assignments.findById(id).orElseThrow(()->ApiException.notFound("Assignment not found."));}
 @Transactional public Assignment create(String title,String description,String subject,Integer marks,LocalDateTime deadline,MultipartFile file,Long userId){validate(title,subject,marks,deadline);User owner=users.findById(userId).orElseThrow(()->ApiException.notFound("User not found."));Assignment a=new Assignment(title.trim(),description,subject.trim(),marks==null?100:marks,deadline,owner);if(file!=null&&!file.isEmpty()){var stored=files.store(file);a.setInstructionFile(stored.path(),stored.originalName());}return assignments.save(a);}
 @Transactional public Assignment update(Long id,String title,String description,String subject,Integer marks,LocalDateTime deadline,MultipartFile file,Long userId){Assignment a=owned(id,userId);validate(title,subject,marks,deadline);a.update(title.trim(),description,subject.trim(),marks==null?a.getMaxMarks():marks,deadline);if(file!=null&&!file.isEmpty()){var stored=files.store(file);a.setInstructionFile(stored.path(),stored.originalName());}return a;}
 @Transactional public void delete(Long id,Long userId){assignments.delete(owned(id,userId));}
 public Assignment owned(Long id,Long userId){Assignment a=get(id);if(!a.getCreatedBy().getId().equals(userId))throw ApiException.forbidden();return a;}
 private void validate(String title,String subject,Integer marks,LocalDateTime deadline){if(title==null||title.isBlank()||title.length()>200||subject==null||subject.isBlank()||subject.length()>100||deadline==null||(marks!=null&&marks<1))throw new ApiException(HttpStatus.BAD_REQUEST,"Enter a valid title, subject, deadline and maximum marks.");}
}
