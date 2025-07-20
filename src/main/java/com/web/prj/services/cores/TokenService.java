package com.web.prj.services.cores;

import javax.crypto.SecretKey;

public interface TokenService {
    String generate(String subject, String role, int expiration);
    SecretKey encodeSecretKey();
}
