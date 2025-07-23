package com.web.prj.services.auth;

import io.jsonwebtoken.Claims;

import javax.crypto.SecretKey;

public interface TokenService {
    String generate(String subject, String role, int expiration);
    String refresh(String refreshToken);
    Claims getClaims(String token);
    SecretKey encodeSecretKey();
}
