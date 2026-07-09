package sn.sdley.my_spring_boot_app2_security.service;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import sn.sdley.my_spring_boot_app2_security.model.User;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    private final String secretKey;

    Map<String, Object>  claims = new HashMap<>();

    public JWTService() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGenerator.generateKey();
            this.secretKey = java.util.Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate secret key for JWT", e);
        }
    }

    public String generateToken(User user) {
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Token valid for 10 hours
                .signWith(getKey())
                .compact();
    }

    private Key getKey() {
        return new javax.crypto.spec.SecretKeySpec(java.util.Base64.getDecoder().decode(secretKey), "HmacSHA256");
    }

    // Add JWT-related methods here
    
}
