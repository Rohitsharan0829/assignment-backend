package com.studentassignment.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class JwtServiceTest {
    @Test void issuesAndReadsSignedTokenForUserEmail() {
        JwtService service = new JwtService("test-secret-that-is-at-least-32-bytes-long", 60_000);
        String token = service.issue("student@example.com", "STUDENT");
        assertEquals("student@example.com", service.subject(token));
    }
}
