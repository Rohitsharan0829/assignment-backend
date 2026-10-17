package com.studentassignment.repository;
import com.studentassignment.model.Assignment; import java.util.List; import org.springframework.data.jpa.repository.JpaRepository;
public interface AssignmentRepository extends JpaRepository<Assignment,Long> { List<Assignment> findAllByOrderByDeadlineAsc(); List<Assignment> findBySubjectContainingIgnoreCaseOrderByDeadlineAsc(String subject); List<Assignment> findByCreatedByIdOrderByDeadlineAsc(Long userId); }
