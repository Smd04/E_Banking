package com.projet.project_e_banking.Config;

import com.projet.project_e_banking.Model.EspaceClient.User;
import com.projet.project_e_banking.Repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Autowired
    private UserRepository userRepository;
    private final String jwtSecret = "YourNewSuperStrongSecretKeyWithAtLeast32Characters!";
    private final int jwtExpirationInMs = 86400000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        System.out.println("Generating token for: " + authentication.getName());
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

        Object principal = authentication.getPrincipal();
        String username = null;
        String role = null;
        String email = null;
        Long userId = null;

        if (principal instanceof User) {
            User user = (User) principal;
            username = user.getUsername();
            role = user.getRole().toString();
            email = user.getEmail();
            userId = user.getId();
        } else if (principal instanceof org.springframework.security.core.userdetails.User) {
            username = ((org.springframework.security.core.userdetails.User) principal).getUsername();
            User user = userRepository.findByUsername(username).orElse(null);
            if (user != null) {
                role = user.getRole().toString();
                email = user.getEmail();
                userId = user.getId();
            }
        }

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("id", userId)
                .claim("username", username)
                .claim("role", role)
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(getSigningKey())
                .compact();
    }

    public String getUsernameFromJWT(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException |
                 MalformedJwtException | SignatureException ex) {
            System.out.println("JWT Error: " + ex.getMessage());
            return false;
        }
    }
}
