package com.studentassignment.service;
import org.springframework.http.HttpStatus;
public class ApiException extends RuntimeException { private final HttpStatus status; public ApiException(HttpStatus status,String message){super(message);this.status=status;} public HttpStatus getStatus(){return status;} public static ApiException notFound(String m){return new ApiException(HttpStatus.NOT_FOUND,m);} public static ApiException forbidden(){return new ApiException(HttpStatus.FORBIDDEN,"You are not allowed to perform this action.");} }
