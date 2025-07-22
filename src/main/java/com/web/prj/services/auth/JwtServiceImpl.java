package com.web.prj.services.auth;

import com.web.prj.services.auth.TokenService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JwtServiceImpl implements TokenService {

    @Value("${spring.security.oauth2.resourceserver.jwt.secret-key}")
    private String secretKey;
    @Override
    public String generate(String subject, String role, int expiration){
        Instant now = Instant.now();
        Instant expiry = now.plus(expiration, ChronoUnit.MINUTES);
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer("TIEN-DEV-JAVA")
                .setAudience("localhost:8080")
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiry))
                .claim("role", role)
                .signWith(encodeSecretKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    @Override
    public SecretKey encodeSecretKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }
}
