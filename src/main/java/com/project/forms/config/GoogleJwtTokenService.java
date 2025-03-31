package com.project.forms.config;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.project.forms.utils.Constants.*;

@Service
public class GoogleJwtTokenService {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long EXPIRATION_TIME = 1000 * 60 * 30; // 30 minutes

    public String generateToken(GoogleIdToken.Payload payload) {
        long currentTime = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(payload.getSubject()) // Google's unique user ID
                .claim(ROLE, ROLE_USER)
                .claim(EMAIL, payload.getEmail())
                .claim(NAME, payload.get(NAME))
                .claim(PICTURE, payload.get(PICTURE))
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public SecretKey getSecretKey() {
        return SECRET_KEY;
    }
}
