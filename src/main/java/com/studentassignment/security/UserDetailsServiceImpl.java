package com.studentassignment.security;
import com.studentassignment.repository.UserRepository; import org.springframework.security.core.userdetails.*; import org.springframework.stereotype.Service;
@Service public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository users; public UserDetailsServiceImpl(UserRepository users){this.users=users;}
    @Override public UserDetails loadUserByUsername(String email){var user=users.findByEmailIgnoreCase(email).orElseThrow(()->new UsernameNotFoundException("Account not found")); return User.withUsername(user.getEmail()).password(user.getPassword()).roles(user.getRole().name()).build();}
}
