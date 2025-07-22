package com.web.prj.services.auth;

import javax.crypto.SecretKey;

public interface TokenService {
    String generate(String subject, String role, int expiration);
    SecretKey encodeSecretKey();
}
