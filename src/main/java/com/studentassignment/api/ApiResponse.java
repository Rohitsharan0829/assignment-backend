package com.studentassignment.api;
import java.util.Map;
public record ApiResponse<T>(T data) { public static Map<String,Object> message(String message){return Map.of("message",message);} }
