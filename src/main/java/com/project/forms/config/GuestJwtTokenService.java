package com.project.forms.config;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.project.forms.utils.Constants.*;

@Service
public class GuestJwtTokenService {

    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 1000 * 60 * 30; // 30 minutes

    public String generateGuestToken() {
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(GUEST)
                .claim(ROLE, ROLE_GUEST)
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public SecretKey getSecretKey() {
        return SECRET_KEY;
    }
}
