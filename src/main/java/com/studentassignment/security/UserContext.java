package com.studentassignment.security;
import com.studentassignment.repository.UserRepository;import com.studentassignment.service.ApiException;import org.springframework.http.HttpStatus;import org.springframework.security.core.Authentication;import org.springframework.stereotype.Component;
@Component public class UserContext {private final UserRepository users;public UserContext(UserRepository users){this.users=users;}public Long id(Authentication a){return users.findByEmailIgnoreCase(a.getName()).orElseThrow(()->new ApiException(HttpStatus.UNAUTHORIZED,"Account not found.")).getId();}}
