package com.studentassignment.api;
import com.studentassignment.service.ApiException; import java.util.Map; import org.springframework.http.*; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.annotation.*; import org.springframework.dao.DataIntegrityViolationException;
@RestControllerAdvice public class ApiExceptionHandler {
 @ExceptionHandler(ApiException.class) ResponseEntity<?> api(ApiException e){return ResponseEntity.status(e.getStatus()).body(Map.of("message",e.getMessage()));}
 @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<?> validation(MethodArgumentNotValidException e){return ResponseEntity.badRequest().body(Map.of("message",e.getBindingResult().getFieldErrors().stream().findFirst().map(x->x.getDefaultMessage()).orElse("Invalid request.")));}
 @ExceptionHandler(DataIntegrityViolationException.class) ResponseEntity<?> conflict(DataIntegrityViolationException e){return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message","A record with these details already exists."));}
 @ExceptionHandler(IllegalArgumentException.class) ResponseEntity<?> badRequest(IllegalArgumentException e){return ResponseEntity.badRequest().body(Map.of("message",e.getMessage()==null?"Invalid request.":e.getMessage()));}
 @ExceptionHandler(Exception.class) ResponseEntity<?> unexpected(Exception e){return ResponseEntity.status(500).body(Map.of("message","The request could not be completed."));}
}
