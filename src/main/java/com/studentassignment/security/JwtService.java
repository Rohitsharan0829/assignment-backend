package com.studentassignment.security;

import io.jsonwebtoken.Jwts; import io.jsonwebtoken.security.Keys; import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets; import java.util.Date; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service;

@Service public class JwtService {
    private final SecretKey key; private final long expiration;
    public JwtService(@Value("${jwt.secret}") String secret,@Value("${jwt.expiration}") long expiration){
        if(secret==null || secret.getBytes(StandardCharsets.UTF_8).length<32) throw new IllegalArgumentException("JWT_SECRET must contain at least 32 bytes.");
        this.key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)); this.expiration=expiration;
    }
    public String issue(String email,String role){long now=System.currentTimeMillis(); return Jwts.builder().subject(email).claim("role",role).issuedAt(new Date(now)).expiration(new Date(now+expiration)).signWith(key).compact();}
    public String subject(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();}
}
